package storm.drpc;

import org.apache.storm.Config;
import org.apache.storm.security.auth.SimpleTransportPlugin;
import org.apache.storm.thrift.TException;
import org.apache.storm.utils.DRPCClient;

/**
 * 描述 ：测试drpc
 * 作者 ：WYH
 * 时间 ：2019/7/23 16:59
 **/
public class DrpcExclam {
    public static void main(String[] args) throws TException {
        Config conf = new Config();
        conf.setDebug(false);
        //不配置会报错
        conf.put(Config.DRPC_THRIFT_TRANSPORT_PLUGIN, SimpleTransportPlugin.class.getName());
        conf.put(Config.STORM_NIMBUS_RETRY_TIMES,3);
        conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL,10000);
        conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL_CEILING,10000);
        conf.put(Config.DRPC_MAX_BUFFER_SIZE, 104857600); // 100M

        DRPCClient client = new DRPCClient(conf,"192.168.194.128",3772);
        for (String word: new String[]{"a","b","c","d"}){
            System.out.println(client.execute("exclamation",word));
        }
    }

}
