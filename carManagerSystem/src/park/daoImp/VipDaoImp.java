package park.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import park.dao.UserDao;
import park.dao.VipDao;
import park.entity.User;
import park.entity.VIp;
import park.util.MyDate;
import park.util.MySqlUtil;

public class VipDaoImp implements VipDao{

	@Override
	public boolean modfiy(VIp vIp) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			String sql="update vip set userName=?,carNo=?,type=?,endTime=?,startTime=?,startTime=?,parkNo=?,location=? where id=?";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, vIp.getUserName());
			ps.setString(2, vIp.getCarNo());
			ps.setInt(3, vIp.getType());
			ps.setString(4, vIp.getEndTime());
			ps.setString(5, vIp.getStartTime());
			ps.setString(6, vIp.getCreateTime());
			ps.setString(7, vIp.getParkNo());
			ps.setString(8, vIp.getLocation());
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
	public boolean add(VIp vIp) {
		Connection conn=null;
		PreparedStatement ps=null;
		try {
			String sql="insert into vip(userName,carNo,type,endTime,startTime,createTime,parkNo,location) values(?,?,?,?,?,?,?,?)";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, vIp.getUserName());
			ps.setString(2, vIp.getCarNo());
			ps.setInt(3, vIp.getType());
			ps.setString(4, vIp.getEndTime());
			ps.setString(5, vIp.getStartTime());
			ps.setString(6, MyDate.getMyDate_2());
			ps.setString(7, vIp.getParkNo());
			ps.setString(8, vIp.getLocation());
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
	public boolean delete(VIp vIp) {
		Connection conn=null;
		PreparedStatement ps=null;
		int n=0;
		try {
			conn=MySqlUtil.getConnection();
			String sql="delete from vip where id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, vIp.getId());
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
	public VIp queryUserByName(String userName,String carNo) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        VIp vIp = new VIp();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,carNo,type,endTime,startTime,createTime,parkNo,location from vip where userName=? and carNo=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, userName);
				ps.setString(2, carNo);
			    rs= ps.executeQuery();
			    
			    if(rs.next()){
			    	vIp.setId(rs.getInt("id"));
			    	vIp.setUserName(userName);
			    	vIp.setCarNo(rs.getString("carNo"));
			    	vIp.setType(rs.getInt("type"));
			    	vIp.setEndTime(rs.getString("endTime"));
			    	vIp.setStartTime(rs.getString("startTime"));
			    	vIp.setCreateTime(rs.getString("createTime"));
			    	vIp.setParkNo(rs.getString("parkNo"));
			    	vIp.setLocation(rs.getString("location"));
			    	return vIp;
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
	
	public VIp queryUserByName(String userName) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        VIp vIp = new VIp();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,carNo,type,endTime,startTime,createTime,parkNo,location from vip where userName=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, userName);
			    rs= ps.executeQuery();
			    
			    if(rs.next()){
			    	vIp.setId(rs.getInt("id"));
			    	vIp.setUserName(userName);
			    	vIp.setCarNo(rs.getString("carNo"));
			    	vIp.setType(rs.getInt("type"));
			    	vIp.setEndTime(rs.getString("endTime"));
			    	vIp.setStartTime(rs.getString("startTime"));
			    	vIp.setCreateTime(rs.getString("createTime"));
			    	vIp.setParkNo(rs.getString("parkNo"));
			    	vIp.setLocation(rs.getString("location"));
			    	return vIp;
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
	
	
	public List<VIp> queryVipByCarNo(String carNo) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        List<VIp> listVIp = new ArrayList<VIp>();
        try {
				conn=MySqlUtil.getConnection();
				String sql = "select id,userName,carNo,type,endTime,startTime,createTime,parkNo,location from vip where carNo=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, carNo);
			    rs= ps.executeQuery();
			    while(rs.next()){
					VIp vIp = new VIp();
			    	vIp.setId(rs.getInt("id"));
			    	vIp.setUserName(rs.getString("userName"));
			    	vIp.setCarNo(rs.getString("carNo"));
			    	vIp.setType(rs.getInt("type"));
			    	vIp.setEndTime(rs.getString("endTime"));
			    	vIp.setStartTime(rs.getString("startTime"));
			    	vIp.setCreateTime(rs.getString("createTime"));
			    	vIp.setParkNo(rs.getString("parkNo"));
			    	vIp.setLocation(rs.getString("location"));
			    	listVIp.add(vIp);
				}
				return listVIp;
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

	@Override
	public List<VIp> queryAll() {
		Connection  conn= null;
		PreparedStatement ps =null;
		ResultSet rs  =null;
		List<VIp> listVIp = new ArrayList<VIp>();
		try {
			conn=MySqlUtil.getConnection();
			String sql = "select id,userName,carNo,type,endTime,startTime,createTime,parkNo,location from vip";
			ps=conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next()){
				VIp vIp = new VIp();
				vIp.setId(rs.getInt("id"));
		    	vIp.setUserName(rs.getString("userName"));
		    	vIp.setCarNo(rs.getString("carNo"));
		    	vIp.setType(rs.getInt("type"));
		    	vIp.setEndTime(rs.getString("endTime"));
		    	vIp.setStartTime(rs.getString("startTime"));
		    	vIp.setCreateTime(rs.getString("createTime"));
		    	vIp.setParkNo(rs.getString("parkNo"));
		    	vIp.setLocation(rs.getString("location"));
				listVIp.add(vIp);
			}
			return listVIp;
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
