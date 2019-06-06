package com.yml.rabbitmq.origin;

import com.rabbitmq.client.ConnectionFactory;
import com.yml.rabbitmq.util.CastUtil;
import com.yml.rabbitmq.util.PropsUtil;

import java.util.Properties;

/**
 * rabbitmq 官网示例，原生java使用rabbitmq
 * @author Liuym
 * @date 2019/1/9 0009
 */
public final class RabbitConfig {
    public static ConnectionFactory getConnect() {
        ConnectionFactory factory = new ConnectionFactory();
        Properties properties = PropsUtil.loadProps("application.properties");
        factory.setHost(properties.getProperty("spring.rabbitmq.host"));
        factory.setPort(CastUtil.castInt(properties.getProperty("spring.rabbitmq.port")));
        factory.setUsername(properties.getProperty("spring.rabbitmq.username"));
        factory.setPassword(properties.getProperty("spring.rabbitmq.password"));
        return factory;
    }

}
