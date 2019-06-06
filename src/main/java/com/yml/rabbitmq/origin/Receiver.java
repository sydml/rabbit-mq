package com.yml.rabbitmq.origin;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * rabbitmq 官网示例，原生java使用rabbitmq
 * @author Liuym
 * @date 2019/1/9 0009
 */
public class Receiver {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void receiveMessage() throws Exception {
        ConnectionFactory factory = RabbitConfig.getConnect();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Receiver Received '" + message + "'");
            try {
                doWork(message);
            } finally {
                System.out.println("Receiver Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });

    }

    private static void doWork(String message) {
        System.out.println("接收到的消息：" + message);
    }
}
