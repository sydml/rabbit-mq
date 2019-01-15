package com.yml.rabbitmq.springamqp.myrabbit;

import com.yml.rabbitmq.springamqp.demo.configure.RabbitMqConfig;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * @author Liuym
 * @date 2019/1/15 0015
 */
@Configuration
@EnableRabbit
public class RabbitConfig {


    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Autowired
    private Environment env;

    /**
     * 单一消费者
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory singleListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(1);
        factory.setPrefetchCount(1);
        factory.setTxSize(1);
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

    /**
     * 多个消费者
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory multiListenerContainer(){
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory,connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency",int.class));
        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency",int.class));
        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch",int.class));
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new ConfirmCallBackListener());
        rabbitTemplate.setReturnCallback(new ReturnCallBackListener());
        return rabbitTemplate;
    }

    @Bean
    public Queue logUserQueue(){
        return new Queue(env.getProperty("log.user.queue.name"), true);
    }

    @Bean
    public Queue logUserQueue2(){
        return new Queue(env.getProperty("log.user.queue.name.two"), true);
    }

    /*@Bean(name="logUserExchange")
    public TopicExchange logUserExchange(){
        return new TopicExchange(env.getProperty("log.user.topic.exchange.name"), true, true);
    }*/

    @Bean
    public Binding logUserBind(){
        return BindingBuilder.bind(logUserQueue()).to(new RabbitMqConfig().testDirectExchange()).with(env.getProperty("log.user.routing.key.name"));
    }

    @Bean
    public Binding logUserBind2(){
        return BindingBuilder.bind(logUserQueue2()).to(new RabbitMqConfig().testDirectExchange()).with(env.getProperty("log.user.routing.key.name.two"));
    }
}