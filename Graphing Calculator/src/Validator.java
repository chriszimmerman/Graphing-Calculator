public class Validator {

	public static boolean isValid(String expression) throws validationException
	{
		
		System.out.println(expression);

		Parser parser = new Parser();
		parser.makeTree(expression);		
		return true;

		


	}

}