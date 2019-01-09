package com.yml.rabbitmq.demo.workerqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * @author Liuym
 * @date 2019/1/9 0009
 */
public class NewTask {
    private static final String TASK_QUEUE_NAME = "task_queue";

    public static void sendMessage(String message) {

        ConnectionFactory factory = RabbitConnect.getConnect();
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
            channel.basicPublish("", TASK_QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println("NewTask sendMessage:" + message);
        } catch (Exception e) {

        }
    }
}
