package park.util;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
	
	
    public void test() {
        AbstractApplicationContext ctx = new ClassPathXmlApplicationContext(
                "classpath:/applicationContext-spring.xml.xml");
        //RabbitMQ模板
        RabbitTemplate template = ctx.getBean(RabbitTemplate.class);
        //发送消息
        template.convertAndSend("Hello, world!");
      
        ctx.destroy(); //容器销毁
    }
	
}
