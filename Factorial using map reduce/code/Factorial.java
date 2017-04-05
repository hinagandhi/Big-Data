 package Cloud.Iliad;  
  
 import java.io.IOException;  
 import java.util.*;  
  
 import org.apache.hadoop.fs.Path;  
 import org.apache.hadoop.conf.*;  
 import org.apache.hadoop.io.*;  
 import org.apache.hadoop.mapred.*;  
 import org.apache.hadoop.util.*;  
  
 public class Factorial {  
  
    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, VLongWritable> {    
      private Text word = new Text("fact");	  
      // OutputCollector<key datatype, value datatype>, reporter reports the status of output i.e 10% done and so on
      public void map(LongWritable key, Text value, OutputCollector<Text,VLongWritable> output, Reporter reporter) throws IOException {  
        String line = value.toString(); 
        VLongWritable x = new VLongWritable(Long.parseLong(line));	
          output.collect(word, x);
     }  
    }  
  
    public static class Reduce extends MapReduceBase implements Reducer<Text, VLongWritable, Text, VLongWritable> {  
      public void reduce(Text key, Iterator<VLongWritable> values, OutputCollector<Text, VLongWritable> output, Reporter reporter) throws IOException {   
        System.out.println("reducer working"); 
         long fact = 1l;
		while (values.hasNext()) {  
            fact*= values.next().get();
        } 
         
        output.collect(key, new VLongWritable(fact));  
        
    }  
 }
    public static void main(String[] args) throws Exception {  
      JobConf conf = new JobConf(Factorial.class);  //class name
      conf.setJobName("wordcount");  // optional ..can be set to anyname
  
      conf.setOutputKeyClass(Text.class); // output key is text file  
      conf.setOutputValueClass(VLongWritable.class);   // value is integer type
    
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
