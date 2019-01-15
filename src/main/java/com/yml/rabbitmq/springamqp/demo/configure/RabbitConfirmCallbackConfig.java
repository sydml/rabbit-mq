/*
package com.yml.rabbitmq.springamqp.demo.configure;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

*/
/**
 * @author Liuym
 * @date 2019/1/14 0014
 *//*

@Component
public class RabbitConfirmCallbackConfig implements RabbitTemplate.ConfirmCallback{
    */
/**
     * 消息发送确认
     *
     *//*

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @PostConstruct
    public void init(){
        //指定 ConfirmCallback
        rabbitTemplate.setConfirmCallback(this);
    }
    */
/**
     * 通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，也就是只确认是否正确到达 Exchange 中
     * 可能和网络有关，网络失败导致消息发送失败
     *//*

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("消息唯一标识："+correlationData);
        System.out.println("确认结果："+ack);
        System.out.println("失败原因："+cause);
    }
}
*/
