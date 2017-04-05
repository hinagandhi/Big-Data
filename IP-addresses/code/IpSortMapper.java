package Cloud.ApacheLog;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 import org.apache.hadoop.mapred.*; 
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;

/**
 * Mapper that takes a line from an Apache access log and emits the IP with a
 * count of 1. This can be used to count the number of times that a host has
 * hit a website.
 */
public class IpSortMapper extends MapReduceBase 
                        implements Mapper<LongWritable, Text, IntWritable, Text>
{

  // Regular expression to match the IP at the beginning of the line in an
  // Apache access log

  // Reusable IntWritable for the count
 // private static final IntWritable one = new IntWritable(1);

  public void map(LongWritable fileOffset, Text lineContents,
      OutputCollector<IntWritable, Text> output, Reporter reporter)
      throws IOException {
    // apply the regex to the line of the access log
  //  Matcher matcher = ipPattern.matcher(lineContents.toString());
      String text = lineContents.toString();
      System.out.println(text);
      String[] arr = text.split("\\s+");
      String arr1 = arr[0];
      String arr2 = arr[1];
      System.out.println(arr1);
      Text word = new Text();
      int y =0;
      IntWritable x = new IntWritable(0);
      System.out.println("String" + arr[0]);
       word = new Text(arr1);
       y = (-1 * Integer.parseInt(arr2));
      x = new IntWritable(y);
    
     System.out.println("second mapper");
      output.collect(x,word);

  }

}
