import javax.swing.UIManager;


public class calcDriver {
	public static void main (String args[])
	{
	    try {
	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	      graphingWindow test = new graphingWindow();
	      } catch(Exception e) {
	    	  e.printStackTrace();
	        System.out.println("Error setting native LAF: " + e);
	      }
	      
	   
		
		
	}
}
