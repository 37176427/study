package storm.bolt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/9 17:20
 **/
public class PrintBolt extends BaseRichBolt {
    private OutputCollector collector ;

    private static final Logger log = LoggerFactory.getLogger(PrintBolt.class);

    private static final long serialVersionUID = 1L;

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        //获取上一个组件声明的field
        String print = tuple.getStringByField("print");
        log.info("[print]: " + print);
        //打印后发给下一个组件
        collector.emit(new Values(print));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("write"));
    }
}
