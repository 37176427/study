package com.study.mq.activemq.helloworld;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 描述 ：消息接收者
 * 作者 ：WYH
 * 时间 ：2019/2/20 17:11
 **/
public class Receiver {
    public static void main (String[] args) throws Exception{
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
        Destination destination = session.createQueue("queue1");
        MessageConsumer messageConsumer = session.createConsumer(destination);
        while (true){
            //receive 阻塞的等待
            TextMessage msg = (TextMessage) messageConsumer.receive();
            if (msg==null) break;
            System.out.println("收到的内容："+ msg.getText());
        }

        //会递归close掉session destination等
        connection.close();
    }
}
