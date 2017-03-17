package park.dao;

import java.util.List;

import park.entity.ParkInfoSet;

/**
 * ]
 * @author Gux
 *
 */
public interface ParkInfoSetDao {
	//修改用户
	public boolean modfiy(ParkInfoSet parkInfoSet);
	//添加用户
	public boolean add(ParkInfoSet parkInfoSet);
	//删除用户
	public boolean delete(ParkInfoSet parkInfoSet);
	//查询所有的用户
	public List<ParkInfoSet> queryAll();
	//根据id查询用户
	public ParkInfoSet queryParkInfoById(Integer id);
	//根据用户名查找用户
	public ParkInfoSet queryParkInfoByName(String parkName);

}
