package storm.bolt;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/9 17:21
 **/
public class WriteBolt extends BaseRichBolt {
    private OutputCollector collector;

    private static final Logger log = LoggerFactory.getLogger(WriteBolt.class);

    private static final long serialVersionUID = 1L;

    private final File file = new File("/test/1.txt");

    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
    }

    @Override
    public void execute(Tuple tuple) {
        String text = tuple.getStringByField("write");
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file,true));
            bufferedWriter.append(text);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            log.info("[write] 写入文件。");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }
}
