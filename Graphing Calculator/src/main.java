public class main {

	public static void main(String args[]){
		
		Validator.isValid("40 / ( 3 + 2 )");
		Validator.isValid("40 / ( 3 )");
		Validator.isValid("40 / ( 3 + 2 ) ~");
		Validator.isValid("sqrt( 3 * ( 6 + 1 ) ^ 5 )");
		Validator.isValid("40 / ( 3 + 2 ~ )");
		Validator.isValid("2 +  + 4");
		Validator.isValid("1 + 16 / (  )");									// Empty paren
		Validator.isValid("3.4 / 1.7");
		Validator.isValid("3 + ln( )");										// Empty math fn
		Validator.isValid("3 + 4 / 2");
		Validator.isValid("~ 3 + ~ 4");
		Validator.isValid("~ 3 ^ x");
		Validator.isValid("~ x / 4");
		Validator.isValid("3 + / 4 2");
		Validator.isValid("3 4 + 2");										// ConsecutiValidatore numbers/Validatorariables
		Validator.isValid("3 x + 3");										// ConsecutiValidatore numbers/Validatorariables
		Validator.isValid("3 + / 2");										// ConsecutiValidatore binary operators
		Validator.isValid("( 3 + 7 ) / ( ( 16 - 12 ) *  1 / 2 ) )");		// Unbalanced rparen
		Validator.isValid("( 3 + 7 ) / ln( ( ( 16 - 12 ) *  1 / 2 ) )");
		Validator.isValid("( 3 + 7 ) / ( ( 16 - 12 ) * ( 1 / 2 ) )");
		Validator.isValid("1 + 100 / ( ( 16 - 2 * 2 ) + 13 ) - 5 / 5");
		Validator.isValid("1 + 100 / ( ( x - 2 * 2 ) + x ) - 5 / 5");
	}	

	
}