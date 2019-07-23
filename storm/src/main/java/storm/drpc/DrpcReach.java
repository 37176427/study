package storm.drpc;


import org.apache.storm.Config;
import org.apache.storm.security.auth.SimpleTransportPlugin;
import org.apache.storm.utils.DRPCClient;

public class DrpcReach {

	public static void main(String[] args) throws Exception {

		Config conf = new Config();
		conf.setDebug(false);
		//不配置会报错
		conf.put(Config.DRPC_THRIFT_TRANSPORT_PLUGIN, SimpleTransportPlugin.class.getName());
		conf.put(Config.STORM_NIMBUS_RETRY_TIMES,3);
		conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL,10000);
		conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL_CEILING,10000);
		conf.put(Config.DRPC_MAX_BUFFER_SIZE, 104857600); // 100M

		DRPCClient client = new DRPCClient(conf,"192.168.194.128", 3772);
	      for (String url : new String[]{ "foo.com/blog/1", "engineering.twitter.com/blog/5" }) {
	    	  System.out.println(client.execute("reach", url));
	      }
	}
}
