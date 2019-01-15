package com.yml.rabbitmq.springamqp.myrabbit;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Liuym
 * @date 2019/1/15 0015
 */
@Service("publishService")
public class PublishService {
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private ConfirmCallBackListener confirmCallBackListener;

    @Autowired
    private ReturnCallBackListener returnCallBackListener;

    public void send( String routingKey, Object message) {
        template.setConfirmCallback(confirmCallBackListener );
        template.setReturnCallback(returnCallBackListener);
        template.convertAndSend( routingKey, message);
    }
}

