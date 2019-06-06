package com.yml.rabbitmq.springamqp.myrabbit;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * amqp 消息消费处理类
 * Created by Yuming-Liu
 * 日期： 2019-01-15
 * 时间： 20:55
 */
@Component
@RabbitListener(queues = "log.user.queue", containerFactory = "multiListenerContainer")
public class CommonMqListener {

    /**
     * 监听消费用户日志
     *
     * @param message
     */

    /*public void consumeUserLogQueue(String message) {
        try {
            User user = JsonUtil.fromJson(message, User.class);
            System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 接收消息确认策略
     *
     * @param message
     * @param channel
     * @param tag
     */
    @RabbitHandler
    public void processMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        System.out.println(message);
        try {
            channel.basicAck(tag, false);            // 确认消息,如果确认过,即便后面重新拒收入队,下一次也只能消费一次
//            channel.basicNack(tag, false,true);//拒收消息,是否重新入队,如果有下一个消费者,会传给下一个消费者
//            channel.basicReject(tag, true);
            System.out.println("confirm over...");
        } catch (Exception e) {
            try {
                channel.basicNack(tag, false, true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    /*@RabbitHandler
    public void processMessage2(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        System.out.println(message);
        try {

            channel.basicAck(tag,false);            // 确认消息,如果确认过,即便后面重新拒收入队,下一次也只能消费一次
//            channel.basicNack(tag, false,true);//拒收消息,是否重新入队,如果有下一个消费者,会传给下一个消费者
//            channel.basicReject(tag, true);
            System.out.println("confirm over 2...");
        } catch (Exception e) {
            try {
                channel.basicNack(tag, false,true);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }*/
}