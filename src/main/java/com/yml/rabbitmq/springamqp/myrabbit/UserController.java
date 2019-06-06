package com.yml.rabbitmq.springamqp.myrabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yml.rabbitmq.basebean.User;
import com.yml.rabbitmq.util.JsonUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Yuming-Liu
 * 日期： 2019-01-15
 * 时间： 20:34
 */
@RestController
public class UserController {

    private static User user = new User("rose", "123456", 18);
    private static User user2 = new User("jack", "123456", 19);

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private Environment env;

    @GetMapping(value = "/log")
    public void login() {
        try {
            String message = JsonUtil.toJson(user);
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.setExchange("topic.exchange");
//            rabbitTemplate.setRoutingKey(env.getProperty("log.user.routing.key.name"));
//            rabbitTemplate.convertAndSend(message);
            rabbitTemplate.setRoutingKey(env.getProperty("log.user.routing.key.name.two"));
            String message2 = JsonUtil.toJson(user2);
            rabbitTemplate.convertAndSend(message2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}