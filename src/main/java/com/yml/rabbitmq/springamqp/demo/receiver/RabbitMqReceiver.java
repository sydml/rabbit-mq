package com.yml.rabbitmq.springamqp.demo.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * rabbitmq 官网amqp示例
 * Created by Yuming-Liu
 * 日期： 2018-08-04
 * 时间： 12:41
 * 消费者
 */

@Component
public class RabbitMqReceiver {

    /*@RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "topic.exchange",durable = "true",type = "topic"),
            value = @Queue(value = "consumer_queue",durable = "true"),
            key = "key.#"
    ))*/

    @RabbitListener(queues = "direct.queue")
    public String directReceive(String message) {
        return message;
    }

    @RabbitListener(queues = "topic.queue")
    public void topicReceive(String message) {
        System.out.println("接收到的测试消息是:" + message);
    }

    @RabbitListener(queues = "topic.queue.test")
    public void topicTestReceive(String message) {
        System.out.println("接收到的测试消息是:" + message);
    }

    @RabbitListener(queues = {"fanout-queue1", "fanout-queue2", "fanout-queue3"})
    public void fanoutQueue1Receive(String message) {
        System.out.println("接收到的测试消息是:" + message);
    }
}
