package park.entity;


/**
 * 临时车辆相关信息
 * @author Gux
 *
 */
public class CarParkInfo {
	
	private Integer id;    		
	private String userName;
	private String parkName;
	private String carNo;		
	private String inTime;
	private int type;
	private String outTime;  
	private String locationNo;
	private int  isCharge;
	
	public int getIsCharge() {
		return isCharge;
	}
	public void setIsCharge(int isCharge) {
		this.isCharge = isCharge;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getOutTime() {
		return outTime;
	}
	public void setOutTime(String outTime) {
		this.outTime = outTime;
	}
	public String getLocationNo() {
		return locationNo;
	}
	public void setLocationNo(String locationNo) {
		this.locationNo = locationNo;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	

}
