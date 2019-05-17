package com.study.mq.rocketmq.filter;

import com.alibaba.rocketmq.common.filter.MessageFilter;
import com.alibaba.rocketmq.common.message.MessageExt;

public class MessageFilterImpl implements MessageFilter {
    @Override
    public boolean match(MessageExt msg) {
        String property = msg.getUserProperty("SequenceId");
        if (property!=null){
            int id = Integer.parseInt(property);
            return (id % 3) == 0 && (id > 10);
        }
        return false;

    }
}
