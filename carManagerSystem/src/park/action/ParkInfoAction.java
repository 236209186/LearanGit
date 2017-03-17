package park.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import park.daoImp.ParkInfoSetDaoImp;
import park.daoImp.UserDaoImp;
import park.entity.ParkInfoSet;
import park.entity.User;
import park.redis.Redis;
import park.util.Constants;
import park.util.Dumper;
import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.opensymphony.xwork2.ActionSupport;

public class ParkInfoAction extends ActionSupport {
	
	private ParkInfoSet parkInfoSet;
	private UserDaoImp userDaoImp = new UserDaoImp();
	private ParkInfoSetDaoImp parkInfoSetDaoImp = new ParkInfoSetDaoImp();
	private String errorInfo;
	private String id;
	private String para;
	private List<ParkInfoSet> listPark = new ArrayList<ParkInfoSet>();
	//返回到页面的值
	private List<String> listData = new ArrayList<String>();
	private Jedis jedis = Redis.createJedis();

	
	
	/**
	 * 添加车库的相关信息
	 * @return
	 */
	public String addParkInfo(){
		System.out.println(parkInfoSet);
		if (null == parkInfoSet) {
			listPark = parkInfoSetDaoImp.queryAll();
		} else if (parkInfoSetDaoImp.add(parkInfoSet)) {
			//缓存同步，清除缓存
			jedis.del("parkNameList");
			jedis.close();
			listPark = parkInfoSetDaoImp.queryAll();
			Dumper.dump(listPark);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询所有车库的相关信息(管理员使用)
	 * @return
	 */
	public String getListParkInfo(){
		listPark = parkInfoSetDaoImp.queryAll();
		return SUCCESS;
	}
	
	public String queryParkInfo(){
		String userName = (String) ServletActionContext.getRequest().getSession().getAttribute(Constants.USER_NAME);
		User user = userDaoImp.queryUserByName(userName);
		if (null == user ){
			return "input";
		}
		if (user.getType() == 0) {
			errorInfo = "你无权限做此操作";
		} else if (parkInfoSetDaoImp.add(parkInfoSet)) {
			listPark = parkInfoSetDaoImp.queryAll();
		}
		return SUCCESS;
	}
	
	/**
	 * 删除车库相关信息
	 * @return
	 */
	public String deleteParkInfo(){
		ParkInfoSet parkInfoSet = new ParkInfoSet();
		parkInfoSet.setId(Integer.parseInt(id));
		if (parkInfoSetDaoImp.delete(parkInfoSet)) {
			//缓存同步，清除缓存
			jedis.del("parkNameList");
			jedis.close();
			para="5";
		} else {
			System.out.println("======删除失败=====");
			para="6";
		}
		return SUCCESS;
	}
	
	/**
	 * 供管理员使用的查询所有车库的名字
	 * @return
	 */
	public String getAllParkName(){
		List<ParkInfoSet> listPark = parkInfoSetDaoImp.queryAll();
		for (ParkInfoSet parkInfoSet : listPark) {
			listData.add(parkInfoSet.getParkName());
		}
		Dumper.dump(listData);
		return SUCCESS;
	}
	
	
	/**
	 * 查询有哪些车库的名字(频繁查询)
	 * @return
	 */
	public String queryBespeakInfo(){
		//从缓存中获取数据
		String string = jedis.get("parkNameList");
		jedis.close();
		if(string!=null){
			//将json转换为list
			listData = JSON.parseArray(string,String.class);
		}else{
		//如果缓存中没有数据，从数据库中查询
		List<ParkInfoSet> listPark = parkInfoSetDaoImp.queryAll();
		for (ParkInfoSet parkInfoSet : listPark) {
			listData.add(parkInfoSet.getParkName());
		}
		//list转换为json
		String json = JSON.toJSONString(listData);
		//添加数据到缓存中
		jedis.set("parkNameList", json );
		jedis.close();
		}
		Dumper.dump(listData);
		return SUCCESS;
	}
	
	public void modifyParkInfo(){
		Dumper.dump(id);
		ParkInfoSet parkInfoSet = parkInfoSetDaoImp.queryParkInfoById(Integer.parseInt(id));
		Map map = new HashMap<String,String>();
		map.put("parkName", parkInfoSet.getParkName());
		map.put("parkLocation", parkInfoSet.getParkLocation());
		map.put("openDate", parkInfoSet.getOpenDate());
		map.put("closeDate", parkInfoSet.getCloseDate());
		map.put("parkNumber", parkInfoSet.getParkNumber());
		map.put("rentOrSale", parkInfoSet.getRentOrSale());
		map.put("feeScale", parkInfoSet.getFeeScale());
		map.put("userInfo", parkInfoSet.getUserInfo());
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
	
	public ParkInfoSet getParkInfoSet() {
		return parkInfoSet;
	}

	public void setParkInfoSet(ParkInfoSet parkInfoSet) {
		this.parkInfoSet = parkInfoSet;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	public List<ParkInfoSet> getListPark() {
		return listPark;
	}
	public void setListPark(List<ParkInfoSet> listPark) {
		this.listPark = listPark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public List<String> getListData() {
		return listData;
	}

	public void setListData(List<String> listData) {
		this.listData = listData;
	}
	
	
	
}
