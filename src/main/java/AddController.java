import java.awt.event.*;

/** 
 * AddController Class
 * The controller for the add button (to add another input field)
 * @author William San
 * @since 1/04/20 
 */ 
public class AddController implements ActionListener {
	View view; // The GUI view for display
	
	/**
	 * Default constructor
	 * @param view The GUI view for display
	 */ 
	public AddController(View view) {
		this.view = view;
  	}
  
	/** 
	 * Adds a new input box
	 * @param e The event sent from the button 
	 */
  	public void actionPerformed(ActionEvent e) {
  		view.addEntry();
  	}
}