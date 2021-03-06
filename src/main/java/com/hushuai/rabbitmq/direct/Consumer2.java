package com.hushuai.rabbitmq.direct;

import com.hushuai.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * created by it_hushuai
 * 2020/3/7 22:37
 */
public class Consumer2 {
    private static final String QUEUE_NAME = "direct_queue_2";
    private static final String EXCHANGE_NAME = "direct_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //消费者声明自己的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //消费者将队列与交换机进行绑定，并且设置Routing Key:"delete"
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "delete");
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException
            {
                String msg = new String(body);
                System.out.println("消费者2获取到消息：" + msg);
            }
        });
    }
}
