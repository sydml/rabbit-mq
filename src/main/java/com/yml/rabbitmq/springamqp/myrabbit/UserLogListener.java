package com.yml.rabbitmq.springamqp.myrabbit;

import com.rabbitmq.client.Channel;
import com.yml.rabbitmq.basebean.User;
import com.yml.rabbitmq.util.JsonUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * Created by Yuming-Liu
 * 日期： 2019-01-15
 * 时间： 22:40
 */
@Component
public class UserLogListener {
    @RabbitListener(queues = "log.user.queue", containerFactory = "multiListenerContainer")
    public void consumeUserLogQueue(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        try {
            User user = JsonUtil.fromJson(message, User.class);
            channel.basicAck(deliveryTag, false);
//            channel.basicNack(deliveryTag, false,true);//拒收消息,是否重新入队,如果有下一个消费者,会传给下一个消费者
//            channel.basicReject(deliveryTag,true);
            System.out.println(message);
            System.out.println("已确认");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "log.user.queue.two", containerFactory = "multiListenerContainer")
    public void consumeUserLogQueue2(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) {
        try {
            User user = JsonUtil.fromJson(message, User.class);
            channel.basicAck(deliveryTag, false);
//            channel.basicNack(deliveryTag, false,true);//拒收消息,是否重新入队,如果有下一个消费者,会传给下一个消费者
//            channel.basicReject(deliveryTag,true);
            System.out.println(message);
            System.out.println("已确认2");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
