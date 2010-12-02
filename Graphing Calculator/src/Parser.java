import java.util.StringTokenizer;

public class Parser {

	// if topOP > newOP
	public boolean higherprec(char topop, char newop) {

		if (topop == '(') {
			if (newop == '(')
				return false;
			else
				return true;
		}

		if (topop == '^') {
			if (newop == '(')
				return false;
			else
				return true;
		}

		if (topop == '/') {
			if (newop == '(' || newop == '/' || newop == '^')
				return false;
			else
				return true;
		}

		if (topop == '*') {
			if (newop == '(' || newop == '/' || newop == '*')
				return false;
			else
				return true;
		}

		if (topop == '-') {
			if (newop == '(' || newop == '/' || newop == '*' || newop == '-')
				return false;
			else
				return true;
		}

		// topop must be '+'
		else
			return false;
	}

	// is an operator?
	public boolean isop(char x) {
		return (x == '+' || x == '-' || x == '*' || x == '/' || x == '^');
	}

	// is a math function?
	public boolean ismath(String x) {
		if (x.compareTo("sin(") == 0)
			return true;
		if (x.compareTo("cos(") == 0)
			return true;
		if (x.compareTo("tan(") == 0)
			return true;
		if (x.compareTo("ln(") == 0)
			return true;
		if (x.compareTo("log(") == 0)
			return true;
		if (x.compareTo("sqrt(") == 0)
			return true;

		else
			return false;
	}

	// is a math function?
	public boolean ismath(char x) {
		return false;
	}

	// is a right parenthesis?
	public boolean isrightparen(char x) {
		return (x == ')');
	}

