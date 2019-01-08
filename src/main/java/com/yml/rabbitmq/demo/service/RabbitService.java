package com.yml.rabbitmq.demo.service;

import com.yml.rabbitmq.client.RabbitMqClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Liuym
 * @date 2019/1/7 0007
 */
@Service
public class RabbitService implements IRabbitService{

    @Autowired
    private RabbitMqClient rabbitMqClient;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String message) {
        rabbitMqClient.directModeSend(message);
    }

    @Override
    public String getMessage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object o = rabbitTemplate.receiveAndConvert("direct.queue");
        return o == null ? null : o.toString();
//        Method method = receive.getClass().getDeclaredMethod("getBodyContentAsString");
//        method.setAccessible(true);
//        Object invoke = method.invoke(receive);
//        return invoke.toString();
    }


}
