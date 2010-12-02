public class Stack {

	// CONSTRUCTOR
	public Stack(){
		top = null;
	}

	// PUSH ELEMENT
	public void push(Node node){

		if (top==null) {
			top = node;
		}

		else {
			Node tempnode = top;
			while( tempnode.next != null )
				tempnode = tempnode.next;
			tempnode.next = node;
		}

	}

	// POP ELEMENT
	public Node pop(){

		Node node;

		// if only one node on stack
		if( top.next == null ){
			node = top;
			top = null;
			return node;
		}

		else {
			Node tempnode = top;
			while (tempnode.next.next != null)
				tempnode = tempnode.next;
			node = tempnode.next;
			tempnode.next = null;
			return node;
		}

	}

	// is stack empty?
	public boolean isempty(){
		return (top==null);
	}

	/* FOR TESTING
	public void printStack(){
		Node pNode = top;
		System.out.print("Stack : ");
		while (pNode != null){
			pNode.element.printExp();
			pNode = pNode.next;
		}
	}
	*/
	
	private Node top;

};