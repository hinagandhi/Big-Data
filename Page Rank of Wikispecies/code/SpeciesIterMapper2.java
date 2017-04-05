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
  
 import org.apache.hadoop.io.Writable; 
 import org.apache.hadoop.io.WritableComparable; 
 import org.apache.hadoop.mapred.MapReduceBase; 
 import org.apache.hadoop.mapred.Mapper; 
 import org.apache.hadoop.mapred.OutputCollector; 
 import org.apache.hadoop.mapred.Reporter; 
 import org.apache.hadoop.io.Text; 
  
  
 public class SpeciesIterMapper2 extends MapReduceBase implements Mapper<WritableComparable, Writable, Text, Text> { 
  
   public void map(WritableComparable key, Writable value, 
                   OutputCollector output, Reporter reporter) throws IOException { 
  
     // get the current page
     String data = ((Text)value).toString(); 
     int index = data.indexOf(":"); 
     int previous_page_rank = 0;
     if (index == -1) { 
       return; 
     } 
     
     // split into title and PR (tab or variable number of blank spaces)
     String toParse = data.substring(0, index).trim();
     //System.out.println("toParse: " + toParse); 
     String[] splits = toParse.split("\t"); 
     if(splits.length == 0) {
       splits = toParse.split(" ");
            if(splits.length == 0) {
               return;
            }
     }
     String pagetitle = splits[0].trim(); 
     // System.out.println("pagetitle: " + pagetitle);
     String pagerank = splits[splits.length - 1].trim();
      //System.out.println("pagerank: " + pagerank);
    
     // parse current score
     double currScore = 0.0;
     try { 
        currScore = Double.parseDouble(pagerank); 
     } catch (Exception e) { 
        currScore = 1.0;
     } 

     // get number of outlinks
     data = data.substring(index + 1); 
     String[] pages = data.split(" "); 
     int numoutlinks = 0;
     StringBuilder sb = new StringBuilder();
     if (pages.length == 0) {
        numoutlinks = 1;
     } else {
       for (String page : pages) { 
         if(page.length() > 0) {
            numoutlinks = numoutlinks + 1;
            sb.append(" " + page);
         }
       } 
     }
     
     // for(String s : pages){
     //  System.out.println("string: " + s);
     //   sb.append(s);
     // }
     double N = 1/currScore ;
     // collect each outlink, with the dampened PR of its inlink, and its inlink
     double dampingFactor  =  (.85 * currScore / numoutlinks) ; 
     String num =   new Double(dampingFactor).toString() + ":";
     Text toEmit = new Text(num);
     //toEmit =  newText(":") + toEmit; 
     for (String page : pages) { 
       if(page.length() > 0 && !page.equals(key) ) {
       // System.out.println("page: " + page);
         output.collect(new Text(page), toEmit); 
       //  output.collect(new Text(page), new  Text(" " + pagetitle));
         // System.out.println("key: " + page);
         // System.out.println("value 1: " + pagetitle);
         // System.out.println("value page rank: " + toEmit);
         // System.out.println("values: " + sb.toString());

       //  output.collect(new Text(page), new Text(sb.toString()));
       }
     } 

     // collect the inlink with its dampening factor, and all outlinks
     output.collect(new Text(pagetitle), new Text(new Double(.15/numoutlinks).toString())); 
     //System.out.println("sb: " + sb.toString());
     // ": " + 
     output.collect(new Text(pagetitle), new Text(sb.toString())); 
   } 
 } 
 