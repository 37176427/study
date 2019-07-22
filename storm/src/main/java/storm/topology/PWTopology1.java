package storm.topology;

import org.apache.storm.LocalCluster;
import org.apache.storm.tuple.Fields;
import storm.bolt.PrintBolt;
import storm.bolt.WriteBolt;
import storm.spout.PWSpout;
import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/9 17:20
 **/
public class PWTopology1{

    public static void main(String[] args) throws Exception {
        Config config = new Config();
        config.setNumWorkers(2);//JVM数
        config.setDebug(false);

        TopologyBuilder builder = new TopologyBuilder();
        //流程：spout->print->write
        builder.setSpout("spout",new PWSpout());
        builder.setBolt("print-bolt",new PrintBolt(),4).shuffleGrouping("spout");
        //builder.setBolt("write-bolt",new WriteBolt(),1).shuffleGrouping("print-bolt");
        //按字段分组
        //builder.setBolt("write-bolt",new WriteBolt(),8).fieldsGrouping("print-bolt",new Fields("write"));
        //广播发送
        builder.setBolt("write-bolt",new WriteBolt(),4).allGrouping("print-bolt");

        //本地模式
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("top1",config,builder.createTopology());
        Thread.sleep(10 * 1000);
        cluster.killTopology("top1");
        cluster.shutdown();
        //集群模式
        //StormSubmitter.submitTopology("top1",config,builder.createTopology());
    }
}
