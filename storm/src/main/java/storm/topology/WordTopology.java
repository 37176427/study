package storm.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import storm.bolt.WordCountBolt;
import storm.bolt.WordReportBolt;
import storm.bolt.WordSplitBolt;
import storm.spout.WordSpout;

/**
 * 描述 ：单词计数
 * 作者 ：WYH
 * 时间 ：2019/7/23 14:23
 **/
public class WordTopology {
    private static final String WORD_SPOUT_ID = "word-spout";
    private static final String SPLIT_BOLT_ID = "split-bolt";
    private static final String COUNT_BOLT_ID = "count-spout";
    private static final String REPORT_BOLT_ID = "report-spout";
    private static final String TOPOLOGY_NAME = "word-count-topology";

    public static void main(String[] args) throws InterruptedException {

        WordSpout spout = new WordSpout();
        WordSplitBolt splitBolt = new WordSplitBolt();
        WordCountBolt countBolt = new WordCountBolt();
        WordReportBolt reportBolt = new WordReportBolt();

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(WORD_SPOUT_ID,spout);
        builder.setBolt(SPLIT_BOLT_ID,splitBolt,5).shuffleGrouping(WORD_SPOUT_ID);
        builder.setBolt(COUNT_BOLT_ID,countBolt,5).fieldsGrouping(SPLIT_BOLT_ID,new Fields("word"));
        builder.setBolt(REPORT_BOLT_ID,reportBolt, 10).globalGrouping(COUNT_BOLT_ID);

        Config config = new Config();
        config.setDebug(false);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(TOPOLOGY_NAME,config,builder.createTopology());

        Thread.sleep(10 * 1000);
        cluster.killTopology(TOPOLOGY_NAME);
        cluster.shutdown();

    }
}
