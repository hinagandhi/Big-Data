// 
 // Author - Jack Hebert (jhebert@cs.washington.edu) 
 // Copyright 2007 
 // Distributed under GPLv3 
 // 
// Modified - Dino Konstantopoulos
// Distributed under the "If it works, remolded by Dino Konstantopoulos, 
// otherwise no idea who did! And by the way, you're free to do whatever 
// you want to with it" dinolicense
// 
package U.CC;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
  
  
public class SpeciesIterDriver2 {
     
     public static void main(String[] args) throws Exception {
		try {
			runJob(args[0], args[1]);
		} catch (IOException ex) {
			Logger.getLogger(SpeciesIterDriver2.class.getName()).log(
					Level.SEVERE, null, ex);
		}
	}
  
 public static void runJob(String input, String output) throws IOException {
     JobClient client = new JobClient(); 
     JobConf conf;
     for(int i=0;i<50;i++){
            conf= new JobConf(SpeciesIterDriver2.class); 
            conf.setJobName("Species Iter"); 

            conf.setNumReduceTasks(1); 

             //~dk
             //conf.setInputFormat(org.apache.hadoop.mapred.SequenceFileInputFormat.class); 
             //conf.setOutputFormat(org.apache.hadoop.mapred.SequenceFileOutputFormat.class); 

             conf.setOutputKeyClass(Text.class); 
             conf.setOutputValueClass(Text.class); 

             /*if (args.length < 2) { 
             System.out.println("Usage: PageRankIter <input path> <output path>"); 
             System.exit(0); 
             } */

             //~dk
             //conf.setInputPath(new Path(args[0])); 
             //conf.setOutputPath(new Path(args[1])); 
             //FileInputFormat.setInputPaths(conf, new Path(args[0]));
             //FileOutputFormat.setOutputPath(conf, new Path(args[1]));
              if(i==0){
                    FileInputFormat.setInputPaths(conf, new Path(input));
                    FileOutputFormat.setOutputPath(conf, new Path("SpeciesRank"+i));
                }
            else{
                 FileInputFormat.setInputPaths(conf, new Path("SpeciesRank" + (i -1)));
                //int newFileVal = i+1;
                FileOutputFormat.setOutputPath(conf, new Path("SpeciesRank" + i));
             }

             //conf.setInputPath(new Path("graph2")); 
             //conf.setOutputPath(new Path("graph3")); 

             conf.setMapperClass(SpeciesIterMapper2.class); 
             conf.setReducerClass(SpeciesIterReducer2.class); 
             conf.setCombinerClass(SpeciesIterReducer2.class); 
         
         //   Path outPath = new Path(output);
            Path outPath = new Path("SpeciesRank" + i);
		    FileInputFormat.addInputPath(conf, new Path(input));
		    FileOutputFormat.setOutputPath(conf, outPath);

		    FileSystem dfs = FileSystem.get(outPath.toUri(), conf);
		      if (dfs.exists(outPath)) {
			     dfs.delete(outPath, true);
		      }

             client.setConf(conf); 
             try { 
             JobClient.runJob(conf); 
             } catch (Exception e) { 
             e.printStackTrace(); 
             } 
        
        }
 
    } 
 } 
 