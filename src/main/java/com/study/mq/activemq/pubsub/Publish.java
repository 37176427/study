package com.study.mq.activemq.pubsub;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/2/22 15:51
 **/
public class Publish {

    private ConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private MessageProducer producer;
    public Publish(){
        try {
            connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER,ActiveMQConnectionFactory.DEFAULT_PASSWORD,"tcp://localhost:61616");
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            producer = session.createProducer(null);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() throws JMSException{
        Destination destination = session.createTopic("topic1");
        TextMessage textMessage = session.createTextMessage();
        textMessage.setText("topic1的内容");
        producer.send(destination,textMessage);
    }

    public static void main(String[] args) throws JMSException{
        Publish p = new Publish();
        p.sendMessage();
    }
}
