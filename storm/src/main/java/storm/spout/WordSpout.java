package storm.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/23 14:25
 **/
public class WordSpout extends BaseRichSpout {

    private SpoutOutputCollector collector;

    private int index = 0;

    private String[] sentences = {
            "i have a pen",
            "i have a apple",
            "come on come on",
            "turn the radio on",
            "i don't think so"
    };
    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        //发送一个句子
        this.collector.emit(new Values(sentences[index++]));
        if (index >= sentences.length) index=0;
        try {
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("sentence"));
    }

    @Override
    public void close(){
        System.out.println("wordSpout close....");
    }
}
