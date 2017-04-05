package Cloud.ApacheLog;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
 import org.apache.hadoop.mapred.*; 

public class Runner {

  /**
   * @param args
   */
  public static void main(String[] args) throws Exception
  {
        JobConf conf = new JobConf(Runner.class);
        conf.setJobName("ip-count");
        
        conf.setMapperClass(IpMapper.class);
        
        conf.setMapOutputKeyClass(Text.class);
        conf.setMapOutputValueClass(IntWritable.class);
        
        conf.setReducerClass(IpReducer.class);
        
        
        // take the input and output from the command line
        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1] + "-temp"));

        
        
        System.out.println("second job");
       JobConf sortJobConf = new JobConf(Runner.class);
         sortJobConf.setJobName("ip-count-sort");
        sortJobConf.setMapperClass(IpSortMapper.class);
        sortJobConf.setReducerClass(IpSortReducer.class);
        sortJobConf.setMapOutputKeyClass(IntWritable.class);
        sortJobConf.setMapOutputValueClass(Text.class);
       
       FileInputFormat.setInputPaths(sortJobConf, new Path(args[1] + "-temp"));
        FileOutputFormat.setOutputPath(sortJobConf, new Path(args[1]));
         System.out.println("third job");
       JobConf swapJob = new JobConf(Runner.class);
         sortJobConf.setJobName("ip-sort");
         JobClient.runJob(conf);
        JobClient.runJob(sortJobConf);
      
        System.out.println("last job");
	}

}
