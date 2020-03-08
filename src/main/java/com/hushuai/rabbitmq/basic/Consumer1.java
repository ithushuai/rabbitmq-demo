package com.hushuai.rabbitmq.basic;

import com.hushuai.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * created by it_hushuai
 * 2020/3/7 19:38
 */
public class Consumer1 {

    private static final String QUEUE_NAME = "basic_queue";

    public static void main(String[] args) throws Exception {
        //消息消费者与mq服务建立连接
        Connection connection = ConnectionUtil.getConnection();
        //建立通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            // 获取消息，并且处理，这个方法类似事件监听，如果有消息的时候，会被自动调用
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                // body 即消息体
                String msg = new String(body);
                System.out.println("消费者1接收到消息：" + msg);
//                System.out.println(1/0);//模拟消费过程发生错误
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}
