package com.yml.rabbitmq.demo.workerqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * @author Liuym
 * @date 2019/1/9 0009
 */
public class Worker {
    private static final String TASK_QUEUE_NAME = "direct.queue";

    public static void receiveMessage() throws Exception {
        ConnectionFactory factory = RabbitConnect.getConnect();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Worker Received '" + message + "'");
            try {
                doWork(message);
            } finally {
                System.out.println("Worker Done");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        };
        channel.basicConsume(TASK_QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });

    }

    private static void doWork(String task) {
        for (char ch : task.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException _ignored) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
