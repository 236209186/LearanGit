package park.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 * @author Gux
 *
 */
public class MySqlUtil {
	static String url=null;
	static String driver=null;
	static String username=null;
	static String password=null;
	static Connection conn=null;
	static{
		try {
			Properties pro=new Properties();
			try {
				pro.load(MySqlUtil.class.getClassLoader().getResourceAsStream("test.properties"));
			    url=pro.getProperty("url");
			    username=pro.getProperty("username");
			    password=pro.getProperty("password");
			    driver=pro.getProperty("driver");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	public static Connection getConnection() throws SQLException{
			conn = DriverManager.getConnection(url,username,password);
		return conn;
	}
	
	public static void closeConection(Connection conn,Statement stat,ResultSet rs){
			try {
				if(rs!=null)rs.close();
				if(stat!=null)stat.close();
				if(conn!=null)conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public static void closeConection(Connection conn,Statement stat){
		closeConection(conn, stat,null);
	}
	public static void closeConection(Connection conn){
		closeConection(conn, null,null);
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(getConnection());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
