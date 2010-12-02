import org.codehaus.janino.ExpressionEvaluator;
import java.util.ArrayList;
import java.util.*;

public class Eqrunner
{
	final static int MAX_PTS = 1000; //range of x coordinates to graph
	

	//takes in an expression as a string and turns it into Java byte code
	public static ArrayList<graphPoint> run(String expression) throws Exception
	{
		expression = formatString(expression); //turn the string into readable Java code
		
		ArrayList<graphPoint> graphC = new ArrayList<graphPoint>(); //contains x,y coordinates for the graph
		
		// adds MAX_PTS Point objects into the array list and initializes the x coordinate of
		// each point
		for(int i = 0; i < MAX_PTS; i++)
		{
			graphC.add(new graphPoint());
			graphC.get(i).setX(i);
		}
		
		// Compile the expression once 
		ExpressionEvaluator ee = new ExpressionEvaluator(              
		    expression,								// the expression we want to compile
		    double.class,                           // we want the expression to evaluate to a double
		    new String[] { "x" },           		// we use the x variable as a parameter in the expression
		    new Class[] { double.class } 			// the x variable parameter is a double
		);		
		
		// Evaluate the expression for every point in the ArrayList 
		for(int i = 0; i < MAX_PTS; i++)
		{
			Double res = (Double) ee.evaluate(
			    new Object[] 
			               {          
			    			new Double(i), //insert a value for the x variable parameter
			    }
			);
			
			// the expression, with the value for x plugged in, evaluates to a double value.
			// this value is assigned to res, which gets plugged in as the y coordinate for its
			// respective point
			graphC.get(i).setY(res);	
		}
		
		return graphC;
	}

	//Replace functions like sin and cos with the appropriate Java math method
	public static String formatString(String dirtyString)
	{
		dirtyString = purgeString(dirtyString, "sin", "Math.sin");
		dirtyString = purgeString(dirtyString, "cos", "Math.cos");
		dirtyString = purgeString(dirtyString, "tan", "Math.tan");
		dirtyString = purgeString(dirtyString, "sqrt", "Math.sqrt");
		dirtyString = purgeString(dirtyString, "log", "Math.log");
		dirtyString = purgeString(dirtyString, "~", "-");

		dirtyString = replace(dirtyString);
		return dirtyString;
	}
	
	//Go through the input string and break it into tokens.
	//if the string we're looking for is found, replace it with the replacement string
	public static String purgeString(String dirtyString, String find, String replacement) 
	{
	    StringBuilder result = new StringBuilder(dirtyString.length());
	    String delimiters = "+-*/(),. ";
	    StringTokenizer st = new StringTokenizer(dirtyString, delimiters, true);
	    while (st.hasMoreTokens()) 
	    {
	        String w = st.nextToken();
	        if (w.equals(find)) 
	        {
	            result.append(replacement);
	        } 
	        else 
	        {
	            result.append(w);
	        }
	    }
	    return result.toString();
	}
	
	// This method searches for carats, aka the exponent operator (^). If one is found, the left
	// and right operands of the carat are extracted from the string, placed in a Math.pow()
	// method, and placed back into the string. This is VERY sloppily done.
	
	public static String replace(String input)
	{
		int pos = 0; //character position in the string
		int s,f; //starting and finishing markers for substrings
		int parenCount; //keeps track of unbalanced parentheses
		String newString; //the formatted string that will be returned
		String pre, post; //substrings that will hold the non-power operator parts of the expression
		String op1, op2; //substrings that will hold the operators for the Math.pow function
		
		char[] inputArray = new char[input.length()]; //input string converted to an array of characters
		inputArray = input.toCharArray();
	
		//iterate through each character in the string
		while(pos != input.length())
		{
			if(inputArray[pos] == '^') //if it's a carat, get the operator substrings
			{
				parenCount = 0;
				
				//set the start and finish counters to one character left of the carat
				f = pos - 1;
				s = pos - 1;
				
				//move left until we get all the characters of the first power operator
				do
				{
					s--;
					if(inputArray[s] == ')') //right parentheses add to the parenCount 
						parenCount++;
					if(inputArray[s] == '(') //left parentheses balance out and thus take away from the parenCount
						parenCount--;
					if(s==0) //if we're at the first character, break to avoid an outOfBounds exception
						break;
				}while(parenCount != 0 || inputArray[s] != ' ' || (inputArray[s] == '(' && inputArray[s-1] != ' '));
				//keep going left to get the first operator until we balance out parentheses and are in white space
				
				char[] preArray = new char[s+1]; //contains the substring of the expression before the first power operand
				for(int i = 0; i <= s; i++)
				{
					preArray[i] = inputArray[i];
				}
				pre = new String(preArray); //convert the character array to a String for concatenation later
				
				
				char[] op1Array = new char[f-s-1]; //contains the first operand of the power function
				for(int i = s+1; i < f; i++)
				{
					op1Array[i-s-1] = inputArray[i];
				}
				op1 = new String(op1Array); //convert the 1st operator to a String for concatenation later
				
				//now we get the 2nd operand, which is to the right of the carat.
				//reset the parentheses counter and set the start and finish markets to
				//one character left of the carat
				parenCount = 0;
				f = pos + 1;
				s = pos + 1;
			
				//move right until we get all the characters in the second power operator
				do
				{
					f++;
					if(inputArray[f] == '(')
						parenCount++;
					if(inputArray[f] == ')')
						parenCount--;
					if(f==input.length()-1)
						break;
				}while(parenCount != 0 || inputArray[f] != ' ');
				// move while parens are unbalanced and we're not at white space
				
				// contains the substring of the expression to the right of the
				// second power operand
				char[] postArray = new char[input.length()-f];
				for(int i = f; i < input.length(); i++)
				{	
					postArray[i-f] = inputArray[i];
				}
				post = new String(postArray);
				
				// conaints the substring of the second power operator
				char[] op2Array = new char[f-pos-2];
				for(int i = pos+2; i < f; i++)
				{
					op2Array[i-pos-2] = inputArray[i];
				}
				op2 = new String(op2Array);
				
				// replace the expression with the Math.pow() method and call replace
				// again in case there are any more carats in the expression
				newString = replace(pre + "Math.pow( " + op1 + " , " + op2 + " )" + post);
				
				return newString;
			}
			pos++; //if the character's not a carat, go to the next character in the string
		}
		return input;
	}

}
