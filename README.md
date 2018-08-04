"# rabbit-mq" 
#### spring-boot 2.0.4 rabbitMq初次尝试
rabbitMq的几个坑

  一 direct模式上,routingKey和bindingKey必须保持一致才可以将消息发送到指定的队列上,是一对一的模式
 
  二 在topic模式上,bindingKey可以以通配符的方式描述,routingKey是指定的字符,是多对多的方式
  这里使用的绑定交换机的方法给人误区:with里面的参数其实叫做:bindingKey
  只有生产者在生产消息时指定的是routingKey进行发送消息
  bindingKey支持通配符,使用场景如下(*和#号都可以代表空的单词)
  
  绑定队列到对应的交换机
     * 注意binding时两种通配符
     * 这里使用的方法给人误区:with后面其实叫做:bindingKey
     * bindingKey的规则是点号分割
     * #:可以匹配多个单词
     * *:只能匹配一个单词
  
  如:
  (1)一个生产者匹配多个交换机,进而将消息发送多个队列上
     生产者: P1->routingKey :1.2.3
     交换机: X ->bindingKey :1.*.3->Q1, *.2.3->Q2, 1.2.*->Q3, 1.#->Q4, #.3->Q5 ...
     这样一个生产者就可以将消息发送多个队列上
  (2)多个生产者匹配一个交换机,进而将多个生产者生产的消息发送到同一个队列中
     生产者: P1->routingKey :1.2, P2->1.2.3
     交换机: X ->bindingKey :1.#->Q
     这样多个生产者就可以将他们生产的消息发送到同一个队列中
  三 生产者不绑定交换机的时候:
  生产者不绑定交换机,那么只有routingKey,bindingKey和队列名三者名字完全一致的时候(并且无论是哪种类型的交换机通过BindingBuilder创建的Binding,必须要存在一个符合该条件的)才可以发送到队列,否者直接丢弃该生产消息
