package storm.trident;

import org.apache.commons.lang3.StringUtils;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;


/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/24 16:28
 **/
public class WordCountTopology {

    public static void main(String[] args) throws Exception{
        TridentTopology topology = new TridentTopology();

        //设定数据源
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("subjects"),
                4,
                new Values("java java ruby ruby"),
                new Values("python python aaa bbb aaa"),
                new Values("ruby php php python"));
        spout.setCycle(false);

        Stream stream = topology.newStream("spout", spout);
        //重点逻辑部分
        stream.shuffle().each(new Fields("subjects"), new SplitFunction(), new Fields("sub"))//按空格分割句子为单词
                .groupBy(new Fields("sub")).aggregate(new Count(),new Fields("count"))//按单词分组 相同单词分到一起 使用
                .each(new Fields("sub","count"), new ResultFunction(),new Fields());

        Config config = new Config();

        config.setNumWorkers(2);
        if (args.length == 0) {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("word-count", config, topology.build());
            Thread.sleep(10 * 1000);
            cluster.shutdown();
        } else {
            StormSubmitter.submitTopology(args[0], config, topology.build());
        }
    }

    private static class SplitFunction extends BaseFunction {

        @Override
        public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
            String subjects = tridentTuple.getStringByField("subjects");
            if (StringUtils.isNotBlank(subjects)) {
                String[] s = subjects.split(" ");
                for (String subject : s) {
                    tridentCollector.emit(new Values(subject));
                }
            }
        }
    }

    private static class ResultFunction extends BaseFunction {

        @Override
        public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
            String sub = tridentTuple.getStringByField("sub");
            Long count = tridentTuple.getLongByField("count");
            System.out.println("单词：" + sub + " 的次数：" + count);
        }
    }
}
