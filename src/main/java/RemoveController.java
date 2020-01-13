import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/** 
 * RemoveController Class
 * The controller for the remove button (to delete an input field)
 * @author William San
 * @since 1/04/20 
 */ 
public class RemoveController implements ActionListener {
	private View view; // The GUI view for display
	private int index; // The index of he entry in the list of entries
	
	/**
	 * Default constructor
	 * @param view The GUI view for display
	 */ 
	public RemoveController(View view, int index) {
		this.view = view;
		this.index = index;
  	}
  
	/** 
	 * Removes the input box associated with index
	 * @param e The event sent from the button 
	 */
  	public void actionPerformed(ActionEvent e) {
  		view.removeEntry(index);
  	}
}