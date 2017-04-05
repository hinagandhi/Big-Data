package gandhi.GA;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

public class GACalculateFitnessMapper extends MapReduceBase implements
    	Mapper<LongWritable, Text, Text, Text> {
    	@Override
		public void map(LongWritable key, Text value,
		OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	    String line = value.toString();
	    int poolSize = 0;
	    String[] values = line.split(":");
		poolSize = Integer.parseInt(values[1]);

	    for(int i = 0 ; i < Integer.parseInt(values[1]); i++)
	    {
	    	Chomosone c = new Chomosone(Genetic.target);
	    	output.collect(new Text(c.chromo.toString()), new Text("0.0"));
	    }
}
}