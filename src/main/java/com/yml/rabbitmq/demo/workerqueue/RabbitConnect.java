package com.yml.rabbitmq.demo.workerqueue;

import com.rabbitmq.client.ConnectionFactory;
import com.yml.rabbitmq.util.CastUtil;
import com.yml.rabbitmq.util.PropsUtil;

import java.util.Properties;

/**
 * @author Liuym
 * @date 2019/1/9 0009
 */
public final class RabbitConnect {
  public static ConnectionFactory getConnect(){
       ConnectionFactory factory = new ConnectionFactory();
       Properties properties = PropsUtil.loadProps("application.properties");
       factory.setHost(properties.getProperty("spring.rabbitmq.host"));
       factory.setPort(CastUtil.castInt(properties.getProperty("spring.rabbitmq.port")));
       factory.setUsername(properties.getProperty("spring.rabbitmq.username"));
       factory.setPassword(properties.getProperty("spring.rabbitmq.password"));
      return factory;
   }

}
