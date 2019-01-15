package com.yml.rabbitmq.origin.workerqueue;

import com.rabbitmq.client.*;

/**
 * @author Liuym
 * @date 2019/1/9 0009
 */
public class Sender {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void sendMessage(String message) {

        ConnectionFactory factory = RabbitConfig.getConnect();
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            channel.basicPublish("topic", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println("Sender sendMessage:" + message);
        } catch (Exception e) {

        }finally {

        }
    }
}
