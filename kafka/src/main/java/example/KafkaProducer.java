package example;

import kafka.serializer.StringEncoder;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import utils.KafkaUtil;

import java.util.Date;
import java.util.Properties;

public class KafkaProducer {

    public static final String topic = "test";

    public static void main(String[] args) throws Exception {
        Properties properties = new Properties();
        properties.put("serializer.class", StringEncoder.class.getName());
        properties.put("metadata.broker.list", "192.168.194.128:9092");    // 声明kafka broker
        properties.put("request.required.acks", "1");
        Producer<String, String> producer = KafkaUtil.getKafkaProducer();
        for (int i = 0; i < 3; i++) {
            //构建消息
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(KafkaUtil.TOPIC_NAME, String.valueOf(i), "this is message " + new Date().getTime());
            producer.send(record, new Callback() {
                //消息发送成功后回调
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    System.out.println("message send to partition " + metadata.partition() + ", offset: " + metadata.offset());
                }
            });
            Thread.sleep(1000);
        }
        producer.close();
    }

}