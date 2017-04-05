package Cloud.Iliad;  
  
 import java.io.IOException;  
 import java.util.*;  
  import java.lang.*;
  import java.text.*;
 import org.apache.hadoop.fs.Path;  
 import org.apache.hadoop.conf.*;  
 import org.apache.hadoop.io.*;  
 import org.apache.hadoop.mapred.*;  
 import org.apache.hadoop.util.*;  
  
 public class LeibnizFormula {  
  
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, DoubleWritable> {    
      private Text word = new Text("pi");	  
      // OutputCollector<key datatype, value datatype>, reporter reports the status of output i.e 10% done and so on
      public void map(LongWritable key, Text value, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {  
        String line = value.toString(); 
        DoubleWritable x = new DoubleWritable(Double.parseDouble(line));		
          output.collect(word, x);
     }  
    }  
  
    public static class Reduce extends MapReduceBase implements Reducer<Text, DoubleWritable, Text, DoubleWritable> {  
      public void reduce(Text key, Iterator<DoubleWritable> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {   
     double pi = 0.0d;
     double val = 0.0d;
     DecimalFormat df = new DecimalFormat("#.####");
		while (values.hasNext()) {
      val = values.next().get();
       pi+= Math.pow(-1,val)/((2*val) + 1) ;
}   
    pi = pi * 4.0d;
    //final_pi = Double.parseDouble(df.format(pi));
    output.collect(key, new DoubleWritable(pi));  
    }  
 }
    public static void main(String[] args) throws Exception {  
      JobConf conf = new JobConf(LeibnizFormula.class);  //class name
      conf.setJobName("picalculation");  // optional ..can be set to anyname
  
      conf.setOutputKeyClass(Text.class); // output key is text file  
      conf.setOutputValueClass(DoubleWritable.class);   // value is integer type
  
      conf.setMapperClass(Map.class); // map class we defined above
      //conf.setCombinerClass(Reduce.class);  // reduce class we define above
      conf.setReducerClass(Reduce.class);  // reduce class we define above
  
      conf.setInputFormat(TextInputFormat.class);  // text input format
      conf.setOutputFormat(TextOutputFormat.class);  // text output format
  
      FileInputFormat.setInputPaths(conf, new Path(args[0]));  // program name where args[0] is input file path 
      FileOutputFormat.setOutputPath(conf, new Path(args[1]));  //and args[1] is output file path
  
      JobClient.runJob(conf);  
    }  
 }  
