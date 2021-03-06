package Mr.RabbitMq;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Mr.R
 * 发布订阅
 */
public class Producer_Publish {

    private static final String QUEUE_INFORM_AAA = "queue_inform_AAA";
    private static final String QUEUE_INFORM_BBB = "queue_inform_BBB";
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
            connection = connectionFactory.newConnection();
            //获取通道
            channel = connection.createChannel();
            //声明队列
            /*  参数明细
              1、queue 队列名称
              2、durable 是否持久化，如果持久化，mq重启后队列还在
              3、exclusive 是否独占连接，队列只允许在该连接中访问，如果connection连接关闭队列则自动删除, 如果将此参数设置true可用于临时队列的创建
              4、autoDelete 自动删除，队列不再使用时是否自动删除此队列，如果将此参数和exclusive参数设置为true就可以实现临时队列（队列不用了就自动删除）
              5、arguments 参数，可以设置一个队列的扩展参数，比如：可设置存活时间
            */
            channel.queueDeclare(QUEUE_INFORM_AAA, true, false, false, null);
            channel.queueDeclare(QUEUE_INFORM_BBB, true, false, false, null);
            //声明交换机
            /**
             * 参数明细：
             * 1、交换机的名称
             * 2、交换机的类型
             * fanout：对应的rabbitmq的工作模式是 publish/subscribe
             */
            channel.exchangeDeclare(EXCHANGE_FANOUT_INFORM, BuiltinExchangeType.FANOUT);
            //进行交换机和队列绑定
            //参数：String queue, String exchange, String routingKey
            /**
             * 参数明细：
             * 1、queue 队列名称
             * 2、exchange 交换机名称
             * 3、routingKey 路由key，作用是交换机根据路由key的值将消息转发到指定的队列中，在发布订阅模式中调协为空字符串
             */
            channel.queueBind(QUEUE_INFORM_AAA,EXCHANGE_FANOUT_INFORM,"");
            channel.queueBind(QUEUE_INFORM_BBB,EXCHANGE_FANOUT_INFORM,"");
            for (int i = 0; i < 5; i++) {
                String str = "工作队列";
                //发送消息
                //参数：String exchange, String routingKey, BasicProperties props, byte[] body
                /**
                 * 参数明细：
                 * 1、exchange，交换机，如果不指定将使用mq的默认交换机（设置为""）
                 * 2、routingKey，路由key，交换机根据路由key来将消息转发到指定的队列，如果使用默认交换机，routingKey设置为队列的名称
                 * 3、props，消息的属性
                 * 4、body，消息内容
                 */
                channel.basicPublish(EXCHANGE_FANOUT_INFORM, "", null, str.getBytes());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
