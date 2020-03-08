package com.hushuai.rabbitmq.workqueue;

import com.hushuai.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

/**
 * created by it_hushuai
 * 2020/3/7 20:46
 */
public class Send {
    private static final String QUEUE_NAME = "work_queue";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // 循环发布任务
        for (int i = 1; i <= 20; i++) {
            // 消息内容
            String message = "task .. " + i;
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println("生产者发送消息：" + message);
            Thread.sleep(50);
        }
        channel.close();
        connection.close();
    }
}
