package storm.trident;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.trident.Stream;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 描述 ：
 * 作者 ：WYH
 * 时间 ：2019/7/24 15:09
 **/
public class TridentFilter {


    public static void main(String[] args) throws InterruptedException, InvalidTopologyException, AuthorizationException, AlreadyAliveException {
        Config config = new Config();

        config.setNumWorkers(2);
        if (args.length == 0) {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("trident-function", config, buildTopology());
            Thread.sleep(10 * 1000);
            cluster.shutdown();
        } else {
            StormSubmitter.submitTopology(args[0], config, buildTopology());
        }
    }

    private static StormTopology buildTopology() {
        TridentTopology topology = new TridentTopology();
        //设定数据源
        FixedBatchSpout spout = new FixedBatchSpout(new Fields("a", "b", "c", "d"),//输入的域字段为abcd
                4,//设置批处理大小
                new Values(1, 4, 7, 10), new Values(3, 1, 3, 11), new Values(5, 2, 6, 7), new Values(2, 5, 8, 0));//测试数据
        spout.setCycle(false);
        Stream inputStream = topology.newStream("spout", spout);
        //输入数据源参数名 处理函数 输出参数名
        inputStream.each(new Fields("a", "b", "c", "d"), new SumFunction(), new Fields("sum"))
                //.parallelismHint(2) 并行度设置
                //.shuffle().global().
        //.partitionBy(new Fields("a")).each(new Fields("a","b","c","d"), new WriteFunction(), new Fields()).parallelismHint(4);.batchGlobal() 分组方式
        .each(new Fields("a", "b", "c", "d"),new CheckFilter())
        .each(new Fields("a", "b", "c", "d", "sum"), new Result(), new Fields());

        return topology.build();

    }


    private static class Result extends BaseFunction {

        @Override
        public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
            System.out.println("Result 传入的tuple:" + tridentTuple);
            Integer a = tridentTuple.getIntegerByField("a");
            Integer b = tridentTuple.getIntegerByField("b");
            Integer c = tridentTuple.getIntegerByField("c");
            Integer d = tridentTuple.getIntegerByField("d");
            System.out.println("a:" + a + ", b:" + b + ", c:" + c + ",d: " + d);
            Integer sum = tridentTuple.getIntegerByField("sum");
            System.out.println("sum:" + sum);
        }
    }

    private static class SumFunction extends BaseFunction {

        @Override
        public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
            System.out.println("SumFunction 传入的tuple:" + tridentTuple);
            Integer a = tridentTuple.getIntegerByField("a");
            Integer b = tridentTuple.getIntegerByField("b");
            Integer c = tridentTuple.getIntegerByField("c");
            Integer d = tridentTuple.getIntegerByField("d");
            Integer sum = a + b;
            tridentCollector.emit(new Values(sum));
        }
    }

    private static class CheckFilter extends BaseFilter {

        @Override
        public boolean isKeep(TridentTuple tridentTuple) {
            System.out.println("CheckFilter 传入的tuple:" + tridentTuple);
            Integer a = tridentTuple.getIntegerByField("a");
            Integer b = tridentTuple.getIntegerByField("b");
            return (a + b) % 2 == 0;
        }
    }

    private static class WriteFunction extends BaseFunction {

        private FileWriter writer;

        @Override
        public void execute(TridentTuple tridentTuple, TridentCollector tridentCollector) {
            System.out.println("write function 传入的tuple:" + tridentTuple);
            Integer a = tridentTuple.getIntegerByField("a");
            try {
                if (writer == null) {
                    writer = new FileWriter("D:\\" + this);
                }
                writer.write(a.toString());
                writer.write("\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
