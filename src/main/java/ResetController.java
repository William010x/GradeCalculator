import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** 
 * ResetController Class
 * The controller for the reset button (to empty all fields)
 * @author William San
 * @since 1/12/20 
 */ 
public class ResetController implements ActionListener {
	private View view; // The GUI view for display
	
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