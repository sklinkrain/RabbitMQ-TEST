package Mr.RabbitMq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Mr.R
 * 发布订阅
 */
public class Consumer_publish_aaa {

    private static final String QUEUE_INFORM_AAA = "queue_inform_AAA";
   // private static final String QUEUE_INFORM_BBB = "queue_inform_BBB";
    private static final String EXCHANGE_FANOUT_INFORM = "exchange_fanout_inform";


    public static void main(String[] args) {

        //获取连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置属性值
        connectionFactory.setPort(5672);
        connectionFactory.setHost("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        Connection connection = null;
        Channel channel = null;

        try {
            //获取连接
            connection=connectionFactory.newConnection();
            //获取通道
            channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(QUEUE_INFORM_AAA,true,false,false,null);
            //声明交换机
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            //关联交换机和队列
            channel.queueBind(QUEUE_INFORM_AAA,EXCHANGE_FANOUT_INFORM,"");
            //获取消息
            DefaultConsumer consumer=new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    /**
                     * 当接收到消息后此方法将被调用
                     * @param consumerTag  消费者标签，用来标识消费者的，在监听队列时设置channel.basicConsume
                     * @param envelope 信封，通过envelope
                     * @param properties 消息属性
                     * @param body 消息内容
                     * @throws IOException
                     */
                    //交换机
                    String exchange = envelope.getExchange();
                    //消息id，mq在channel中用来标识消息的id，可用于确认消息已接收
                    long deliveryTag = envelope.getDeliveryTag();
                    //消息内容
                    String message= new String(body,"utf-8");
                    System.out.println("receive message:"+message);
                    System.out.println(exchange);
                    System.out.println(deliveryTag);
                }
            };

            //监听队列
            //参数：String queue, boolean autoAck, Consumer callback
            /**
             * 参数明细：
             * 1、queue 队列名称
             * 2、autoAck 自动回复，当消费者接收到消息后要告诉mq消息已接收，如果将此参数设置为tru表示会自动回复mq，如果设置为false要通过编程实现回复
             * 3、callback，消费方法，当消费者接收到消息要执行的方法
             */
            channel.basicConsume(QUEUE_INFORM_AAA,true,consumer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }


    }

}
