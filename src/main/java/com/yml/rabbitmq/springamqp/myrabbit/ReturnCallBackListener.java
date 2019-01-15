package com.yml.rabbitmq.springamqp.myrabbit;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ReturnCallback;
import org.springframework.stereotype.Component;

/**
 * @author Liuym
 * @date 2019/1/15 0015
 */

@Component
public class ReturnCallBackListener implements ReturnCallback{
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("++++++++++ReturnCallBackListener.returnedMessage.run+++++++++++++++++");
        System.out.println("return--message:"+new String(message.getBody())+",replyCode:"+replyCode+",replyText:"+replyText+",exchange:"+exchange+",routingKey:"+routingKey);
    }
}

