package park.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import park.dao.ReservationDao;
import park.entity.Reservation;
import park.util.MySqlUtil;

public class ReservationDaoImp implements ReservationDao{

	@Override
	public boolean modfiy(Reservation reservation) {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			String sql="update reservation set isUsed=?,parkNo=? where userName=?";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setInt(1, reservation.getIsUsed());
			ps.setString(2, reservation.getParkNo());
			ps.setString(3, reservation.getUserName());
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
	public boolean add(Reservation reservation) {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			String sql="insert into reservation(userName,carNo,parkName,parkNo,type,createTime) values(?,?,?,?,?,?)";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, reservation.getUserName());
			ps.setString(2, reservation.getCarNo());
			ps.setString(3, reservation.getParkName());
			ps.setString(4, reservation.getParkNo());
			ps.setString(5, reservation.getType());
			ps.setString(6, reservation.getCreateTime());
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
	public boolean delete(Reservation reservation) {
		Connection conn=null;
		PreparedStatement ps=null;
		int n=0;
		try {
			conn=MySqlUtil.getConnection();
			String sql="delete from reservation where id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, reservation.getId());
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
	
	public List<Reservation> queryReservationByCarNo(String carNo) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        List<Reservation> listReservation = new ArrayList<Reservation>();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,carNo,parkName,parkNo,type from reservation where carNo=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, carNo);
			    rs= ps.executeQuery();
			    while(rs.next()){
			    	Reservation r = new Reservation();
					r.setId(rs.getInt("id"));
			    	r.setUserName(rs.getString("userName"));
			    	r.setCarNo(rs.getString("carNo"));
			    	r.setParkName(rs.getString("parkName"));
			    	r.setParkNo(rs.getString("parkNo"));
			    	r.setType(rs.getString("type"));
			    	listReservation.add(r);
				}
				return listReservation;
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
	
	public List<Reservation> queryReservationByParkNameAndType(String parkName,String type) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        List<Reservation> listReservation = new ArrayList<Reservation>();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,carNo,parkName,parkNo,type ,createTime from reservation where parkName=? and type=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, parkName);
				ps.setString(2, type);
			    rs= ps.executeQuery();
			    while(rs.next()){
			    	Reservation r = new Reservation();
					r.setId(rs.getInt("id"));
			    	r.setUserName(rs.getString("userName"));
			    	r.setCarNo(rs.getString("carNo"));
			    	r.setParkName(rs.getString("parkName"));
			    	r.setParkNo(rs.getString("parkNo"));
			    	r.setType(rs.getString("type"));
			    	r.setCreateTime(rs.getString("createTime"));
			    	listReservation.add(r);
				}
				return listReservation;
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
	 * 根据用户名查询用户的预约信息
	 * @param userName
	 * @return
	 */
	public List<Reservation> queryReservationByUserName(String userName) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        List<Reservation> listReservation = new ArrayList<Reservation>();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,carNo,parkName,parkNo,type,createTime from reservation where userName=? and isUsed=0";
				ps=conn.prepareStatement(sql);
				ps.setString(1, userName);
			    rs= ps.executeQuery();
			    while(rs.next()){
			    	Reservation r = new Reservation();
					r.setId(rs.getInt("id"));
			    	r.setUserName(rs.getString("userName"));
			    	r.setCarNo(rs.getString("carNo"));
			    	r.setParkName(rs.getString("parkName"));
			    	r.setParkNo(rs.getString("parkNo"));
			    	r.setType(rs.getString("type"));
			    	r.setCreateTime(rs.getString("createTime"));
			    	listReservation.add(r);
				}
				return listReservation;
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
	
	public List<Reservation> queryReservationByUserNameAndType(String userName,String type) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        List<Reservation> listReservation = new ArrayList<Reservation>();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,carNo,parkName,parkNo,type from reservation where userName=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, userName);
			    rs= ps.executeQuery();
			    while(rs.next()){
			    	Reservation r = new Reservation();
					r.setId(rs.getInt("id"));
			    	r.setUserName(rs.getString("userName"));
			    	r.setCarNo(rs.getString("carNo"));
			    	r.setParkName(rs.getString("parkName"));
			    	r.setParkNo(rs.getString("parkNo"));
			    	r.setType(rs.getString("type"));
			    	listReservation.add(r);
				}
				return listReservation;
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
	public List<Reservation> queryAll() {
		Connection  conn= null;
		PreparedStatement ps =null;
		ResultSet rs  =null;
		List<Reservation> listReservation = new ArrayList<Reservation>();
		try {
			conn=MySqlUtil.getConnection();
			String sql = "select id,userName,carNo,parkName,parkNo,type from reservation";
			ps=conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next()){
				Reservation r = new Reservation();
				r.setId(rs.getInt("id"));
		    	r.setUserName(rs.getString("userName"));
		    	r.setCarNo(rs.getString("carNo"));
		    	r.setParkName(rs.getString("parkName"));
		    	r.setParkNo(rs.getString("parkNo"));
		    	r.setType(rs.getString("type"));
		    	listReservation.add(r);
			}
			return listReservation;
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
