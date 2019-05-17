package com.study.mq.activemq.helloworld;

/**
 * 描述 ：消息发送者
 * 作者 ：WYH
 * 时间 ：2019/2/20 17:10
 **/

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {

    public static void main (String[] args) throws Exception{
        //第一步：建立连接工厂对象 需要填入用户名密码及链接地址 使用默认即可 默认端口为tcp://localhost:61616
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://localhost:61616");
        //第二步：通过连接工厂对象创建一个连接 调用start方法开启连接 默认连接是关闭的
        Connection connection = connectionFactory.createConnection();
        connection.start();
        //第三步：通过connection对象创建session对话（上下文）,用于接收消息 参数配置1为是否启用事务 2为签收模式 一般设置为自动签收
        Session session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
        //第四步：通过session创建destination对象 指的是一个客户端用来指定生产消息目标和消费消息来源的对象
        // 在PTP模式中 destination被称为QUEUE 在pub/sub模式Destination成为topic
        Destination destination = session.createQueue("queue1");
        //第五步：我们需要通过session对象创建消息的发送和接收对象(生产消费者)MessageProducer/MessgaeConsumer
        MessageProducer messageProducer = session.createProducer(destination);
        //第六步：使用messageProducer的setDeliveryMode方法为其设置持久化特性和非持久化特性（是否需要持久化存储）
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //第七步：最后使用jms规范的TextMessage形式创建数据（通过session对象） 用messageProducer的send方法发送
        for (int i = 0; i < 5; i++) {
            TextMessage textMessage = session.createTextMessage();
            textMessage.setText("hello world!" + i);
            messageProducer.send(textMessage);
        }
        //会递归close掉session destination等
        connection.close();
    }
}
