package com.study.mq.activemq.cluster;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/2/25 15:57
 **/
public class Sender {
    public static void main(String[] args) {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                    ActiveMQConnectionFactory.DEFAULT_USER,
                    ActiveMQConnectionFactory.DEFAULT_PASSWORD,
                    "failover:(tcp://192.168.194.128:51511,tcp://192.168.194.128:51512,tcp://192.168.194.128:51513)?Randomize=false");
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("first");
            MessageProducer producer = session.createProducer(null);

            for (int i = 0; i < 5000; i++) {
                TextMessage msg = session.createTextMessage();
                msg.setText("我是消息内容" + i);
                producer.send(destination,msg,DeliveryMode.NON_PERSISTENT,0,1000L*60);
                System.out.println("发送消息：" + msg.getText());
                Thread.sleep(1000);
            }

            connection.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
