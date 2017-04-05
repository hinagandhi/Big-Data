// Genetic Algorithm Node
	package gandhi.GA;
    
    import java.util.*;
    import java.lang.*;

	public class Chomosone {
		// The chromo
		public StringBuffer chromo		 = new StringBuffer(Genetic.chromoLen * 4);
		public StringBuffer decodeChromo = new StringBuffer(Genetic.chromoLen * 4);
		public double score;
		public int total;
		
		// Constructor that generates a random
		public Chomosone(int target) {
			
			// Create the full buffer
			for(int y=0;y<Genetic.chromoLen;y++) {
				// What's the current length
				int pos = chromo.length();
				
				// Generate a random binary integer
				String binString = Integer.toBinaryString(Genetic.rand.nextInt(Genetic.ltable.length));
				//System.out.println("chromoLen: " + Genetic.chromoLen);
				int fillLen = 4 - binString.length();
				
				// Fill to 4
				for (int x=0;x<fillLen;x++) chromo.append('0');
				
				// Append the chromo
				chromo.append(binString);
				
			}
			
			// Score the new cromo
			scoreChromo(target);
		}
					
		public Chomosone(StringBuffer chromo) { this.chromo = chromo; }
		
		// Decode the string
		public final String decodeChromo() {	

			// Create a buffer
			decodeChromo.setLength(0);
			
			// Loop throught the chromo
			for (int x=0;x<chromo.length();x+=4) {
				// Get the
				int idx = Integer.parseInt(chromo.substring(x,x+4), 2);
				if (idx<Genetic.ltable.length) decodeChromo.append(Genetic.ltable[idx]);
			}
			
			// Return the string
			return decodeChromo.toString();
		}
		
		// Scores this chromo
		public final void scoreChromo(int target) {
			total = addUp();
                     //   System.out.println("total: " + total);
			if (total == target) score = 0;
			score = (double)1 / (target - total);
                   //    System.out.println("score: " + score);   
                       
		}
		
		// Crossover bits
		public final String crossOver(String n1, String n2) {
             StringBuilder sb1 = new StringBuilder();
             sb1.append(n1);
             StringBuilder sb2 = new StringBuilder();
             sb2.append(n2);
			// Generate a random position
			
			int pos = Genetic.rand.nextInt(n1.length());
			
			// Swap all chars after that position
			for (int x=pos;x<sb1.length();x++) {
				// Get our character
				char tmp = sb1.charAt(x);
				
				// Swap the chars
				sb1.setCharAt(x, sb2.charAt(x));
				sb2.setCharAt(x, tmp);
			}
           StringBuilder sb = new StringBuilder();
           sb.append(sb1.toString() + " " + sb2.toString());
           return sb.toString();
		}
			
		// Mutation
		public final String mutate(String chromo) {
			StringBuilder sb = new StringBuilder();
			sb.append(chromo);
			for (int x=0;x<sb.length();x++) {
				if (Genetic.rand.nextDouble()<=Genetic.mutRate) 
					sb.setCharAt(x, (sb.charAt(x)=='0' ? '1' : '0'));
			}
			return sb.toString();
		}
			
		
				
		// Add up the contents of the decoded chromo
		public final int addUp() { 
		
			// Decode our chromo
			String decodedString = decodeChromo();
			
			// Total
			int tot = 0;
			
			// Find the first number
			int ptr = 0;
			while (ptr<decodedString.length()) { 
				char ch = decodedString.charAt(ptr);
				if (Character.isDigit(ch)) {
					tot=ch-'0';
					ptr++;
					break;
				} else {
					ptr++;
				}
			}
			
			// If no numbers found, return
			if (ptr==decodedString.length()) return 0;
			
			// Loop processing the rest
			boolean num = false;
			char oper=' ';
			while (ptr<decodedString.length()) {
				// Get the character
				char ch = decodedString.charAt(ptr);
				
				// Is it what we expect, if not - skip
				if (num && !Character.isDigit(ch)) {ptr++;continue;}
				if (!num && Character.isDigit(ch)) {ptr++;continue;}
			
				// Is it a number
				if (num) { 
					switch (oper) {
						case '+' : { tot+=(ch-'0'); break; }
						case '-' : { tot-=(ch-'0'); break; }
						case '*' : { tot*=(ch-'0'); break; }
						case '/' : { if (ch!='0') tot/=(ch-'0'); break; }
					}
				} else {
					oper = ch;
				}			
				
				// Go to next character
				ptr++;
				num=!num;
			}
			
			return tot;
		}

		public final boolean isValid(String chromo) { 
		
			// Decode our chromo
			String decodedString = decodeChromo(chromo);
			
			boolean num = true;
			for (int x=0;x<decodedString.length();x++) {
				char ch = decodedString.charAt(x);

				// Did we follow the num-oper-num-oper-num patter
				if (num == !Character.isDigit(ch)) return false;
				
				// Don't allow divide by zero
				if (x>0 && ch=='0' && decodedString.charAt(x-1)=='/') return false;
				
				num = !num;
			}
			
			// Can't end in an operator
			if (!Character.isDigit(decodedString.charAt(decodedString.length()-1))) return false;
			
			return true;
		}

		public final String scoreChromo(int target,String chromo) {
			total = addUp(chromo);
                     //   System.out.println("total: " + total);
			if (total == target) score = 0;
			score = (double)1 / (target - total);
                   //    System.out.println("score: " + score);
            StringBuilder sb = new StringBuilder();
            sb.append(String.valueOf(total)+":"+String.valueOf(score));     
            return sb.toString();  
		}
		public final String decodeChromo(String chromo) {	

			// Create a buffer
			decodeChromo.setLength(0);
			
			// Loop throught the chromo
			for (int x=0;x<chromo.length();x+=4) {
				// Get the
				int idx = Integer.parseInt(chromo.substring(x,x+4), 2);
				if (idx<Genetic.ltable.length) decodeChromo.append(Genetic.ltable[idx]);
			}
			
			// Return the string
			return decodeChromo.toString();
		}

		public final int addUp(String chromo) { 
		
			// Decode our chromo
			String decodedString = decodeChromo(chromo);
			
			// Total
			int tot = 0;
			
			// Find the first number
			int ptr = 0;
			while (ptr<decodedString.length()) { 
				char ch = decodedString.charAt(ptr);
				if (Character.isDigit(ch)) {
					tot=ch-'0';
					ptr++;
					break;
				} else {
					ptr++;
				}
			}
			
			// If no numbers found, return
			if (ptr==decodedString.length()) return 0;
			
			// Loop processing the rest
			boolean num = false;
			char oper=' ';
			while (ptr<decodedString.length()) {
				// Get the character
				char ch = decodedString.charAt(ptr);
				
				// Is it what we expect, if not - skip
				if (num && !Character.isDigit(ch)) {ptr++;continue;}
				if (!num && Character.isDigit(ch)) {ptr++;continue;}
			
				// Is it a number
				if (num) { 
					switch (oper) {
						case '+' : { tot+=(ch-'0'); break; }
						case '-' : { tot-=(ch-'0'); break; }
						case '*' : { tot*=(ch-'0'); break; }
						case '/' : { if (ch!='0') tot/=(ch-'0'); break; }
					}
				} else {
					oper = ch;
				}			
				
				// Go to next character
				ptr++;
				num=!num;
			}
			
			return tot;
		}
	}
