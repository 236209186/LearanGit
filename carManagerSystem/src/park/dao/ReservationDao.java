package park.dao;

import java.util.List;

import park.entity.Reservation;


public interface ReservationDao {
	//修改用户
	public boolean modfiy(Reservation reservation);
	//添加用户
	public boolean add(Reservation reservation);
	//删除用户
	public boolean delete(Reservation reservation);
	//查询所有的用户
	public List<Reservation> queryAll();
	//根据用户名和消费类型查询用户的预约信息
	public List<Reservation> queryReservationByUserNameAndType(String userName,String type);

}
