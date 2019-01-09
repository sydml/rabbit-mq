package com.yml.rabbitmq.demo.workerqueue;

/**
 * @author Liuym
 * @date 2019/1/9 0009
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        String[] info = new String[]{"en","he","a"};
//        String message = String.join(" ", info);
//        NewTask.sendMessage(message);
        Worker.receiveMessage();
    }
}
