package com.hushuai.rabbitmq.topics;

import com.hushuai.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * created by it_hushuai
 * 2020/3/7 23:03
 */
public class Consumer1 {
    private static final String QUEUE_NAME = "topic_queue_1";
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        //消费者声明自己的队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        //消费者将队列与交换机进行绑定，并且设置Routing Key:"insert"
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "#.insert");
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body)
                    throws IOException
            {
                String msg = new String(body);
                System.out.println("消费者1获取到消息：" + msg);
            }
        });
    }
}
