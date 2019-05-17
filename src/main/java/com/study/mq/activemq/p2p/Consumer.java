package com.study.mq.activemq.p2p;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/2/21 16:38
 **/
public class Consumer {
    public final String SELECTOR_1 = "color='blue'";
    public final String SELECTOR_2 = "color='blue' AND sal > 2100";
    public final String SELECTOR_3 = "receiver='A'";
    //public final String SELECTOR_4 = "receiver='B'";
    //单例模式
    //1连接工厂
    private ConnectionFactory connectionFactory;
    //2连接对象
    private Connection connection;
    //3session对象
    private Session session;
    //4消息消费者
    private MessageConsumer messageConsumer;
    //5目标地址
    private Destination destination;
    public Consumer() {
        try {
            this.connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnectionFactory.DEFAULT_USER, ActiveMQConnectionFactory.DEFAULT_PASSWORD, "tcp://localhost:61616");
            this.connection = this.connectionFactory.createConnection();
            this.connection.start();
            this.session = this.connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            this.destination = this.session.createQueue("first");
            //指定了选择器
            this.messageConsumer = this.session.createConsumer(this.destination,SELECTOR_1);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void receiver(){
        try {
            this.messageConsumer.setMessageListener(new Listener());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    class Listener implements MessageListener{

        @Override
        public void onMessage(Message message) {
            try {
                if (message instanceof TextMessage){
                    System.out.println("不支持text");
                }
                if (message instanceof MapMessage){
                    MapMessage ret = (MapMessage) message;
                    System.out.println(ret.toString());
                    System.out.println(ret.getString("name"));
                    System.out.println(ret.getString("age"));

                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.receiver();
    }
}
