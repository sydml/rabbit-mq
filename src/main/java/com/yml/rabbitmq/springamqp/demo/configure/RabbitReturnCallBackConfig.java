/*
package com.yml.rabbitmq.springamqp.demo.configure;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

*/
/**
 * @author Liuym
 * @date 2019/1/14 0014
 *//*

@Component
public class RabbitReturnCallBackConfig implements RabbitTemplate.ReturnCallback{
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostConstruct
    public void init(){
        //指定 ReturnCallback
        rabbitTemplate.setReturnCallback(this);
    }

    */
/**
     * 通过实现 ReturnCallback 接口，启动消息失败返回，比如路由不到队列时触发回调
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     *//*

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("消息主体 message : "+message);
        System.out.println("消息主体 message : "+replyCode);
        System.out.println("描述："+replyText);
        System.out.println("消息使用的交换器 exchange : "+exchange);
        System.out.println("消息使用的路由键 routing : "+routingKey);
    }
}
*/
