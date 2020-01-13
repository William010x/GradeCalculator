import javax.swing.*;

/** 
 * Calculator Class
 * Opens a window for a grade calculator
 * @author William San
 * @since 01/01/20 
 */ 
public class Calculator {
	/**
	 * Main method
	 * @param args An array of commands that can be used on startup 
	 */
	public static void main (String [] args) {
		// Instance variables
		Model model = new Model();   //The simulator model for calculations
		View view = new View(model); // The GUI view for display
		
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
