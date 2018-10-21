package storm.tridentStorm;

import java.util.Arrays;
import java.util.List;

import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

public class SplitFunction extends BaseFunction {

	@Override
	public void execute(TridentTuple tuple, TridentCollector collector) {
		
		String words = tuple.getString(0);
		List<String> wordSplit = Arrays.asList(words.split(" "));
		for(String word: wordSplit) {
			collector.emit(new Values(word));
		}
	}
}
