package park.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import park.daoImp.CarParkInfoDaoImp;
import park.daoImp.ChargeInfoDaoImp;
import park.daoImp.ParkInfoSetDaoImp;
import park.daoImp.ReservationDaoImp;
import park.daoImp.UserDaoImp;
import park.entity.CarParkInfo;
import park.entity.ChargeInfo;
import park.entity.ParkInfoSet;
import park.entity.Reservation;
import park.entity.User;
import park.redis.Redis;
import park.util.Constants;
import park.util.Dumper;
import park.util.MyDate;
import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionSupport;

public class CarParkAction extends ActionSupport {
	
	private CarParkInfo carParkInfo;
	private ParkInfoSet parkInfoSet;
	private List<String> parkList = new ArrayList<String>();
	private List<String> locationList = new ArrayList<String>();
	private CarParkInfoDaoImp carParkInfoDaoImp = new CarParkInfoDaoImp();
	private ParkInfoSetDaoImp parkInfoSetDaoImp = new ParkInfoSetDaoImp();
	private ReservationDaoImp reservationDaoImp = new ReservationDaoImp();
	private UserDaoImp  userDaoImp=new UserDaoImp();

	

	private Jedis jedis = Redis.createJedis();
	
	private String para;
	private String id;
	private String name;
	private String type;
	private Integer count;
	private String carNo;
	private List<CarParkInfo> listData = new ArrayList<CarParkInfo>();


	/**
	 * 临时车辆入住登记信息
	 * @return
	 */
	public String addCarParkInfo(){
		String userName = StringUtils.isEmpty(carParkInfo.getUserName())?getRandomString(6):carParkInfo.getUserName();
		if (null != carParkInfo) {
			carParkInfo.setUserName(userName);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			String inTime = sdf.format(new Date());
			carParkInfo.setInTime(inTime);
			System.out.println(userName+"\t"+inTime);
		}
		
		if (null != carParkInfo && (carParkInfoDaoImp.add(carParkInfo))) {
			para = "1";
			ParkInfoSet parkInfoSet = parkInfoSetDaoImp.queryCarNumberByNameAndType(carParkInfo.getParkName(), carParkInfo.getType());
			String s = parkInfoSet.getUserInfo();
			if (StringUtils.isEmpty(s)) {
				s = carParkInfo.getLocationNo() +",";
			} else {
				s = s + carParkInfo.getLocationNo() +",";
			}
			
			parkInfoSetDaoImp.modfiy(s, carParkInfo.getParkName());

			//解决缓存同步的问题
//			jedis.del(carParkInfo.getParkName());
//			jedis.close();
//			sendMsg("insert",carParkInfo.getParkName());
//			amqpTemplate.convertAndSend(carParkInfo.getParkName());
//			rabbitMqSender.sendDataToCrQueue("1", "2");
		} else {
			para = "0";
		}
		return SUCCESS;
	}
	
