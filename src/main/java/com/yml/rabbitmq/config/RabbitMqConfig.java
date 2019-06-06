package com.yml.rabbitmq.config;

import com.yml.rabbitmq.springamqp.myrabbit.ConfirmCallBackListener;
import com.yml.rabbitmq.springamqp.myrabbit.ReturnCallBackListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class RabbitMqConfig {


    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private SimpleRabbitListenerContainerFactoryConfigurer factoryConfigurer;

    @Autowired
    private Environment env;

    /**
     * 基础配置
     */

    /**
     * 单一消费者
     * 开启手动确认模式
     * 开启单个监听线程
     */
    @Bean
    public SimpleRabbitListenerContainerFactory singleListenerContainer() {
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
     * 配置了MessageConverter
     * 开启手动确认模式
     * 开启多个监听线程
     */
    @Bean
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factoryConfigurer.configure(factory, connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.concurrency", int.class));
        factory.setMaxConcurrentConsumers(env.getProperty("spring.rabbitmq.listener.max-concurrency", int.class));
        factory.setPrefetchCount(env.getProperty("spring.rabbitmq.listener.prefetch", int.class));
        return factory;
    }

    /**
     * rabbitTemplate 基础配置
     * 配置了发送消息的回调确认
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //强制开启回调确认
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback(new ConfirmCallBackListener());
        rabbitTemplate.setReturnCallback(new ReturnCallBackListener());
        return rabbitTemplate;
    }

    /**
     * 配置队列
     * 持久化开启
     */
    @Bean
    public Queue logUserQueue() {
        return new Queue(env.getProperty("log.user.queue.name"), true);
    }

    @Bean
    public Queue logUserQueue2() {
        return new Queue(env.getProperty("log.user.queue.name.two"), true);
    }


    //由于另一个demo已经配置过这个交换机,所以这里不能再次配置
    /*@Bean(name="logUserExchange")
    public TopicExchange logUserExchange(){
        return new TopicExchange(env.getProperty("log.user.topic.exchange.name"), true, true);
    }*/

    /**
     * 接收消息时配置队列和交换机的路由 routingKey，确定队列如何通过路由规则获取消息
     * 发送消息时配置rabbitTemplate和交换机的bindingKey
     */
    @Bean
    public Binding logUserBind() {
        return BindingBuilder.bind(logUserQueue()).to(new RabbitMqConfig().testDirectExchange()).with(env.getProperty("log.user.routing" +
                ".key.name"));
    }

    @Bean
    public Binding logUserBind2() {
        return BindingBuilder.bind(logUserQueue2()).to(new RabbitMqConfig().testDirectExchange()).with(env.getProperty("log.user.routing" +
                ".key.name.two"));
    }

    /**
     * 基础绑定规则配置示例
     *
     * 一 direct模式上,routingKey和bindingKey必须保持一致才可以将消息发送到指定的队列上,是一对一的模式
     *
     * 二 在topic模式上,bindingKey可以以通配符的方式描述,routingKey是指定的字符,是多对多的方式
     *
     * 三 交换机绑定队列时用的bindingKey 和 发送消息时候用的routingKey保持一致（direct exchange）或者匹配规则一致（topic exchange）就可以将消息发送到该队列上,
     * 其中fanoutExchange是广播，只绑定队列即可，不用指定bindingKey
     * 这里使用的绑定交换机的方法给人误区:with里面的参数其实叫做:bindingKey
     * 只有生产者在生产消息时指定的是routingKey进行发送消息
     * bindingKey支持通配符,使用场景如下(*和#号都可以代表空的单词)
     * 如:
     * (1)一个生产者匹配多个交换机,进而将消息发送多个队列上
     *    生产者: P1->routingKey :1.2.3
     *    交换机: X ->bindingKey :1.*.3->Q1, *.2.3->Q2, 1.2.*->Q3, 1.#->Q4, #.3->Q5 ...
     *    这样一个生产者就可以将消息发送多个队列上
     * (2)多个生产者匹配一个交换机,进而将多个生产者生产的消息发送到同一个队列中
     *    生产者: P1->routingKey :1.2, P2->1.2.3
     *    交换机: X ->bindingKey :1.#->Q
     *    这样多个生产者就可以将他们生产的消息发送到同一个队列中
     * 三 生产者不绑定交换机的时候:
     */


    // Direct 模式

    /**
     * 定义direct队列
     *
     * @return
     */
    @Bean(name = "direct.queue")
    public Queue directQueue() {
        return new Queue("direct.queue");
    }

    /**
     * 定义direct交换机
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct.exchange");
    }


    /**
     * 绑定队列到交换机
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bingingDirectExchange(@Qualifier("direct.queue") Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("direct.queue");
    }


    //Topic 模式


    /**
     * 定义topic队列
     *
     * @return
     */
    @Bean(name = "topic.queue")
    public Queue topicQueue() {
        return new Queue("topic.queue");
    }

    /**
     * 定义另一个topic队列
     *
     * @return
     */
    @Bean(name = "topic.queue.test")
    public Queue topicQueueTest() {
        return new Queue("topic.queue.test");
    }

    /**
     * 定义topic交换机
     *
     * @return
     */
    @Bean
    public TopicExchange testDirectExchange() {
        return new TopicExchange("topic.exchange");
    }

    /**
     * routingKey,和bindingKey 在direct Exchange 上是一致的，但不必把对列名也写成一致的
     * <p>
     * 绑定队列到对应的交换机
     * 注意binding时两种通配符
     * 这里使用的方法给人误区:with后面其实叫做:bindingKey
     * bindingKey的规则是点号分割
     * #:可以匹配多个单词
     * *:只能匹配一个单词
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingTopicExchange(@Qualifier("topic.queue") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*.queue.#");
    }

    /**
     * 绑定另一个队列到同一个交换机
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeTest(@Qualifier("topic.queue.test") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("topic.queue.test.*");
    }


    /**
     * 另一个绑定
     *
     * @return
     */
    @Bean
    public Binding bindingTopicWithoutWildcardExchangeTest(@Qualifier("topic.queue.test") Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("topic.queue.test");
    }


    //Fanout 模式
    @Bean(name = "fanout-queue1")
    public Queue fanoutQueue1() {
        return new Queue("fanout-queue1");
    }

    @Bean(name = "fanout-queue2")
    public Queue fanoutQueue2() {
        return new Queue("fanout-queue2");
    }

    @Bean(name = "fanout-queue3")
    public Queue fanoutQueue3() {
        return new Queue("fanout-queue3");
    }


    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout-exchange");
    }

    @Bean
    public Binding fanoutBingingQueue1(@Qualifier("fanout-queue1") Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding fanoutBingingQueue2(@Qualifier("fanout-queue2") Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding fanoutBingingQueue3(@Qualifier("fanout-queue3") Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

}