package com.study.mq.rocketmq.quickstart;

import com.alibaba.rocketmq.client.consumer.*;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/4/9 16:12
 **/
public class PullScheduleService {

    public static void main(String[] args) throws MQClientException {
        String group_name = "schedule_consumer";

        final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService(group_name);
        scheduleService.getDefaultMQPullConsumer().setNamesrvAddr("192.168.194.128:9876;192.168.194.129:9876");
        scheduleService.setMessageModel(MessageModel.CLUSTERING);
        scheduleService.registerPullTaskCallback("TopicPull", (mq, context) -> {
            MQPullConsumer consumer = context.getPullConsumer();
            try {
                long offset = consumer.fetchConsumeOffset(mq, false);
                if (offset < 0) offset = 0;
                PullResult pullResult = consumer.pull(mq, "*", offset, 32);
                switch (pullResult.getPullStatus()) {
                    case NO_MATCHED_MSG:
                        break;
                    case NO_NEW_MSG:
                        break;
                    case OFFSET_ILLEGAL:
                        break;
                    case FOUND:
                        List<MessageExt> list = pullResult.getMsgFoundList();
                        for (MessageExt messageExt:list){
                            System.out.println(new String(messageExt.getBody()));
                        }
                    default:
                        break;
                }
                //每隔5s定时刷新到broker
                consumer.updateConsumeOffset(mq,pullResult.getNextBeginOffset());
                context.setPullNextDelayTimeMillis(6000);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        scheduleService.start();
    }
}
