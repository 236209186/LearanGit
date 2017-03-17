package park.dao;

import java.util.List;

import park.entity.User;
import park.entity.VIp;

/**
 * 
 * @author Gux
 *
 */
public interface VipDao {
	//修改用户
	public boolean modfiy(VIp vIp);
	//添加用户
	public boolean add(VIp vIp);
	//删除用户
	public boolean delete(VIp vIp);
	//查询所有的用户
	public List<VIp> queryAll();
	//根据用户名查找用户
	public VIp queryUserByName(String userName,String carNo);

}
