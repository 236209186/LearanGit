package park.daoImp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import park.dao.UserDao;
import park.entity.User;
import park.util.MySqlUtil;

public class UserDaoImp implements UserDao{
	
	
	/**
	 * 判断用户是否登录成功
	 */

	@Override
	public boolean login(User user) {
		Connection  conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		final String test="select 1 from car_user where userName=? and passWord=?";
		try {
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(test);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPassWord());
			rs=ps.executeQuery();
			if(rs.next()){
				return true;
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
		return false;
	}

	@Override
	public boolean modfiy(User user) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			String sql="update car_user set userName=?,realName=?,passWord=?,phone=?,type=? ,count=? where id=?";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getRealName());
			ps.setString(3, user.getPassWord());
			ps.setString(4, user.getPhone());
			ps.setInt(5, user.getType());
			ps.setInt(6, user.getCount());
			ps.setInt(7, user.getId());
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
	public boolean add(User user) {
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			String sql="insert into car_user(userName,realName,passWord,phone,type) values(?,?,?,?,?)";
			conn=MySqlUtil.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getRealName());
			ps.setString(3, user.getPassWord());
			ps.setString(4, user.getPhone());
			ps.setInt(5, user.getType());
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
	public boolean delete(User user) {
		Connection conn=null;
		PreparedStatement ps=null;
		int n=0;
		try {
			conn=MySqlUtil.getConnection();
			String sql="delete from car_user where id=?";
			ps=conn.prepareStatement(sql);
			ps.setInt(1, user.getId());
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
	public User queryUserById(Integer id) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        User user = new User();
        try {
	     	   	//�������ݿ������
				conn=MySqlUtil.getConnection();
				// Ԥ����sql���,Ȼ�����ʹ�ô˶����θ�Ч��ִ�и�sql��䡣
				String sql = "select userName,realName,passWord,phone,type from car_user where id=?";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, id);
			    rs= ps.executeQuery();
			    
			    if(rs.next()){
			    	user.setId(id);
			    	user.setUserName(rs.getString("userName"));
			    	user.setRealName(rs.getString("realName"));
			    	user.setPassWord(rs.getString("passWord"));
			    	user.setPhone(rs.getString("phone"));
			    	user.setType(rs.getInt("type"));
			    	return user;
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
	        return user;
	}

	@Override
	public User queryUserByName(String userName) {
		Connection  conn= null;
        PreparedStatement ps =null;
        ResultSet rs  =null;
        User user = new User();
        try {
	     	   	
				conn=MySqlUtil.getConnection();
				
				String sql = "select id,realName,passWord,phone,type,count from car_user where userName=?";
				ps=conn.prepareStatement(sql);
				ps.setString(1, userName);
			    rs= ps.executeQuery();
			    
			    if(rs.next()){
			    	user.setId(rs.getInt("id"));
			    	user.setUserName(userName);
			    	user.setRealName(rs.getString("realName"));
			    	user.setPassWord(rs.getString("passWord"));
			    	user.setPhone(rs.getString("phone"));
			    	user.setType(rs.getInt("type"));
			    	user.setCount(rs.getInt("count"));
			    	return user;
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

	@Override
	public List<User> queryAll() {
		Connection  conn= null;
		PreparedStatement ps =null;
		ResultSet rs  =null;
		List<User> listUser = new ArrayList<User>();
		try {
			//�������ݿ������
			conn=MySqlUtil.getConnection();
			// Ԥ����sql���,Ȼ�����ʹ�ô˶����θ�Ч��ִ�и�sql��䡣
			String sql = "select id,userName,realName,passWord,phone,type from car_user";
			ps=conn.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("userName"));
				user.setRealName(rs.getString("realName"));
				user.setPassWord(rs.getString("passWord"));
				user.setPhone(rs.getString("phone"));
				user.setType(rs.getInt("type"));
				listUser.add(user);
			}
			return listUser;
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

}
