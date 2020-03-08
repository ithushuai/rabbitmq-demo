package com.hushuai.rabbitmq.fanout;

import com.hushuai.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * created by it_hushuai
 * 2020/3/7 22:09
 */
public class Consumer2 {
    private static final String QUEUE_NAME = "fanout_queue_2";
    private static final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 声明exchange，指定类型为direct
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //消费者将队列与交换机进行绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
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
