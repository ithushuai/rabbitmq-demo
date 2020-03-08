package com.hushuai.rabbitmq.topics;

import com.hushuai.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * created by it_hushuai
 * 2020/3/7 23:00
 */
public class Send {
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为direct
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC, true);
        String message = "新增一个订单";
        //生产者发送消息时，设置消息的Routing Key:"insert"
        channel.basicPublish(EXCHANGE_NAME, "entity.item.insert", MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
        System.out.println("生产者发送消息：" + message);
        channel.close();
        connection.close();
    }
}
