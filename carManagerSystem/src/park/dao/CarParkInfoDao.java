package park.dao;

import java.util.List;

import park.entity.CarParkInfo;
import park.entity.User;

/**
 * 
 * @authormyz
 * 临时车辆
 */
public interface CarParkInfoDao {
	//修改用户
		public boolean modfiy(CarParkInfo carParkInfo);
		//添加用户
		public boolean add(CarParkInfo carParkInfo);
		//删除用户
		public boolean delete(CarParkInfo carParkInfo);
		//查询所有的用户
		public List<CarParkInfo> queryAll();
		//根据id查询用户
		public CarParkInfo queryCarParkById(Integer id);
		//根据用户名查找用户
		public CarParkInfo queryCarParkBByName(String userName);
	
}
