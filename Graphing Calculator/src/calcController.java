import java.util.ArrayList;

public class calcController {

	public ArrayList<graphPoint> runEq(String equation) throws Exception, validationException {
		

		if (Validator.isValid(equation))
		{
			return Eqrunner.run(equation);
		}
		return null;
	}

	public calcController() {

	}

}
