package park.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import park.dao.ParkInfoSetDao;
import park.entity.ParkInfoSet;
import park.entity.User;
import park.util.MySqlUtil;

public class ParkInfoSetDaoImp implements ParkInfoSetDao{

	public boolean modfiy(String userInfo,String parkName) {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			String sql="update park_info_set set userInfo=? where parkName=?";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, userInfo);
			ps.setString(2, parkName);
			int n=ps.executeUpdate();
			if(n==1){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				MySqlUtil.closeConection(conn, ps);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}

	@Override
	public boolean add(ParkInfoSet parkInfoSet) {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			String sql="insert into park_info_set(parkName,parkLocation,openDate,closeDate,parkNumber,rentOrSale,feeScale,userInfo) values(?,?,?,?,?,?,?,?)";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, parkInfoSet.getParkName());
			ps.setString(2, parkInfoSet.getParkLocation());
			ps.setString(3, parkInfoSet.getOpenDate());
			ps.setString(4, parkInfoSet.getCloseDate());
			ps.setInt(5, parkInfoSet.getParkNumber());
			ps.setInt(6, parkInfoSet.getRentOrSale());
			ps.setDouble(7, parkInfoSet.getFeeScale());
			ps.setString(8, parkInfoSet.getUserInfo());
			int n=ps.executeUpdate();
			if(n==1){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				MySqlUtil.closeConection(conn, ps);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}
	
	/**
	 * 根据车库名和消费类型进行查询
	 * @param name
	 * @param type
	 * @return
	 */
	public ParkInfoSet queryCarNumberByNameAndType(String name,Integer type) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,parkName,parkLocation,openDate,closeDate,parkNumber,rentOrSale,feeScale,userInfo from park_info_set where parkName=? and rentOrSale=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, name);
				ps.setInt(2, type);
			    rs= ps.executeQuery();
			    if(rs.next()){
			    	ParkInfoSet parkInfoSet = new ParkInfoSet();
			    	parkInfoSet.setId(rs.getInt("id"));
			    	parkInfoSet.setParkName(rs.getString("parkName"));
			    	parkInfoSet.setParkLocation(rs.getString("parkLocation"));
			    	parkInfoSet.setOpenDate(rs.getString("openDate"));
			    	parkInfoSet.setCloseDate(rs.getString("closeDate"));
			    	parkInfoSet.setParkNumber(rs.getInt("parkNumber"));
			    	parkInfoSet.setRentOrSale(Integer.parseInt(rs.getString("rentOrSale")));
			    	parkInfoSet.setFeeScale(rs.getDouble("feeScale"));
			    	parkInfoSet.setUserInfo(rs.getString("userInfo"));
			    	//carParkInfoList.add(carParkInfo);
			    	return parkInfoSet;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				
				try {
					MySqlUtil.closeConection(conn, ps, rs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	        return null;
	}
	
	@Override
	public boolean delete(ParkInfoSet parkInfoSet) {
		Connection conn=null;
		PreparedStatement ps=null;
		int n=0;
		try {
			conn=MySqlUtil.getConnection();
			String sql="delete from park_info_set where id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, parkInfoSet.getId());
		    n= ps.executeUpdate();
			if(n==1){
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				MySqlUtil.closeConection(conn, ps);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} 
		return false;
	}

	@Override
	public ParkInfoSet queryParkInfoById(Integer id) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        User user = new User();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,parkName,parkLocation,openDate,closeDate,parkNumber,rentOrSale,feeScale,userInfo from park_info_set where id=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, id);
			    rs= ps.executeQuery();
			    
			    if(rs.next()){
			    	ParkInfoSet parkInfoSet = new ParkInfoSet();
					parkInfoSet.setId(rs.getInt("id"));
					parkInfoSet.setParkName(rs.getString("parkName"));
					parkInfoSet.setParkLocation(rs.getString("parkLocation"));
					parkInfoSet.setOpenDate(rs.getString("openDate"));
					parkInfoSet.setCloseDate(rs.getString("closeDate"));
					parkInfoSet.setParkNumber(rs.getInt("parkNumber"));
					parkInfoSet.setRentOrSale(rs.getInt("rentOrSale"));
					parkInfoSet.setFeeScale(rs.getDouble("feeScale"));
					parkInfoSet.setUserInfo(rs.getString("userInfo"));
					return parkInfoSet;
			    }
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					MySqlUtil.closeConection(conn, ps, rs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	        return null;//user;
	}
	
	/**
	 * 根据车库名查询车库的相关信息
	 */
	@Override
	public ParkInfoSet queryParkInfoByName(String parkName) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        User user = new User();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,parkName,parkLocation,openDate,closeDate,parkNumber,rentOrSale,feeScale,userInfo from park_info_set where parkName=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, parkName);
			    rs= ps.executeQuery();
			    
			    if(rs.next()){
			    	ParkInfoSet parkInfoSet = new ParkInfoSet();
					parkInfoSet.setId(rs.getInt("id"));
					parkInfoSet.setParkName(rs.getString("parkName"));
					parkInfoSet.setParkLocation(rs.getString("parkLocation"));
					parkInfoSet.setOpenDate(rs.getString("openDate"));
					parkInfoSet.setCloseDate(rs.getString("closeDate"));
					parkInfoSet.setParkNumber(rs.getInt("parkNumber"));
					parkInfoSet.setRentOrSale(rs.getInt("rentOrSale"));
					parkInfoSet.setFeeScale(rs.getDouble("feeScale"));
					parkInfoSet.setUserInfo(rs.getString("userInfo"));
					return parkInfoSet;
			    }
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					MySqlUtil.closeConection(conn, ps, rs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	        return null;//user;
	}

	
	/**
	 * 查询停车场所有车库的相关信息和车位的使用情况
	 */
	@Override
	public List<ParkInfoSet> queryAll() {
		//id,parkName,parkLocation,openDate,closeDate,parkNumber,rentOrSale,feeScale,userInfo
		Connection  conn= null;
		PreparedStatement ps =null;
		ResultSet rs  =null;
		List<ParkInfoSet> listParkInfoSet = new ArrayList<ParkInfoSet>();
		
		try {
			conn=MySqlUtil.getConnection();
			String sql = "select id,parkName,parkLocation,openDate,closeDate,parkNumber,rentOrSale,feeScale,userInfo from park_info_set order by parkName";
			ps=conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next()){
				ParkInfoSet parkInfoSet = new ParkInfoSet();
				parkInfoSet.setId(rs.getInt("id"));
				parkInfoSet.setParkName(rs.getString("parkName"));
				parkInfoSet.setParkLocation(rs.getString("parkLocation"));
				parkInfoSet.setOpenDate(rs.getString("openDate"));
				parkInfoSet.setCloseDate(rs.getString("closeDate"));
				parkInfoSet.setParkNumber(rs.getInt("parkNumber"));
				parkInfoSet.setRentOrSale(rs.getInt("rentOrSale"));
				parkInfoSet.setFeeScale(rs.getDouble("feeScale"));
				parkInfoSet.setUserInfo(rs.getString("userInfo"));
				listParkInfoSet.add(parkInfoSet);
			}
			return listParkInfoSet;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				MySqlUtil.closeConection(conn, ps, rs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public boolean modfiy(ParkInfoSet parkInfoSet) {
		// TODO Auto-generated method stub
		return false;
	}

}
