package com.yml.rabbitmq;

import com.yml.rabbitmq.client.RabbitMqClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StopWatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqApplicationTests {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private RabbitMqClient rabbitMqClient;

	@Test
	public void sentMessageToServer() {
		rabbitTemplate.convertAndSend("direct.queue", "direct.queue", "rabbitMq 测试");
	}

	@Test
    public void init() {
		StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        int threads = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(threads);

        final CountDownLatch start = new CountDownLatch(1);
        final CountDownLatch end = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            executorService.execute(() -> {
                try {
                    start.await();
                    for (int j = 0; j < 4; j++) {
						rabbitMqClient.topicModeSend("topic消息发送");
                        rabbitMqClient.fanoutModeSend("线程:" + Thread.currentThread().getName() + ", fanout消息发送" + j);
                        rabbitMqClient.topicModeSend("线程:" + Thread.currentThread().getName() + ", ttt.queue.111.234发送的消息" + j);
                        rabbitMqClient.topicTestModeSend("线程:" + Thread.currentThread().getName() + ", topic.queue.test.exchange发送的消息" + j);
                        rabbitMqClient.topicModeSendTest("线程:" + Thread.currentThread().getName() + ", 不指定交换机时topic.queue.test发送的消息" + j);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    end.countDown();
                }
            });
        }
        start.countDown();
        try {
            end.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        stopWatch.stop();
        System.out.println("发送消息耗时：" + stopWatch.getTotalTimeMillis());
    }

}
