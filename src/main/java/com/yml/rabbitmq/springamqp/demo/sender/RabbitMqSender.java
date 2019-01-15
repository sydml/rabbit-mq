package com.yml.rabbitmq.springamqp.demo.sender;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Yuming-Liu
 * 日期： 2018-08-04
 * 时间： 12:38
 */
@Component
public class RabbitMqSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private DirectExchange directExchange;

    @Autowired
    private TopicExchange topicExchange;

    //direct模式
    public void directModeSend(String message) {
        rabbitTemplate.convertAndSend(directExchange.getName(),"direct.queue", message);
    }

    /**
     * 可以不绑定交换机
     * @param message
     */
    public void directModeSendTest(String message) {
        rabbitTemplate.convertAndSend("direct.queue", message);
    }

    //topic模式
    public void topicModeSend(String message) {//topic.queue
        rabbitTemplate.convertAndSend(topicExchange.getName(), "ttt.queue.111.234", message);
    }

    public void topicTestModeSend(String message) {//topic.queue.test
        rabbitTemplate.convertAndSend(topicExchange.getName(),"topic.queue.test.exchange", message);
    }

    /**
     *  生产者不绑定交换机
     *  那么只有routingKey,bindingKey和队列名三者名字完全一致的时候(并且无论是哪种类型的交换机通过BindingBuilder创建的Binding,必须要存在一个符合该条件的)才可以发送到队列,否者直接丢弃该生产消息
     * @param message
     */
    public void topicModeSendTest(String message) {
        rabbitTemplate.convertAndSend("topic.queue.test", message);
    }

    //fanout模式
    public void fanoutModeSend(String message) {
        rabbitTemplate.convertAndSend("fanout-exchange", "",message);
    }

}