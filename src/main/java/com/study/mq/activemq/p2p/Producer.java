package com.study.mq.activemq.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/2/21 16:21
 **/
public class Producer {
    //单例模式
    //1连接工厂
    private ConnectionFactory connectionFactory;
    //2连接对象
    private Connection connection;
    //3session对象
    private Session session;
    //4消息生产者
    private MessageProducer messageProducer;

    public Producer() {
        try {
            this.connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
            this.connection = this.connectionFactory.createConnection();
            this.connection.start();
            this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            this.messageProducer = this.session.createProducer(null);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    public Session getSession() {
        return this.getSession();
    }

    public void send1() {
        try {
            Destination destination = this.session.createQueue("first");
            MapMessage msg1 = this.session.createMapMessage();
            msg1.setString("name", "张三");
            msg1.setString("age", "23");
            msg1.setStringProperty("color", "blue");
            msg1.setIntProperty("sal", 2000);
            int id = 1;
            msg1.setInt("id", id);
            String receiver = id % 2 == 0 ? "A" : "B";
            msg1.setStringProperty("receiver", receiver);
            MapMessage msg2 = this.session.createMapMessage();
            msg2.setString("name", "李四");
            msg2.setString("age", "26");
            msg2.setStringProperty("color", "red");
            msg2.setIntProperty("sal", 1000);
            id = 2;
            msg2.setInt("id", id);
            receiver = id % 2 == 0 ? "A" : "B";
            msg2.setStringProperty("receiver", receiver);
            MapMessage msg3 = this.session.createMapMessage();
            msg3.setString("name", "王五");
            msg3.setString("age", "29");
            msg3.setStringProperty("color", "green");
            msg3.setIntProperty("sal", 3300);
            id = 3;
            msg3.setInt("id", id);
            receiver = id % 2 == 0 ? "A" : "B";
            msg3.setStringProperty("receiver", receiver);
            MapMessage msg4 = this.session.createMapMessage();
            msg4.setString("name", "赵四");
            msg4.setString("age", "35");
            msg4.setStringProperty("color", "yellow");
            msg4.setIntProperty("sal", 6600);
            id = 4;
            msg4.setInt("id", id);
            receiver = id % 2 == 0 ? "A" : "B";
            msg4.setStringProperty("receiver", receiver);
            this.messageProducer.send(destination, msg1, DeliveryMode.NON_PERSISTENT, 2, 1000 * 60 * 10L);
            this.messageProducer.send(destination, msg2, DeliveryMode.NON_PERSISTENT, 3, 1000 * 60 * 10L);
            this.messageProducer.send(destination, msg3, DeliveryMode.NON_PERSISTENT, 6, 1000 * 60 * 10L);
            this.messageProducer.send(destination, msg4, DeliveryMode.NON_PERSISTENT, 9, 1000 * 60 * 10L);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Producer p = new Producer();
        p.send1();
    }
}
