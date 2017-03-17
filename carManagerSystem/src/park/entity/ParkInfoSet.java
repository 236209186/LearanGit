package park.entity;

public class ParkInfoSet {
	
	private Integer id;    		
	private String parkName;//停车场车库名字
	private String parkLocation;//停车场地址ַ
	private String openDate;//停车场开放时间
	private String closeDate;//停车场关闭时间
	private int parkNumber;//ͣ停车场车位数量
	private int rentOrSale;//停车场类型
	private double feeScale;//停车场费用
	private String userInfo;//车位使用情况
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getParkLocation() {
		return parkLocation;
	}
	public void setParkLocation(String parkLocation) {
		this.parkLocation = parkLocation;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getCloseDate() {
		return closeDate;
	}
	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}
	public int getRentOrSale() {
		return rentOrSale;
	}
	public void setRentOrSale(int rentOrSale) {
		this.rentOrSale = rentOrSale;
	}
	public double getFeeScale() {
		return feeScale;
	}
	public void setFeeScale(double feeScale) {
		this.feeScale = feeScale;
	}
	public int getParkNumber() {
		return parkNumber;
	}
	public void setParkNumber(int parkNumber) {
		this.parkNumber = parkNumber;
	}
	public String getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}
	
}
