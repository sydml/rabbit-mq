package com.yml.rabbitmq.springamqp.dynamic;

import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * @author Liuym
 * @date 2019/1/14 0014
 */
public class ConsumerSimpleMessageListenerContainer extends SimpleMessageListenerContainer {

    public void startConsumers() throws Exception {
        super.doStart();
    }
}
