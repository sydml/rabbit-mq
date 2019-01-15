package com.yml.rabbitmq.origin.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Liuym
 * @date 2019/1/7 0007
 */
@RequestMapping("/message")
public interface IRabbitService {
    @RequestMapping(value="/send", method= RequestMethod.POST)
    @ResponseBody
    void sendMessage(String message);

    @RequestMapping(value="/get", method= RequestMethod.GET)
    @ResponseBody
    String getMessage() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}
