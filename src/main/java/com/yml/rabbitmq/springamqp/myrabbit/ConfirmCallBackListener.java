package com.yml.rabbitmq.springamqp.myrabbit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Component;

/**
 * 消息确认监听器
 * @author Liuym
 * @date 2019/1/15 0015
 */

@Component
public class ConfirmCallBackListener implements ConfirmCallback {
    private static final Logger log = LoggerFactory.getLogger(ConfirmCallBackListener.class);

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("消息发送成功:correlationData({}),ack({}),cause({})", correlationData, ack, cause);
        }
    }
}

