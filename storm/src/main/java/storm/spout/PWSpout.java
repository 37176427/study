package storm.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/9 17:20
 **/
public class PWSpout extends BaseRichSpout{

    private static final long serialVersionUID = 1L;

    private SpoutOutputCollector collector;
    //数据源
    private static final Map<Integer,String> MAP = new HashMap<>();

    static {
        MAP.put(0,"java");
        MAP.put(1,"php");
        MAP.put(2,"python");
        MAP.put(3,"groovy");
        MAP.put(4,"5");
        MAP.put(5,"66");
        MAP.put(6,"77");
        MAP.put(7,"88");
        MAP.put(8,"99");
        MAP.put(9,"1010");
        MAP.put(10,"111");
    }

    @Override
    public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        //对spout进行初始化
        this.collector = spoutOutputCollector;
    }

    @Override
    public void nextTuple() {
        final Random r = new Random();
        int i = r.nextInt(10);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //发送数据
        System.out.println("发送数据");
        this.collector.emit(new Values(MAP.get(i)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        //对发送的数据进行声明 发送的数据标识
        outputFieldsDeclarer.declare(new Fields("print"));
    }
}
