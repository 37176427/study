package com.study.mq.activemq.cluster;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/2/25 15:57
 **/
public class Receiver {
    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnectionFactory.DEFAULT_USER,
                    ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                    "failover:(tcp://192.168.194.128:51511,tcp://192.168.194.128:51512,tcp://192.168.194.128:51513)?Randomize=false");
            Connection connection = connectionFactory.createConnection();
            connection.start();
            Session session = connection.createSession(false,1);
            Destination destination = session.createQueue("first");
            MessageConsumer consumer = session.createConsumer(destination);
            while (true){
                Message receive = consumer.receive();
                if (receive instanceof TextMessage){
                    TextMessage msg = (TextMessage) receive;
                    System.out.println("收到消息："+msg.getText());
                }
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
