package storm.redis;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;


public class Topology {
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException {
		
		TopologyBuilder builder = new TopologyBuilder();
		// set the spout class
		builder.setSpout("spout", new SampleSpout(), 2);
		// set the bolt class
		builder.setBolt("bolt", new StormRedisBolt("192.168.194.128",6379), 2).shuffleGrouping("spout");

		Config conf = new Config();
		conf.setDebug(true);
		// create an instance of LocalCluster class for
		// executing topology in local mode.
		LocalCluster cluster = new LocalCluster();

		// StormRedisTopology is the name of submitted topology.
		cluster.submitTopology("StormRedisTopology", conf, builder.createTopology());
		try {
			Thread.sleep(10000);
		} catch (Exception exception) {
			System.out.println("Thread interrupted exception : " + exception);
		}
		// kill the StormRedisTopology
		cluster.killTopology("StormRedisTopology");
		// shutdown the storm test cluster
		cluster.shutdown();
}
}