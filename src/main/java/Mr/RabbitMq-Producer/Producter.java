package Mr.RabbitMq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Mr.R
 */
public class Producter {
    public static void main(String[] args) {
        Channel channel=null;
        Connection connection=null;
        //获取连接工厂
        ConnectionFactory connectionFactory=new ConnectionFactory();

        connectionFactory.setPort(5672);
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        try {
            //获取连接
             connection = connectionFactory.newConnection();
            //获取连接通道
             channel = connection.createChannel();
            //声明queue
            channel.queueDeclare("rabbit-test",true,false,false,null);
            //发送消息
            String msg="hello RabbitMQ";
            channel.basicPublish("","rabbit-test",null,msg.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }finally {
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
