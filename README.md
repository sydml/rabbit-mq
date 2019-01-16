### spring-boot 2.0.4 RabbitMq使用示例，基本可以完成业务场景使用
### RabbitMqConfig 基础配置示例
- 队列声明
- 交换机声明（direct, topic, fanout）
- 绑定规则声明
- 增加生产消费模型示例
#### 基础配置讲解
####  一 direct模式上,routingKey和bindingKey必须保持一致才可以将消息发送到指定的队列上,是一对一的模式
 
####  二 在topic模式上,bindingKey可以以通配符的方式描述,routingKey是指定的字符,是多对多的方式

####  三 交换机绑定队列时用的bindingKey 和 发送消息时候用的routingKey保持一致（direct exchange）或者匹配规则一致（topic exchange）就可以将消息发送到该队列上;其中fanoutExchange是广播，只绑定队列即可，不用指定bindingKey
_这里使用的绑定交换机的方法给人误区:with里面的参数其实叫做:bindingKey,生产者在发送消息时指定的叫做:routingKey,
  bindingKey支持通配符,使用场景如下(*和#号都可以代表空的单词)_
1. 注意绑定交换机时的bindingKey的命名规则是点号分割英文单词,
2. bindingKey有两种通配符
   1. "#" :可以匹配多个单词
   2. "*" :只能匹配一个单词
3. 通配符的使用规则如下:
-  (1)一个生产者匹配多个交换机,进而将消息发送多个队列上
    1. 生产者: P1->routingKey :1.2.3
    2. 交换机: X ->bindingKey :1.*.3->Q1, *.2.3->Q2, 1.2.*->Q3, 1.#->Q4, #.3->Q5 ...
     这样一个生产者就可以将消息发送多个队列上
-  (2)多个生产者匹配一个交换机,进而将多个生产者生产的消息发送到同一个队列中
    1. 生产者: P1->routingKey :1.2, P2->1.2.3
    2. 交换机: X ->bindingKey :1.#->Q
    3. 这样多个生产者就可以将他们生产的消息发送到同一个队列中
####  三 生产者不绑定交换机的时候
1. 生产者不绑定交换机,那么只有routingKey,bindingKey和队列名三者名字完全一致的时候(并且无论是哪种类型的交换机通过BindingBuilder创建的Binding,必须要存在一个符合该条件的)才可以发送到队列,否者直接丢弃该生产消息

### 基础配置升级
- 配置rabbitTemplate 配置，增加发送消息确认回调统一处理
- 配置单消费者及多消费者，开启手动模式
- 增加消息发送，接收demo示例，接收消息确认规则
- @RabbitListener @RabbitHandler的使用方式

#### routingKey（接收消息） bindingKey（发送消息） 区别
- 生产者发送消息时发送者是不需要知道消费者的接收队列是哪个；
- 消费者接收消息时，消费者同样不需要知道生产者是谁；
- 接收消息时：routingKey 队列和交换机绑定时使用routingKey,确定业务中某个队列receive 消息时是如何路由到绑定的交换机上；
- 发送消息时：bindingKey 负责绑定交换机，使用rabbitTemplate.convertAndSend()时指定发送规则，这里的bindingKey对于topic交换机支持通配符。

#### 关于动态监听 由于@RabbitListener 只能在编译期硬编码指定消费队列，如果要动态的增删队列，需要自己实现listener
- 使用RabbitAdmin获取connection ,channel,加上自己开启线程方式轮询队列消费可以实现，但是还是想基于@RabbitListener 的实现方式，动态的将队列及绑定控制起来这个有空研究吧
- SimpleMessageListenerContainer 的使用