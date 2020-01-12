import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** 
 * AddController Class
 * The controller for the add button (to add another input field)
 * @author William San
 * @since 1/04/20 
 */ 
public class ResetController implements ActionListener {
	View view; // The GUI view for display
	
	/**
	 * Default constructor
	 * @param view The GUI view for display
	 */ 
	public ResetController(View view) {
		this.view = view;
  	}
  
	/** 
	 * Adds a new input box
	 * @param e The event sent from the button 
	 */
  	public void actionPerformed(ActionEvent e) {
  		view.reset();
  	}
}