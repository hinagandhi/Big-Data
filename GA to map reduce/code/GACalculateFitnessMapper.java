package gandhi.GA;


import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.*;
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
	    String[] values = line.split("\t");
	    Chomosone c = new Chomosone(Genetic.target);
	    c.chromo = new StringBuffer(Genetic.chromoLen * 4);
	   	c.chromo.append(values[0]);
	   	Random rand1 = new Random();
		int  n = rand1.nextInt(4) + 1;
	   	if(values[1].equals("0.0"))
		   {
	   		c.scoreChromo(Genetic.target);
	   		String s = c.chromo.toString() + "	" + String.valueOf(c.score);
	   		output.collect(new Text(String.valueOf(n)), new Text(s));
	   		}
	   	else
	   	{
	   		String s = c.chromo.toString() + "	" + values[1];
	   		output.collect(new Text(String.valueOf(n)), new Text(s));
	   	}	
	    }
}