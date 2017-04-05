package Cloud.ApacheLog;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

/**
 * Counts all of the hits for an ip. Outputs all ip's
 */
public class IpSortReducer extends MapReduceBase implements Reducer<IntWritable, Text, Text, IntWritable> 
{

  public void reduce(IntWritable count, Iterator<Text> ip,
      OutputCollector<Text, IntWritable> output, Reporter reporter)
      throws IOException {
    int totalCount = 0;
    while(ip.hasNext())
    {
      totalCount = (-1) * count.get();
      output.collect(ip.next(), new IntWritable(totalCount));
    }
  }

}
