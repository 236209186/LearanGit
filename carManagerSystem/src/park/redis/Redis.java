package park.redis;

import redis.clients.jedis.Jedis;

public class Redis {
	
	private static Jedis jedis=null;
	
	
	/**
	 * 创建redis客户端，并解决并发访问的问题
	 * @return
	 */
	public synchronized static Jedis createJedis(){
		
		/**
		 * 采用懒加载的思想，为空的时候才创建
		 */
		if(jedis==null){
			
			jedis = new Jedis("192.168.92.131", 6379);
		}
		
		return jedis;
		
		
	}

}
