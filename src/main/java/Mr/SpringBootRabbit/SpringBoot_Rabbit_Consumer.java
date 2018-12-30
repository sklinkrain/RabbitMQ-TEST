package Mr.SpringBootRabbit;

import Mr.Configuration.RabbitConfiguration;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
 * @author Mr.R
 */
@Component
public class SpringBoot_Rabbit_Consumer {
    //监听AAA队列

    @RabbitListener(queues = {RabbitConfiguration.QUEUE_INFORM_AAA})
    public void receive_aaa(String msg, Message message, AMQP.Channel channel) {

        System.out.println(msg);

    }

}
