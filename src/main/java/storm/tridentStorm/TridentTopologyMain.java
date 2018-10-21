package storm.tridentStorm;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Debug;
import org.apache.storm.tuple.Fields;

public class TridentTopologyMain {

	public static void main(String[] args) throws InterruptedException {

		TridentTopology topology = new TridentTopology();
		topology.newStream("lines", new WordReader())
				.each(new Fields("word"), new SplitFunction(), new Fields("word_split"))
				.each(new Fields("word_split"), new Debug());

		Config conf = new Config();
		conf.setDebug(true);
		conf.put("fileToRead", "C:\\thota\\read.txt");
//		conf.put("dirToWrite", "C:\\thota\\trident\\");

		if (args.length > 0) {
			try {
				System.out.println("Hello");
				StormSubmitter.submitTopology("Trident-Topology", conf, topology.build());
			} catch (AlreadyAliveException | InvalidTopologyException | AuthorizationException e) {
				e.printStackTrace();
			}
		} else {
			LocalCluster cluster = new LocalCluster();
			try {
				cluster.submitTopology("Trident-Topology", conf, topology.build());
				Thread.sleep(10000);
			} finally {
				cluster.shutdown();
			}
		}
	}
}
