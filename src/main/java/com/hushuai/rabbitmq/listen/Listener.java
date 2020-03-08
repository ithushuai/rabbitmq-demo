package com.hushuai.rabbitmq.listen;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * created by it_hushuai
 * 2020/3/7 23:47
 */
@Component
public class Listener {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "spring.test.queue", durable = "false"),
                    exchange = @Exchange(value = "spring.test.exchange", type = ExchangeTypes.DIRECT),
                    key = "insert"
            )
    )
    public void listen(String msg){
        System.out.println("消费者接受到消息：" + msg);
    }
}
