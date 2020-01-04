import javax.swing.*;

/** 
 * Calculator Class
 * Opens a window for a grade calculator
 * @author William San
 * @since 01/01/20 
 */ 
public class Calculator {
	//Instance Variables
	//static Model model = new Model();  //The simulator model for calculations
	static View view = new View();     //The simulator view for display
	
	/**
	 * Main method
	 * @param args An array of commands that can be used on startup 
	 */
	public static void main (String [] args) {
		//Initialize the Frame
		JFrame frame = new JFrame("Grade Calculator");
		frame.setSize(620, 600);
		frame.setResizable(false);
		frame.setLocation(100, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setContentPane(view);
    	frame.setVisible(true);
	}
}
