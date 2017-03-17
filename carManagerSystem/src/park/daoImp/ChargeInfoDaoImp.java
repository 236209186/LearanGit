package park.daoImp;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import park.entity.ChargeInfo;
import park.util.MySqlUtil;

public class ChargeInfoDaoImp{

	public boolean add(ChargeInfo chargeInfo) {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			String sql="insert into charge_info(userName,parkName,carNo,hour,type,locationNo,charge,createTime,isCharge) values(?,?,?,?,?,?,?,?,?)";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, chargeInfo.getUserName());
			ps.setString(2, chargeInfo.getParkName());
			ps.setString(3, chargeInfo.getCarNo());
			ps.setInt(4, chargeInfo.getHour());
			ps.setInt(5, chargeInfo.getType());
			ps.setInt(6, chargeInfo.getLocationNo());
			ps.setDouble(7, chargeInfo.getCharge());
			ps.setString(8, chargeInfo.getCreateTime());
			ps.setInt(9, chargeInfo.getIsCharge());
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

	public boolean delete(Integer id) {
		Connection conn=null;
		PreparedStatement ps=null;
		int n=0;
		try {
			conn=MySqlUtil.getConnection();
			String sql="delete from charge_info where id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, id);
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

	public ChargeInfo queryCarParkById(Integer id) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,parkName,carNo,hour,type,locationNo,charge,createTime,isCharge from charge_info where id=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, id);
			    rs= ps.executeQuery();
			    if(rs.next()){
			    	ChargeInfo chargeInfo = new ChargeInfo();
					chargeInfo.setId(rs.getInt("id"));
					chargeInfo.setUserName(rs.getString("userName"));
					chargeInfo.setParkName(rs.getString("parkName"));
					chargeInfo.setCarNo(rs.getString("carNo"));
					chargeInfo.setHour(rs.getInt("hour"));
					chargeInfo.setType(rs.getInt("type"));
					chargeInfo.setLocationNo(rs.getInt("locationNo"));
					chargeInfo.setCharge(rs.getDouble("charge"));
					chargeInfo.setCreateTime(rs.getString("createTime"));
					chargeInfo.setIsCharge(rs.getInt("isCharge"));
					return chargeInfo;
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

	
	/**
	 * 查询用户使用停车场的费用及其相关信息
	 * @param userName
	 * @return
	 */
	public List<ChargeInfo> queryCarParkBByName(String userName) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        List<ChargeInfo> chargeInfoList = new ArrayList<ChargeInfo>();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,parkName,carNo,hour,type,locationNo,charge,createTime,isCharge from charge_info where userName=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, userName);
			    rs= ps.executeQuery();
			    while(rs.next()){
			    	ChargeInfo chargeInfo = new ChargeInfo();
					chargeInfo.setId(rs.getInt("id"));
					chargeInfo.setUserName(rs.getString("userName"));
					chargeInfo.setParkName(rs.getString("parkName"));
					chargeInfo.setCarNo(rs.getString("carNo"));
					chargeInfo.setHour(rs.getInt("hour"));
					chargeInfo.setType(rs.getInt("type"));
					chargeInfo.setLocationNo(rs.getInt("locationNo"));
					chargeInfo.setCharge(rs.getDouble("charge"));
					chargeInfo.setCreateTime(rs.getString("createTime"));
					chargeInfo.setIsCharge(rs.getInt("isCharge"));
					chargeInfoList.add(chargeInfo);
			    }
			    return chargeInfoList;
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
	
	/**
	 * 查询所有用户使用停车场的相关信息，费用等
	 * @return
	 */
	public List<ChargeInfo> queryAll() {
		Connection  conn= null;
		PreparedStatement ps =null;
		ResultSet rs  =null;
		List<ChargeInfo> chargeInfoList = new ArrayList<ChargeInfo>();
		
		try {
			conn=MySqlUtil.getConnection();
			String sql = "select id,userName,parkName,carNo,hour,type,locationNo,charge,createTime,isCharge from charge_info";
			ps=conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next()){
				ChargeInfo chargeInfo = new ChargeInfo();
				chargeInfo.setId(rs.getInt("id"));
				chargeInfo.setUserName(rs.getString("userName"));
				chargeInfo.setParkName(rs.getString("parkName"));
				chargeInfo.setCarNo(rs.getString("carNo"));
				chargeInfo.setHour(rs.getInt("hour"));
				chargeInfo.setType(rs.getInt("type"));
				chargeInfo.setLocationNo(rs.getInt("locationNo"));
				chargeInfo.setCharge(rs.getDouble("charge"));
				chargeInfo.setCreateTime(rs.getString("createTime"));
				chargeInfo.setIsCharge(rs.getInt("isCharge"));
				chargeInfoList.add(chargeInfo);
			}
			return chargeInfoList;
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


}
