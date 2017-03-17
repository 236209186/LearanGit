package park.mq;

import park.redis.Redis;
import redis.clients.jedis.Jedis;

import com.google.gson.Gson;

public class ParkHandler {

    //private static final ObjectMapper MAPPER = new ObjectMapper();

  
    private Jedis jedis = Redis.createJedis();
   

    public void execute(String msg) {
        try {
        	
            this.jedis.del(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void test(String message){
    	System.out.println(message);
    	System.out.println("打印！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
    	System.out.println("打印！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
    	System.out.println("打印！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
    	System.out.println("打印！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
    	System.out.println("打印！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
    }
}
