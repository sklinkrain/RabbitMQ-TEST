package Mr.Configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Mr.R
 * 配置rabbit配置文件
 */
@Configuration
public class RabbitConfiguration {

    public static final String QUEUE_INFORM_AAA = "queue_inform_aaa";
    public static final String QUEUE_INFORM_BBB = "queue_inform_bbb";
    public static final String EXCHANGE_TOPICS_INFORM="exchange_springBoot_inform";
    public static final String ROUTINGKEY_AAA="inform.#.aaa.#";
    public static final String ROUTINGKEY_BBB="inform.#.bbb.#";

    //声明交换机
    @Bean(EXCHANGE_TOPICS_INFORM)
    public Exchange EXCHANGE_TOPICS_INFORM(){
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(EXCHANGE_TOPICS_INFORM).durable(true).build();
    }

    //声明QUEUE_INFORM_EMAIL队列
    @Bean(QUEUE_INFORM_AAA)
    public Queue QUEUE_INFORM_AAA(){
        return new Queue(QUEUE_INFORM_AAA);
    }

    //声明QUEUE_INFORM_SMS队列
    @Bean(QUEUE_INFORM_BBB)
    public Queue QUEUE_INFORM_BBB(){
        return new Queue(QUEUE_INFORM_BBB);
    }

    //ROUTINGKEY_AAA队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_QUEUE_INFORM_AAA(@Qualifier(QUEUE_INFORM_AAA) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_AAA).noargs();
    }

    //ROUTINGKEY_BBB队列绑定交换机，指定routingKey
    @Bean
    public Binding BINDING_ROUTINGKEY_BBB(@Qualifier(QUEUE_INFORM_BBB) Queue queue, @Qualifier(EXCHANGE_TOPICS_INFORM) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_BBB).noargs();
    }




}
