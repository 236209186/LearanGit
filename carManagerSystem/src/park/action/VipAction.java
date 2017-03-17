package park.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import park.daoImp.CarParkInfoDaoImp;
import park.daoImp.ChargeInfoDaoImp;
import park.daoImp.ParkInfoSetDaoImp;
import park.daoImp.VipDaoImp;
import park.entity.CarParkInfo;
import park.entity.ChargeInfo;
import park.entity.ParkInfoSet;
import park.entity.User;
import park.entity.VIp;
import park.util.Dumper;
import park.util.MyDate;
import sun.security.krb5.internal.PAData;

public class VipAction extends ActionSupport{
	
//	private List<String> parkList = new ArrayList<String>();
//	private List<String> locationList = new ArrayList<String>();
//	private ParkInfoSetDaoImp parkInfoSetDaoImp = new ParkInfoSetDaoImp();
//	private CarParkInfoDaoImp carParkInfoDaoImp = new CarParkInfoDaoImp();
//	private VipDaoImp vipDaoImp = new VipDaoImp();
//	private List<VIp> vips = new ArrayList<VIp>();
//	private VIp vip;
//	private Integer id;
//	private String userName;
//	
//	public String queryVipInfo(){
//		List<ParkInfoSet> parkInfoSets = parkInfoSetDaoImp.queryAll();
//		for (ParkInfoSet parkInfoSet : parkInfoSets) {
//			parkList.add(parkInfoSet.getParkName());
//			String saleOrRent = parkInfoSet.getUserInfo();
//			Integer count = parkInfoSet.getParkNumber();
//			for (int i = 1; i <= count; i++) {
//				if (null ==saleOrRent) {
//					locationList.add(i+"");
//				} else if (!saleOrRent.contains(i+",")) {
//					locationList.add(i+"");
//				}
//			}
//		}
//		return SUCCESS;
//	}
//	
//	public void queryVipByNameAjax(){
//		
//		if (userName.length() > 0) {
//			VIp vIp = vipDaoImp.queryUserByName(userName);
//			PrintWriter printWriter = null; 
//			try {
//				HttpServletResponse response = ServletActionContext.getResponse();
//				response.setCharacterEncoding("utf-8");
//				printWriter = response.getWriter();
//				if (null != vIp) {
//					printWriter.write("true");
//				} else {
//					printWriter.write("false");
//				}
//				printWriter.flush();
//			} catch (IOException e) {
//				e.printStackTrace();
//
//			} finally{
//				printWriter.close();
//			}
//
//		}
//	}
//	
//	public String deleteVipInfoById(){
//		if (null != id) {
//			VIp vIp = new VIp();
//			vIp.setId(id);
//			vipDaoImp.delete(vIp);
//			vips = vipDaoImp.queryAll();
//		}
//		return SUCCESS;
//	}
//	
//	/**
//	 * 添加vip用户
//	 * @return
//	 */
//	public String addVipInfo(){
//		Dumper.dump(vip);
//		if (null != vip) {
//			String parkInfo = vip.getLocation();
//			String parkName = vip.getParkNo();
//			ParkInfoSetDaoImp parkInfoSetDaoImp = new ParkInfoSetDaoImp();
//			ParkInfoSet parkInfoSet = parkInfoSetDaoImp.queryParkInfoByName(parkName);
//			String info = parkInfoSet.getUserInfo();
//			if (null == info) {
//				info = parkInfo + ",";
//			} else {
//				info = info + parkInfo + ",";
//			}
//			parkInfoSetDaoImp.modfiy(info, parkName);
//			vipDaoImp.add(vip);
//			try {
//				chargeVip(vip, parkInfoSet);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//		}
//		return SUCCESS;
//	}
//	
//	public void chargeVip(VIp vIp,ParkInfoSet parkInfoSet) throws ParseException{
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//		long start = sdf.parse(vIp.getStartTime()).getTime();
//		long end = sdf.parse(vIp.getEndTime()).getTime();
//		int hour = (int)((end - start)%3600000 == 0 ? (end - start)/3600000 : (end - start)/3600000+1);
//		
//		ChargeInfo chargeInfo = new ChargeInfo();
//		chargeInfo.setCarNo(vIp.getCarNo());
//		chargeInfo.setCharge(parkInfoSet.getFeeScale()*hour*0.8);
//		chargeInfo.setCreateTime(MyDate.getMyDate_2());
//		chargeInfo.setHour(hour);
//		chargeInfo.setLocationNo(Integer.parseInt(vip.getLocation()));
//		chargeInfo.setParkName(vIp.getParkNo());
//		chargeInfo.setType(vIp.getType());
//		chargeInfo.setUserName(vIp.getUserName());
//		chargeInfo.setIsCharge(1);
//		new ChargeInfoDaoImp().add(chargeInfo);
//	}
//	
//	public String queryAllVipInfo() {
//		vips = vipDaoImp.queryAll();
//		Dumper.dump(vips);
//		return SUCCESS;
//	}
//	
//	public String queryVipInfoByCarNo() {
//		if (null != vip) {
//		vips = vipDaoImp.queryVipByCarNo(vip.getCarNo());
//		Dumper.dump(vips);
//		}
//		return SUCCESS;
//	}
//	
//	public List<String> getParkList() {
//		return parkList;
//	}
//	public void setParkList(List<String> parkList) {
//		this.parkList = parkList;
//	}
//	public List<String> getLocationList() {
//		return locationList;
//	}
//	public void setLocationList(List<String> locationList) {
//		this.locationList = locationList;
//	}
//
//	public List<VIp> getVips() {
//		return vips;
//	}
//
//	public void setVips(List<VIp> vips) {
//		this.vips = vips;
//	}
//
//	public VIp getVip() {
//		return vip;
//	}
//
//	public void setVip(VIp vip) {
//		this.vip = vip;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public Integer getId() {
//		return id;
//	}
//
//	public void setId(Integer id) {
//		this.id = id;
//	}
	
	
}
