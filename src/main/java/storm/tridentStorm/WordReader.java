package storm.tridentStorm;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class WordReader extends BaseRichSpout{

	private SpoutOutputCollector collector;

	private FileReader fileReader;
	private BufferedReader reader;

	private boolean completed = false;

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		this.collector = collector;

		try {
			this.fileReader = new FileReader(conf.get("fileToRead").toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		this.reader = new BufferedReader(fileReader);
	}

	@Override
	public void nextTuple() {

		if (!completed) {
			try {
				String word = reader.readLine();

				if (word != null) {
					word = word.trim();
					word = word.toLowerCase();
					collector.emit(new Values(word));
				}

				else {
					completed = true;
					fileReader.close();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}

}
