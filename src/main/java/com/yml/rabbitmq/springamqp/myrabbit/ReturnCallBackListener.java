package com.yml.rabbitmq.springamqp.myrabbit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Component;

/**
 * @author Liuym
 * @date 2019/1/15 0015
 */

@Component
public class ReturnCallBackListener implements ReturnCallback{
    private static final Logger log= LoggerFactory.getLogger(ReturnCallBackListener.class);

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
    }
}

