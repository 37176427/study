package storm.drpc;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.drpc.LinearDRPCTopologyBuilder;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * 描述 ：drpc中每一个中间bolt的输出 第一项都必须是id
 * 作者 ：WYH
 * 时间 ：2019/7/23 16:40
 **/
public class BasicDRPCTopology {

    public static class ExclaimBolt extends BaseRichBolt{
        private OutputCollector collector;

        @Override
        public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {
            collector = outputCollector;
        }

        @Override
        public void execute(Tuple tuple) {
            String input = tuple.getString(1);
            System.out.println("tuple value0:" + tuple.getValue(0));
            collector.emit(new Values(tuple.getValue(0),input + "!"));
        }

        @Override
        public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
            outputFieldsDeclarer.declare(new Fields("id","result"));
        }
    }

    public static void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException {

        LinearDRPCTopologyBuilder builder = new LinearDRPCTopologyBuilder("exclamation");
        builder.addBolt(new ExclaimBolt(),3);

        Config config = new Config();
        //本地方式
        if (args == null || args.length == 0){
            LocalDRPC drpc = new LocalDRPC();
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("drpc_demo",config,builder.createLocalTopology(drpc));;

            for (String word : new String[]{"hello","goodbye"}){
                System.out.println("result for \" " + word + "\":" + drpc.execute("exclamation",word ));
            }
            cluster.shutdown();
        }else {
            //远程调用
            config.setNumWorkers(2);
            StormSubmitter.submitTopology(args[0],config,builder.createRemoteTopology());
        }
    }
}
