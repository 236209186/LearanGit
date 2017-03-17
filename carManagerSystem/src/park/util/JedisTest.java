package park.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import park.redis.Redis;
import redis.clients.jedis.Jedis;

public class JedisTest {
	
	
	public static void main(String[] args) {
		
				Jedis jedis = Redis.createJedis();
				Map<String, String> map = new HashMap<String, String>();
				map.put("age", "18");
				map.put("sex", "1");
				Gson gson = new Gson();
				String string = gson.toJson(map);
				
				System.out.println(string);
				//jedis.set("Index", string);
				//jedis.expire("Index", 20);
				System.out.println("过期时间：");
				System.out.println(jedis.pttl("Index")+"");
				System.out.println(jedis.get("Index"));
				System.out.println(jedis.get("guxu"));
				jedis.close();
				
	}

}