	/**
	 * 1.根据用户名和车牌号查询预约信息
	 *  判断用户是否已经预约
	 *  a.预约
	 *  	用户车辆进入停车场
	 *  b.未预约
	 *  	返回到页面，该用户没有预约
	 * @throws ParseException 
	 * 
	 */
	public void getReservationInfo() throws ParseException{
		PrintWriter printWriter = null; 
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			printWriter = response.getWriter();
			//根据用户名查询用户的预约信息
			List<Reservation> lists = reservationDaoImp.queryReservationByUserName(name);
			//判断用户是否已经预约
			if (null == lists || lists.size() == 0) {
				Map map = new HashMap<String,String>();
				map.put("isOk", "noreservationinfo");
				printWriter.write(new Gson().toJson(map));
				printWriter.flush();
			} else {
				//如果用户已经预约
				//用户的车辆进入停车场
				Reservation r = lists.get(0);
				//判断预约时间和当前时间是否大于15分钟，预约超时
				String createTime=r.getCreateTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				long millionSeconds = (sdf.parse(createTime)).getTime();
				long currentTime = System.currentTimeMillis();
				int minute = (int) ((int)(currentTime - millionSeconds)/60000.0);
				//判断预约是否超时
				if(minute>Constants.RULE){
					//修改用户表中的爽约次数
					User user = userDaoImp.queryUserByName(name);
					int count=user.getCount();
					user.setCount(count+1);
					userDaoImp.modfiy(user);
					//修改车位的使用信息
					ParkInfoSet parkInfoSet = parkInfoSetDaoImp.queryCarNumberByNameAndType(r.getParkName(), Integer.parseInt(r.getType()));
					String s = parkInfoSet.getUserInfo();
					s = s.replace(r.getParkNo()+",", "");
					parkInfoSetDaoImp.modfiy(s, r.getParkName());
					//删除预约信息
					reservationDaoImp.delete(r);
					//返回超时信息到页面
					Map map = new HashMap<String,String>();
					map.put("isOk", "chaoshi");
					printWriter.write(new Gson().toJson(map));
					printWriter.flush();
				}else{
				//预约没有超时
				CarParkInfo carParkInfo = new CarParkInfo();
				//设置车牌号
				carParkInfo.setCarNo(carNo);
				
				String inTime = sdf.format(new Date());
				carParkInfo.setInTime(inTime);
				carParkInfo.setLocationNo(r.getParkNo());
				carParkInfo.setType(Integer.parseInt(r.getType()));
				carParkInfo.setParkName(r.getParkName());
				carParkInfo.setUserName(r.getUserName());
				carParkInfoDaoImp.add(carParkInfo);
				//修改预约信息的状态为已经使用
				r.setIsUsed(1);
				//reservationDaoImp.modfiy(r);
				reservationDaoImp.delete(r);
				Map map = new HashMap<String,String>();
				map.put("isOk", "success");
				printWriter.write(new Gson().toJson(map));
				printWriter.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally{
			printWriter.close();
		}
	}
	
	/**
	 * 生成随机用户名
	 * @param length
	 * @return
	 */
	public String getRandomString(int length) { 
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }    
	/**
	 * 临时车辆查询所有车库的名字
	 * @return
	 */
	public String queryAllCarInfo(){
		listData = carParkInfoDaoImp.queryAll();
		List<ParkInfoSet> parkInfoSets = parkInfoSetDaoImp.queryAll();
		for (ParkInfoSet parkInfoSet : parkInfoSets) {
			parkList.add(parkInfoSet.getParkName());
		}
		
		return SUCCESS;
	}
	
	/**
	 * 临时车辆中根据车库名字，查询车位使用信息
	 */
	public void selectCarParkInfoByName(){
		
		PrintWriter printWriter = null; 
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("utf-8");
		Map map = new HashMap<String,String>();
		try {
			printWriter = response.getWriter();
		
				//根据车库名查询车库的相关信息
				ParkInfoSet parkInfoSet = parkInfoSetDaoImp.queryParkInfoByName(name);
				//获取该车库停车位的使用信息
				String saleOrRent = parkInfoSet.getUserInfo();
				//获取该车库停车位数量
				Integer count = parkInfoSet.getParkNumber();
				for (int i = 1; i <= count; i++) {
					if (null ==saleOrRent) {
						locationList.add(i+"");
					} else if (!saleOrRent.contains(i+",")) {
						locationList.add(i+"");
					}
				}
				map.put("locationList", locationList);
				String json = new Gson().toJson(map);
			
				printWriter.write(json );
				printWriter.flush();
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally{
				printWriter.close();
			}
		}
	/**
	 * 根据车库名称和消费类型进行查询车库的相关使用信息
	 * if(缓存中有值){
	 * 	
	 * }
	 * @return
	 * @throws ParseException 
	 */
	public void queryCarNumberByNameAndType() throws ParseException{
		
			Map map = new HashMap<String,String>();
			PrintWriter printWriter = null; 
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
		try {
			printWriter = response.getWriter();
			//从缓存中获取结果
			String cacheResult = jedis.get(name);
			if(cacheResult!=null){
				jedis.close();
				printWriter.write(cacheResult);
				printWriter.flush();
			}else{
				//如果缓存中没有数据，从数据库中获取数据
				//获取车库的车位使用情况
				parkInfoSet = parkInfoSetDaoImp.queryCarNumberByNameAndType(name,Integer.parseInt(type));
				if(null !=parkInfoSet){
					//有这个车库的相关信息
					//获取车库中车位的使用信息
					String userInfo = parkInfoSet.getUserInfo();
					//根据车库名查询这个车库的预约信息
					List<Reservation> list = reservationDaoImp.queryReservationByParkNameAndType(name, type);
					//判断占用的车位中是否有预约车位
					if(list.size()!=0){
						//如果占用的车位中有预约车位
						for (Reservation reservation : list) {
							//判断预约时间和当前时间是否大于15分钟，预约超时
							String createTime=reservation.getCreateTime();
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
							long millionSeconds = (sdf.parse(createTime)).getTime();
							long currentTime = System.currentTimeMillis();
							int minute = (int) ((int)(currentTime - millionSeconds)/60000.0);
							//判断预约是否超时
							if(minute>Constants.RULE){
								//修改用户表中的爽约次数
								User user = userDaoImp.queryUserByName(reservation.getUserName());
								int count=user.getCount();
								user.setCount(count+1);
								userDaoImp.modfiy(user);
								//修改车位的使用信息
								//ParkInfoSet parkInfoSet = parkInfoSetDaoImp.queryCarNumberByNameAndType(r.getParkName(), Integer.parseInt(r.getType()));
								//String s = parkInfoSet.getUserInfo();
								userInfo = userInfo.replace(reservation.getParkNo()+",", "");
								parkInfoSetDaoImp.modfiy(userInfo, reservation.getParkName());
								//删除预约信息
								reservationDaoImp.delete(reservation);
							}//第三个个if结束
						}//for循环结束
					}//第二个if结束
					//获取这个车库的车位数量
					int count = parkInfoSet.getParkNumber();
					map.put("count", count);
					map.put("userInfo",userInfo==null?"":userInfo);
				}else{
					//没有这个车库的相关信息
					map.put("count", 0);
					map.put("userInfo", "");
				}
				String Result = new Gson().toJson(map);
				//将查询结果放到缓存中
				//name为车库的名字
				jedis.set(name, Result);
				//设置缓存的过期时间
				jedis.expire(name, Constants.TIME);
				jedis.close();
				printWriter.write(Result);
				printWriter.flush();
			}			
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally{
			printWriter.close();
		}
	}
	/**
	 * 删除信息
	 * @return
	 */
	public String deleteCarInfo(){
		if (null != id){
			CarParkInfo carParkInfo = new CarParkInfo();
			carParkInfo.setId(Integer.parseInt(id));
			if(carParkInfoDaoImp.delete(carParkInfo)){
				para="5";
			} else {
				para="6";
			}
		}
		System.out.println("para======"+para);
		return SUCCESS;
	}
	
	/**
	 * 修改信息
	 */
	public void modifyCarParkInfo(){
		Dumper.dump(id);
		CarParkInfo carParkInfo = carParkInfoDaoImp.queryCarParkById(Integer.parseInt(id));
		Map map = new HashMap<String,String>();
		map.put("userName", carParkInfo.getUserName());
		map.put("parkName", carParkInfo.getParkName());
		map.put("carNo", carParkInfo.getCarNo());
		map.put("inTime", carParkInfo.getInTime());
		map.put("type", carParkInfo.getType());
		map.put("outTime", carParkInfo.getOutTime());
		map.put("locationNo", carParkInfo.getLocationNo());
		Gson gson = new Gson();
		String date = gson.toJson(map);
		Dumper.dump(date);
		PrintWriter printWriter = null; 
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			printWriter = response.getWriter();
			printWriter.write(date);
			printWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
			
		} finally{
			printWriter.close();
		}
	}
	
	/**
	 * 计费逻辑
	 * @throws ParseException
	 * @throws IOException 
	 */
	public void addChargeInfo() throws ParseException, IOException{
		
			PrintWriter printWriter = null; 
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			printWriter = response.getWriter();
			CarParkInfo carParkInfo = carParkInfoDaoImp.queryCarParkBByCarNo(carNo);
			//判断停车场是否存在当前车辆
			if (null == carParkInfo ) {
			Map map = new HashMap<String,String>();
			map.put("isOk", "nocar");
			printWriter.write(new Gson().toJson(map));
			printWriter.flush();
			printWriter.close();
			}else{
			//如果当前车辆存在
			ParkInfoSet parkInfoSet = new ParkInfoSetDaoImp().queryParkInfoByName(carParkInfo.getParkName());
			Dumper.dump(parkInfoSet);
			Map map = new HashMap<String,String>();
			map.put("userName", carParkInfo.getUserName());
			map.put("parkName", carParkInfo.getParkName());
			map.put("carNo", carParkInfo.getCarNo());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
			long millionSeconds = sdf.parse(carParkInfo.getInTime()).getTime();
			long currentTime = System.currentTimeMillis();
			int hour = (int)((currentTime - millionSeconds)/3600000.0 <= 1 ? 1: (currentTime - millionSeconds)/3600000.0);
			map.put("hour", hour);
			map.put("type", carParkInfo.getType());
			map.put("locationNo", carParkInfo.getLocationNo());
			
			ChargeInfo chargeInfo = new ChargeInfo();
			chargeInfo.setCarNo(carParkInfo.getCarNo());
			//判断用户是否是员工
			User user = userDaoImp.queryUserByName(carParkInfo.getUserName());
			//判断该用户是否注册
			if(user!=null){
				//若用户为注册用户
				chargeInfo.setCharge((user.getType())==3?0:((parkInfoSet.getFeeScale()*hour)));
				//判断用户的类型是员工还是游客
				map.put("charge", (user.getType())==3?0:((parkInfoSet.getFeeScale()*hour)));
			}else{
				//若用户未注册
				chargeInfo.setCharge((parkInfoSet.getFeeScale()*hour));
				map.put("charge", (parkInfoSet.getFeeScale()*hour));
			}
			
			chargeInfo.setCreateTime(MyDate.getMyDate_2());
			chargeInfo.setHour(hour);
			chargeInfo.setLocationNo(Integer.parseInt(carParkInfo.getLocationNo()));
			chargeInfo.setParkName(carParkInfo.getParkName());
			chargeInfo.setType(carParkInfo.getType());
			chargeInfo.setUserName(carParkInfo.getUserName());
			chargeInfo.setIsCharge(1);
			new ChargeInfoDaoImp().add(chargeInfo);
			
			carParkInfo.setOutTime(MyDate.getMyDate_2());
			carParkInfo.setIsCharge(1);
			carParkInfoDaoImp.modfiy(carParkInfo);
			
			Gson gson = new Gson();
			String date = gson.toJson(map);
			/**
			 * 结完账，把车位改为已空闲
			 */
			String userInfo = parkInfoSet.getUserInfo().replace(carParkInfo.getLocationNo()+",", "");
			parkInfoSetDaoImp.modfiy(userInfo, parkInfoSet.getParkName());
			//解决缓存同步的问题
			jedis.del(parkInfoSet.getParkName());
			jedis.close();
			printWriter.write(date);
			printWriter.flush();
			printWriter.close();
		}
		
	}
	
	/**
	 * 指定具体的车库和车位，预订车位
	 */
	public void addCarNumberByNameAndType(){
		PrintWriter printWriter = null;
		try {
			HttpSession session = ServletActionContext.getRequest().getSession();
			String userName =  null != session.getAttribute(Constants.USER_NAME) ? session.getAttribute(Constants.USER_NAME).toString() : "";
			String userType =  null != session.getAttribute(Constants.TYPE) ? session.getAttribute(Constants.TYPE).toString() : "";
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			printWriter = response.getWriter();
			ParkInfoSet parkInfoSet = parkInfoSetDaoImp.queryCarNumberByNameAndType(name, Integer.parseInt(type));
			if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(userType)) {
				if ("1".equals(userType)) {
					System.out.println("管理员不能执行此操作");
					Map map = new HashMap<String,String>();
					map.put("isOk", "no");
					printWriter.write(new Gson().toJson(map));
					printWriter.flush();
				} else {
					List<Reservation> lists = reservationDaoImp.queryReservationByUserName(userName);
					if (null != lists && lists.size() > 0) {
						//如果用户已经预约（数据库中有当前用户的预约信息）
//						Reservation r = lists.get(0);
//						r.setParkNo(count+"");
//						r.setParkName(name);
//						reservationDaoImp.modfiy(r);
						Map map = new HashMap<String,String>();
						map.put("isOk", "reservationed");
						printWriter.write(new Gson().toJson(map));
						printWriter.flush();
					} else if(!((parkInfoSet.getUserInfo()+"").contains(count+""))){
						//如果车位没有被预订
						Reservation reservation =  new Reservation();
						reservation.setParkName(name);
						reservation.setParkNo(count+"");
						reservation.setUserName(userName);
						reservation.setType(type);
						reservation.setCarNo(" ");
						reservation.setCreateTime(MyDate.getMyDate_2());
						reservationDaoImp.add(reservation);
						//根据车库名和消费类型查询车位的使用信息
						
						String s = parkInfoSet.getUserInfo();
						if (StringUtils.isEmpty(s)) {
							s = count +",";
						} else {
							s = s + count +",";
						}
						parkInfoSetDaoImp.modfiy(s, name);
						//解决缓存同步的问题
						jedis.del(name);
						jedis.close();
						Map map = new HashMap<String,String>();
						map.put("isOk", "yes");
						printWriter.write(new Gson().toJson(map));
						printWriter.flush();
					}else{
						Map map = new HashMap<String,String>();
						map.put("isOk", "parked");
						printWriter.write(new Gson().toJson(map));
						printWriter.flush();
					}
				
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			printWriter.close();
		}
	}
	
	
	/**
	 * 取消预订的 车位
	 */
	public void removeCarNumberByNameAndType(){
		PrintWriter printWriter = null; 
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("utf-8");
			printWriter = response.getWriter();
			HttpSession session = ServletActionContext.getRequest().getSession();
			String userName =  null != session.getAttribute(Constants.USER_NAME) ? session.getAttribute(Constants.USER_NAME).toString() : "";
			//通过当前登录的用户名查询用户的预约信息
			List<Reservation> lists = reservationDaoImp.queryReservationByUserName(userName);
			if (lists.size() == 0 || !(lists.get(0).getParkName().equals(name) && lists.get(0).getParkNo().equals(count+""))) {
				Map map = new HashMap<String,String>();
				map.put("isOk", "no_permission");
				printWriter.write(new Gson().toJson(map));
				printWriter.flush();
			} else {
				//用户已经预约
				Reservation reservation = lists.get(0);
				reservationDaoImp.delete(reservation);
				ParkInfoSet parkInfoSet = parkInfoSetDaoImp.queryCarNumberByNameAndType(name, Integer.parseInt(type));
				String s = parkInfoSet.getUserInfo();
				s = s.replace(count+",", "");
				parkInfoSetDaoImp.modfiy(s, name);
				//解决缓存同步的问题
				jedis.del(name);
				jedis.close();
				Map map = new HashMap<String,String>();
				map.put("isOk", "yes_permission");
				printWriter.write(new Gson().toJson(map));
				printWriter.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			printWriter.close();
		}
	}
	
	
	public CarParkInfo getCarParkInfo() {
		return carParkInfo;
	}

	public void setCarParkInfo(CarParkInfo carParkInfo) {
		this.carParkInfo = carParkInfo;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public List<CarParkInfo> getListData() {
		return listData;
	}

	public void setListData(List<CarParkInfo> listData) {
		this.listData = listData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ParkInfoSet getParkInfoSet() {
		return parkInfoSet;
	}
	public void setParkInfoSet(ParkInfoSet parkInfoSet) {
		this.parkInfoSet = parkInfoSet;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

	public String getCarNo() {
		return carNo;
	}

	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}

	public List<String> getParkList() {
		return parkList;
	}

	public void setParkList(List<String> parkList) {
		this.parkList = parkList;
	}

	public List<String> getLocationList() {
		return locationList;
	}

	public void setLocationList(List<String> locationList) {
		this.locationList = locationList;
	}
	
	
}