	public void makeTree(String equation) {

		Stack num = new Stack(); // NUMSTACK
		Stack ops = new Stack(); // OP STACK
		String tempstr; // token string
		String laststr = ""; // previous token string
		@SuppressWarnings("unused")
		char topnodeop = '_'; // to buffer last operator
		boolean consecutiveNums = false;

		// tokenize the string
		StringTokenizer tokenZ = new StringTokenizer(equation);

		// while more tokens to process
		while (tokenZ.hasMoreTokens()) {

			// extract next token
			tempstr = tokenZ.nextToken();

			if (tempstr.charAt(0) == '~') { // THROW EXCEPTION!!!
				if (!tokenZ.hasMoreTokens()) // EQUATION CAN'T END WITH ~
					throw (new validationException(
							"Invalid negation (~) at end of equation"));
				consecutiveNums = false;
			}

			// OPERATORS and LEFT PARENS and MATH FUNCTIONS
			else if ((isop(tempstr.charAt(0))) || (tempstr.charAt(0) == '(')
					|| ismath(tempstr)) {

				char newop = tempstr.charAt(0); // current operator
				Node topnode = null; // node to buffer last operator
				consecutiveNums = false;

				if (ismath(tempstr)) {
					ExpTree newexp = new ExpTree(tempstr, null, null);
					Node newnode = new Node();
					newnode.element = newexp;
					newnode.next = null;
					ops.push(newnode);
				}
				// if empty stack or left paren, push new operator
				else if (ops.isempty() || newop == '(') {
					ExpTree newexp = new ExpTree(newop, null, null);
					Node newnode = new Node();
					newnode.element = newexp;
					newnode.next = null;
					ops.push(newnode);
				}

				// if op stack is not empty
				else {

					// extract op from top of stack
					topnode = ops.pop();
					topnodeop = topnode.element.getOp();

					if (topnode.element.getOp() == '(') {
						// push back left paren
						ops.push(topnode);

						// push new op on top
						Node newnode = new Node();
						ExpTree newexp = new ExpTree(newop, null, null);
						newnode.element = newexp;
						newnode.next = null;
						ops.push(newnode);
					}

					// if higher precedence ( * then + )
					else if (higherprec(topnode.element.getOp(), newop)) {

						// reconstitute top num node with numbers from stack
						Node num2 = num.pop();
						Node num1 = num.pop();
						ExpTree num1E = num1.element;
						ExpTree num2E = num2.element;
						topnode.element.setleft(num1E);
						topnode.element.setright(num2E);
						num.push(topnode);

						// push new op node
						Node newnode = new Node();
						ExpTree newexp = new ExpTree(newop, null, null);
						newnode.element = newexp;
						newnode.next = null;
						ops.push(newnode);
					}

					// lower precedence ( + , * )
					else {
						// push back top op
						ops.push(topnode);

						// push new op on top
						Node newnode = new Node();
						ExpTree newexp = new ExpTree(newop, null, null);
						newnode.element = newexp;
						newnode.next = null;
						ops.push(newnode);
					}
				}
			}

			// RIGHT PAREN TOKEN
			else if (isrightparen(tempstr.charAt(0))) {

				// if no matching left paren
				if (ops.isempty()) // THROW EXCEPTION!!!
					throw (new validationException(
							"Unbalanced right parenthesis"));

				Node popnode = ops.pop();

				if (ismath(laststr)) // THROW EXCEPTION
					throw (new validationException(
							"Math function with no arguments"));
				if (laststr.charAt(0) == '(' || ismath(laststr)) // THROW
																	// EXCEPTION!!!
					throw (new validationException("Empty parentheses\n"));
				if (laststr.charAt(0) == '~') // THROW EXCEPTION!!!
					throw (new validationException(
							"Invalid negation (~) before right parenthesis"));

				// this handles the case of a single math function argument
				if (ismath(popnode.element.getOpS())) {
					Node tempnum = num.pop();
					ExpTree tempnumE = tempnum.element;
					popnode.element.setleft(tempnumE);
					num.push(popnode);
				}

				else {
					while (popnode.element.getOp() != '('
							&& !ismath(popnode.element.getOpS())) {

						if (num.isempty()) // THROW EXCEPTION!!!
							throw (new validationException("Invalid equation!"));
						Node num2 = num.pop();
						ExpTree num2E = num2.element;
						popnode.element.setright(num2E);

						if (num.isempty()) // THROW EXCEPTION!!!
							throw (new validationException("Invalid equation!"));
						Node num1 = num.pop();
						ExpTree num1E = num1.element;
						popnode.element.setleft(num1E);

						num.push(popnode);

						if (ops.isempty()) { // THROW EXCEPTION!!!
							throw (new validationException(
									"Unbalance right parenthesis\n"));
						}

						popnode = ops.pop();
					}

					// this handles the case of a single math function argument
					if (ismath(popnode.element.getOpS())) {
						Node tempnum = num.pop();
						ExpTree tempnumE = tempnum.element;
						popnode.element.setleft(tempnumE);
						num.push(popnode);
					}
				}
			}

			// NUMERICAL or VARIABLE TOKEN
			else {

				if (consecutiveNums) // THROW EXCEPTION
					throw (new validationException(
							"Consecutive numbers/variables"));

				Node newnode = new Node();
				ExpTree newexp;
				if (tempstr.charAt(0) == 'x') {
					newexp = new ExpTree(tempstr.charAt(0));
				} else {
					double x = Double.parseDouble(tempstr);
					newexp = new ExpTree(x);
				}

				newnode.element = newexp;
				newnode.next = null;
				num.push(newnode);
				consecutiveNums = true;
			}

			
			laststr = tempstr;
		}

		// EMPTY STACKS AND BUILD EXPTREE
		while (!ops.isempty()) {
			Node op1 = ops.pop();

			if (num.isempty()) // THROW EXCEPTION!!!
				throw (new validationException("Invalid equation"));

			Node num2 = num.pop();
			if (num.isempty()) // THROW EXCEPTION!!!
				throw (new validationException("Invalid equation"));

			Node num1 = num.pop();

			ExpTree op1E = op1.element;
			ExpTree num2E = num2.element;
			ExpTree num1E = num1.element;

			op1E.setleft(num1E);
			op1E.setright(num2E);

			num.push(op1);
		}

		
	}

}