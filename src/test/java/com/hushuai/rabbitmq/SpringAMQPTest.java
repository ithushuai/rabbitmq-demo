package com.hushuai.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * created by it_hushuai
 * 2020/3/7 23:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAMQPTest {
    @Resource
    private AmqpTemplate template;

    @Test
    public void testSendMsg() throws InterruptedException {
        String message = "hello spring";
        template.convertAndSend("spring.test.exchange", "insert", message);
        System.out.println("生产者发送消息：" + message);
        Thread.sleep(10000);//等待10s，让测试方法延迟结束，防止消费者未来得及获取消息
    }
}
