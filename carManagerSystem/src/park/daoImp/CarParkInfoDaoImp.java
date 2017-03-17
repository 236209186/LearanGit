package park.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import park.dao.CarParkInfoDao;
import park.entity.CarParkInfo;
import park.entity.User;
import park.util.MySqlUtil;


public class CarParkInfoDaoImp implements CarParkInfoDao{

	@Override
	public boolean modfiy(CarParkInfo carParkInfo) {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			String sql="update car_park_info set outTime=?,isCharge=? where id=?";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, carParkInfo.getOutTime());
			ps.setInt(2, carParkInfo.getIsCharge());
			ps.setInt(3, carParkInfo.getId());
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
	public boolean add(CarParkInfo carParkInfo) {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			String sql="insert into car_park_info(userName,parkName,carNo,inTime,type,outTime,locationNo) values(?,?,?,?,?,?,?)";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, carParkInfo.getUserName());
			ps.setString(2, carParkInfo.getParkName());
			ps.setString(3, carParkInfo.getCarNo());
			ps.setString(4, carParkInfo.getInTime());
			ps.setInt(5, carParkInfo.getType());
			ps.setString(6, "");
			ps.setString(7, carParkInfo.getLocationNo());
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
	public boolean delete(CarParkInfo carParkInfo) {
		Connection conn=null;
		PreparedStatement ps=null;
		int n=0;
		try {
			conn=MySqlUtil.getConnection();
			String sql="delete from car_park_info where id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, carParkInfo.getId());
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
	public CarParkInfo queryCarParkById(Integer id) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,parkName,carNo,inTime,type,outTime,locationNo from car_park_info where id=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, id);
			    rs= ps.executeQuery();
			    if(rs.next()){
			    	CarParkInfo carParkInfo = new CarParkInfo();
			    	carParkInfo.setId(rs.getInt("id"));
			    	carParkInfo.setUserName(rs.getString("userName"));
			    	carParkInfo.setParkName(rs.getString("parkName"));
			    	carParkInfo.setCarNo(rs.getString("carNo"));
			    	carParkInfo.setInTime(rs.getString("inTime"));
			    	carParkInfo.setType(Integer.parseInt(rs.getString("type")));
			    	carParkInfo.setOutTime(rs.getString("outTime"));
			    	carParkInfo.setLocationNo(rs.getString("locationNo"));
					return carParkInfo;
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
	public CarParkInfo queryCarParkBByName(String userName) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,parkName,carNo,inTime,type,outTime,locationNo from car_park_info where userName=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, userName);
			    rs= ps.executeQuery();
			    
			    if(rs.next()){
			    	CarParkInfo carParkInfo = new CarParkInfo();
			    	carParkInfo.setId(rs.getInt("id"));
			    	carParkInfo.setUserName(rs.getString("userName"));
			    	carParkInfo.setParkName(rs.getString("parkName"));
			    	carParkInfo.setCarNo(rs.getString("carNo"));
			    	carParkInfo.setInTime(rs.getString("inTime"));
			    	carParkInfo.setType(Integer.parseInt(rs.getString("type")));
			    	carParkInfo.setOutTime(rs.getString("outTime"));
			    	carParkInfo.setLocationNo(rs.getString("locationNo"));
			    	return carParkInfo;
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
	
	public CarParkInfo queryCarParkBByCarNo(String carNo) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,parkName,carNo,inTime,type,outTime,locationNo from car_park_info where carNo=? and outTime='' and isCharge=0";
				ps=conn.prepareStatement(sql);
				ps.setString(1, carNo);
			    rs= ps.executeQuery();
			    
			    if(rs.next()){
			    	CarParkInfo carParkInfo = new CarParkInfo();
			    	carParkInfo.setId(rs.getInt("id"));
			    	carParkInfo.setUserName(rs.getString("userName"));
			    	carParkInfo.setParkName(rs.getString("parkName"));
			    	carParkInfo.setCarNo(rs.getString("carNo"));
			    	carParkInfo.setInTime(rs.getString("inTime"));
			    	carParkInfo.setType(Integer.parseInt(rs.getString("type")));
			    	carParkInfo.setOutTime(rs.getString("outTime"));
			    	carParkInfo.setLocationNo(rs.getString("locationNo"));
			    	return carParkInfo;
			    }
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				//�ر���
				try {
					MySqlUtil.closeConection(conn, ps, rs);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	        return null;
	}
	
	public CarParkInfo queryCarParkBByNameAndType(String userName,Integer type) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,parkName,carNo,inTime,type,outTime,locationNo from car_park_info where userName=? and type=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, userName);
				ps.setInt(2, type);
			    rs= ps.executeQuery();
			    
			    if(rs.next()){
			    	CarParkInfo carParkInfo = new CarParkInfo();
			    	carParkInfo.setId(rs.getInt("id"));
			    	carParkInfo.setUserName(rs.getString("userName"));
			    	carParkInfo.setParkName(rs.getString("parkName"));
			    	carParkInfo.setCarNo(rs.getString("carNo"));
			    	carParkInfo.setInTime(rs.getString("inTime"));
			    	carParkInfo.setType(Integer.parseInt(rs.getString("type")));
			    	carParkInfo.setOutTime(rs.getString("outTime"));
			    	carParkInfo.setLocationNo(rs.getString("locationNo"));
			    	return carParkInfo;
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
	public List<CarParkInfo> queryAll() {
		Connection  conn= null;
		PreparedStatement ps =null;
		ResultSet rs  =null;
		List<CarParkInfo> carParkInfoList = new ArrayList<CarParkInfo>();
		
		try {
			conn=MySqlUtil.getConnection();
			String sql = "select id,userName,parkName,carNo,inTime,type,outTime,locationNo from car_park_info";
			ps=conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next()){
				CarParkInfo carParkInfo = new CarParkInfo();
		    	carParkInfo.setId(rs.getInt("id"));
		    	carParkInfo.setUserName(rs.getString("userName"));
		    	carParkInfo.setParkName(rs.getString("parkName"));
		    	carParkInfo.setCarNo(rs.getString("carNo"));
		    	carParkInfo.setInTime(rs.getString("inTime"));
		    	carParkInfo.setType(Integer.parseInt(rs.getString("type")));
		    	carParkInfo.setOutTime(rs.getString("outTime"));
		    	carParkInfo.setLocationNo(rs.getString("locationNo"));
		    	carParkInfoList.add(carParkInfo);
			}
			return carParkInfoList;
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
