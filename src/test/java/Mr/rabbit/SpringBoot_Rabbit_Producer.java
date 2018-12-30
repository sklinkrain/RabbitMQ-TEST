package Mr.rabbit;

import Mr.Configuration.RabbitConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBoot_Rabbit_Producer {

   @Autowired
    RabbitTemplate rabbitTemplate;

   @Test
    public void testSend(){

        String str="SpringBoot+RabbitMQ";

        rabbitTemplate.convertAndSend(RabbitConfiguration.EXCHANGE_TOPICS_INFORM,"inform.ss.aaa",str);

    }




}
