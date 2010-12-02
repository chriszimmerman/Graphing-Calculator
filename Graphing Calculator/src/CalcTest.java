import org.codehaus.janino.ExpressionEvaluator;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
  


public class CalcTest 
{
	String test1, test2, test3, test4, test5, test6, test7, test8, test9, test10,
		  test11, test12, test13, test14, test15, test16, test17, test18, test19, test20; 
	
	@Test
	@Before public void init()
	{
		test1 = new String("( x + 3 )");
		test2 = new String("( 3 + 7 - 4 )");
		test3 = new String("( 8 / 6 * 4 + 3 )");
		test4 = new String("( ( x + 5 ) * ( x - 7 ) )");
		test5 = new String("( sin( x ) )");
		test6 = new String("( x ^ 2 )");
		test7 = new String("( sin( x ) * x )");
		test8 = new String("( sin( x ) ^ 5 )");
		test9 = new String("( ( 0 * x ) / ( 7 + 4 ) )");
		test10 = new String("( cos( x ^ 3 ) )");
		test11 = new String("( sin( x + 7 - 4 / 8 ) )");
		test12 = new String("( ( x + 7 ) ^ ( 3 - x ) )");
		test13 = new String("( sin( x - 7 ^ 2 ) )");
		test14 = new String("( - ( x * 4 ) )");
		test15 = new String("( sqrt( 7 * x + 3 ) )");
		test16 = new String("( ( 2 * x + 3 ) )");
		test17 = new String("( 7 - sqrt( cos( x ) ) )");
		test18 = new String("( sin( cos( sqrt( x ) ) ) )");
		test19 = new String("( x ^ x )");
		test20 = new String("( ( ( 4 * 3 ) + (x + 4 ) ) * x )");
		org.junit.runner.JUnitCore.runClasses(CalcTest.class);
	}
	
	@Test
	public void testStrings(String test) throws Exception
	{
		// Compile the expression once; relatively slow.
		ExpressionEvaluator ee = new ExpressionEvaluator(              
				test,							// expression
				double.class,                           // expressionType
				new String[] { "x" },           // parameterNames
				new Class[] { double.class } // parameterTypes
		);		
	
		// Evaluate it with varying parameter values; very fast.
		Double res = (Double) ee.evaluate(
		    new Object[] {          // parameterValues
		        new Double(4),

		    }
		);
	}
	
	
}
