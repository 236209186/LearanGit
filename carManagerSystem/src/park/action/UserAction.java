package park.action;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import park.daoImp.UserDaoImp;
import park.entity.User;
import park.util.Constants;
import park.util.Dumper;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{
	
	private User user;
	private String errorInfo;
	private UserDaoImp userDaoImp = new UserDaoImp();
	private String id;
	private String para;
	private List<User> userList = new ArrayList<User>();
	private String userName;
	private String adminCommand;//管理员命令
	private String employeeCommand;
	
	
	/**
	 * 用户登录
	 * @return
	 */
	public String userLogin(){
		
		HttpSession session = ServletActionContext.getRequest().getSession();
		if (null != session.getAttribute(Constants.USER_NAME)) {
			user = userDaoImp.queryUserByName(session.getAttribute(Constants.USER_NAME).toString());
			return SUCCESS;
		} else if(null != user && userDaoImp.login(user)){	
			user = userDaoImp.queryUserByName(user.getUserName());
			session.setAttribute(Constants.USER_NAME, user.getUserName());
			session.setAttribute(Constants.TYPE, user.getType()+"");
			session.setAttribute(Constants.COUNT, user.getCount()+"");
			return SUCCESS;
		}else{
			errorInfo = "用户名或者密码错误";
			return "input";
		}
	}
	
	
	/**
	 * 用户退出
	 * @return
	 */
	public String logOut(){
		HttpSession  session = (HttpSession) ServletActionContext.getRequest().getSession();
		Dumper.dump(session);
		System.out.println(session.isNew());
		session.removeAttribute(Constants.USER_NAME);
		session.removeAttribute(Constants.TYPE);
		//session.invalidate();
		return SUCCESS;
	}
	
	/**
	 * 用户注册
	 * @return
	 */
	public String register(){
		if (user.getId() > 0) {
			if (userDaoImp.modfiy(user)){
				para = "1";
			} else {
				para = "0";
			}
		}
		else if (userDaoImp.add(user)) {
			para = "1";
		} else {
			para = "0";
		}
		return SUCCESS;
	}

	/**
	 * 删除用户
	 * @return
	 */
	public String deleteUser(){
		User user = new User();
		user.setId(Integer.parseInt(id));
		if (userDaoImp.delete(user)) {
			para="5";
		} else {
			System.out.println("======删除失败=====");
			para="6";
		}
		return SUCCESS;
	}
	
	/**
	 * 修改用户
	 * @return
	 */
	public String modifyUser(){
		user = userDaoImp.queryUserById(Integer.parseInt(id));
		return SUCCESS;
	}

	
	/**
	 *根据用户名查询这个用户是否存在 
	 */
	public void queryUserByNameAjax(){
		if (userName.length() > 0) {
			User user = userDaoImp.queryUserByName(userName);
			PrintWriter printWriter = null; 
			try {
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("utf-8");
				printWriter = response.getWriter();
				if (null != user) {
					printWriter.write("true");
				} else {
					printWriter.write("false");
				}
				printWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();

			} finally{
				printWriter.close();
			}

		}
	}
	
	
	/**
	 * 判断管理员命令
	 */
	public void queryAdminCommand(){
		if (adminCommand.length() > 0) {
			//User user = userDaoImp.queryUserByName(userName);
			PrintWriter printWriter = null; 
			try {
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("utf-8");
				printWriter = response.getWriter();
				if ("scott".equals(adminCommand.trim())) {
					printWriter.write("true");
				} else {
					printWriter.write("false");
				}
				printWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();

			} finally{
				printWriter.close();
			}

		}
	}
	
	/**
	 * 判断员工命令
	 */
	public void queryemployeeCommand(){
		if (employeeCommand.length() > 0) {
			//User user = userDaoImp.queryUserByName(userName);
			PrintWriter printWriter = null; 
			try {
				HttpServletResponse response = ServletActionContext.getResponse();
				response.setCharacterEncoding("utf-8");
				printWriter = response.getWriter();
				if ("tiger".equals(employeeCommand.trim())) {
					printWriter.write("true");
				} else {
					printWriter.write("false");
				}
				printWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();

			} finally{
				printWriter.close();
			}

		}
	}
	
	/**
	 * 根据用户名查询用户
	 * @return
	 */
	public String queryUserByName(){
		String userName = (String) ServletActionContext.getRequest().getSession().getAttribute(Constants.USER_NAME);
		User user = userDaoImp.queryUserByName(userName);
		if (user.getType() == 0) {
			userList.add(user);
		} else {
			userList = userDaoImp.queryAll(); 
		}
		return SUCCESS;
	}
	
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAdminCommand() {
		return adminCommand;
	}
	public void setAdminCommand(String adminCommand) {
		this.adminCommand = adminCommand;
	}

	

}
