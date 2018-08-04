package com.yml.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqApplicationTests {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void sentMessageToServer() {
		rabbitTemplate.convertAndSend("direct.queue", "direct.queue", "rabbitMq 测试");
	}

}
