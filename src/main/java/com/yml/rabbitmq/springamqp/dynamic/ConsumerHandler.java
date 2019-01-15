package com.yml.rabbitmq.springamqp.dynamic;

/**
 * @author Liuym
 * @date 2019/1/14 0014
 */
public class ConsumerHandler {
    public void handleMessage(String text) {
        System.out.println("接收到消息：--------------------------: " + text);
    }

}
