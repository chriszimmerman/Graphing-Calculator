public class ExpTree {

	// CONSTRUCTOR (op)
	ExpTree(char x, ExpTree l, ExpTree r){
		opval = x;
		left = l;
		right = r;
		opvalS = "";
	}

	// CONSTRUCTOR (math fn)
	ExpTree(String x, ExpTree l, ExpTree r){
		opvalS = x;
		left = l;
		right = r;
	}

	// CONSTRUCTOR (num)
	ExpTree(double v){
		num = v;
		left = right = null;
		opvalS = "";
	}

	// set left child
	void setleft(ExpTree exp){
		left = exp;
	}

	// get left child
	ExpTree getleft(){
		return( left );
	}

	// set right child
	public void setright(ExpTree exp){
		right = exp;
	}

	// get right child
	public ExpTree getright(){
		return( right );
	}

	// returns op character
	public char getOp(){
		return opval;
	}

	// returns opS string
	public String getOpS(){
		return opvalS;
	}

	// returns number value
	public double getNum(){
		return num;
	}
	
	/* FOR TESTING

	public void printExp(){
		System.out.print("__op:" + opval + " num:" + num + "__");
	}

	public double eval(ExpTree tree){
		// INSTEAD OF EVALUATE, FORMAT STRING
		if     ( tree.getOp() == '+' )
			return( eval(tree.getleft()) + eval(tree.getright()) );
		else if( tree.getOp() == '-' )
			return( eval(tree.getleft()) - eval(tree.getright()) );
		else if( tree.getOp() == '*' )
			return( eval(tree.getleft()) * eval(tree.getright()) );
		else if( tree.getOp() == '/' )
			return( eval(tree.getleft()) / eval(tree.getright()) );
	//	else if( tree.getOp() == '^' )
	//		return( eval(Math.pow(tree.getleft(), tree.getright())));
		else 
			return( tree.getNum() );
	}

	public double cal(){
		//return eval(this);
	}
	*/
	
	private String opvalS;
	private char opval;
	private double num;
	private ExpTree left;
	private ExpTree right;
	
};