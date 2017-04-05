package gandhi.GA;

import java.io.IOException;
import java.io.StringReader;
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


public class Genetic {
	public static char[] ltable = {'0','1','2','3','4','5','6','7','8','9','+','-','*','/'};
	public static int chromoLen = 5;
	public static double crossRate = 0.1;
	public static double mutRate = 0.01;
	public static Random rand = new Random();
	public static int poolSize = 1000;
	public static int target= 80;
	int gen=0;		
}