package com.hushuai.rabbitmq.basic;

import com.hushuai.rabbitmq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;

/**
 * created by it_hushuai
 * 2020/3/7 19:32
 */
public class Send {
    private static final String QUEUE_NAME = "basic_queue";

    public static void main(String[] args) throws Exception {

        //消息发送端与mq服务创建连接
        Connection connection = ConnectionUtil.getConnection();
        //建立通道
        Channel channel = connection.createChannel();
        //声明队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        String message = "hello world";
        //MessageProperties.PERSISTENT_TEXT_PLAIN,真的字符串类型的消息进行持久化
        try {
//            channel.txSelect();
            channel.confirmSelect();
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    //消息被成功处理
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    //消息丢失
                }
            });
//            channel.txCommit();
        }catch (Exception e){
//            channel.txRollback();
        }

        System.out.println("生产者已发送：" + message);
        channel.close();
        connection.close();
    }
}
