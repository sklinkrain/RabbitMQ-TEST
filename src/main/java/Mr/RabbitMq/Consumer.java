package Mr.RabbitMq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Mr.R
 */
public class Consumer {

    public static void main(String[] args) {

        DefaultConsumer consumer = null;
        Channel channel = null;
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setVirtualHost("/");
        connectionFactory.setPassword("guest");
        connectionFactory.setUsername("guest");
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);

        try {
            Connection connection = connectionFactory.newConnection();

           channel = connection.createChannel();

            channel.queueDeclare("rabbit-test", true, false, false, null);

            consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                    //交换机                 
                    String exchange = envelope.getExchange();
                    //路由key                
                    String routingKey = envelope.getRoutingKey();
                    //消息id                
                    long deliveryTag = envelope.getDeliveryTag();
                    //消息内容                
                    String msg = new String(body, "UTF-8");
                    System.out.println(msg);
                }
            };

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        try {
            channel.basicConsume("rabbit-test", true, consumer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
