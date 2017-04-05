package gandhi.GA;

 import java.io.IOException; 
 import java.util.Iterator; 
  
 import org.apache.hadoop.io.WritableComparable; 
 import org.apache.hadoop.mapred.MapReduceBase; 
 import org.apache.hadoop.mapred.OutputCollector; 
 import org.apache.hadoop.mapred.Reducer; 
 import org.apache.hadoop.mapred.Reporter; 
 import org.apache.hadoop.io.Text; 

public class GAFitnessReducer extends MapReduceBase implements Reducer<WritableComparable, Text, Text, Text> { 
  
   public void reduce(WritableComparable key, Iterator values, 
                      OutputCollector output, Reporter reporter) throws IOException { 
            while (values.hasNext()) {
               String[] outcomes1 = values.next().toString().split("\t");
               if(values.hasNext())
               {
               String[] outcomes2 = values.next().toString().split("\t");
               String chromosome1 = outcomes1[0];
               double score1 = Double.parseDouble(outcomes1[1]);
               String chromosome2 = outcomes2[0];
               double score2 = Double.parseDouble(outcomes2[1]);
               Chomosone c = new Chomosone(Genetic.target);
               String crossover = c.crossOver(chromosome1, chromosome2);
               String[] newmembers = crossover.split(" ");
               chromosome1 = newmembers[0];
               chromosome2 = newmembers[1];
               chromosome1 = c.mutate(chromosome1);
               chromosome2 = c.mutate(chromosome2);
               String scoretotal1 = c.scoreChromo(Genetic.target,chromosome1);
               String scoretotal2 = c.scoreChromo(Genetic.target,chromosome2);
               String[] finalscores1 = scoretotal1.split(":");
               String[] finalscores2 = scoretotal2.split(":");
               if (Integer.parseInt(finalscores1[0]) == Genetic.target && c.isValid(chromosome1)) { 
                  output.collect(new Text("Solution"), new Text(c.decodeChromo(chromosome1))); 
                  //output.collect(new Text("Chromosome"), new Text(chromosome1));
                 // output.collect(new Text("final fitness score"), new Text(finalscores1[1]));
                  System.exit(0);
               }else
               {
                  output.collect(new Text(chromosome1), new Text(finalscores1[1]));
               }
               if (Integer.parseInt(finalscores2[0]) == Genetic.target && c.isValid(chromosome2)) { 
                  output.collect(new Text("Solution"), new Text(c.decodeChromo(chromosome2)));
                  output.collect(new Text("Chromosome"), new Text(chromosome2));
                  output.collect(new Text("final fitness score"), new Text(finalscores2[1]));
                  System.exit(0);
               }else
               {
                  output.collect(new Text(chromosome2), new Text(finalscores2[1]));
               }
               }else
               {
                  output.collect(new Text(outcomes1[0]), new Text(outcomes1[1]));
               }
            }
   }
}  