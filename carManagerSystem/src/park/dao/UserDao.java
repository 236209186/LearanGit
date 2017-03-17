package park.dao;

import java.util.List;

import park.entity.User;

/**
 * 
 * @author Gux
 *
 */
public interface UserDao {
	//登录判断
	public boolean login(User user);
	//修改用户
	public boolean modfiy(User user);
	//添加用户
	public boolean add(User user);
	//删除用户
	public boolean delete(User user);
	//查询所有的用户
	public List<User> queryAll();
	//根据id查询用户
	public User queryUserById(Integer id);
	//根据用户名查找用户
	public User queryUserByName(String name);

}
