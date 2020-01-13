import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

/** 
 * CalculateController Class
 * The controller for the Calculate button (to calculate final mark and Calculate excel file)
 * @author William San
 * @since 1/12/20 
 */ 
public class CalculateController implements ActionListener {
	private View view;     // The GUI view for display
	private Model model;   // The model for calculations
	
	boolean error = false; // Determines if an error occurred
	
	/**
	 * Default constructor
	 * @param view The GUI view for display
	 */ 
	public CalculateController(View view, Model model) {
		this.view = view;
		this.model = model;
  	}
  
	/** 
	 * Sends all text field information to model and performs calculations
	 * @param e The event sent from the button 
	 */
  	public void actionPerformed(ActionEvent e) {
  		// Store course information
  		this.model.setCourse(this.view.courseText.getText());
  		
  		// Store grade information
  		this.model.resetGrades();
  		
  		for (int i = 0; i < view.entries.size(); i++) {
  			JTextField assessmentText = this.view.getEntryField(i, View.ASSESSMENT_MODE);
			JTextField gradeText = this.view.getEntryField(i, View.GRADE_MODE);
			JTextField weightText = this.view.getEntryField(i, View.WEIGHT_MODE);
			
			if (validInput(gradeText) && validInput(weightText)) {
				double mark = Double.parseDouble(gradeText.getText());
				double weight = Double.parseDouble(weightText.getText());
				this.model.setGrade(assessmentText.getText(), mark, weight);
			}
			else {
				break;
			}
  		}
  		
  		// Store desired mark information
  		if (validInput(this.view.desiredText) && !error) {
  			this.model.setDesiredMark(Double.parseDouble(this.view.desiredText.getText()));
  		}
  		
  		// Calculate final mark
  		if (!error) {
  			this.model.generateMark();
  			this.model.notifyObservers();
  		}
  	}
  	
  	/** 
  	 * Validating if the inputs are actually numbers 
  	 */
    private boolean validInput(JTextField input) {
    	try {
    		if (Double.parseDouble(input.getText()) < 0 || Double.parseDouble(input.getText()) > 100) {
    			View.errorMessage(2);
    			return false;
    		}
    		else {
    			return true;
    		}
    	} 
    	catch (NumberFormatException e) {
    		View.errorMessage(1);
    		error = true;
    		return false;
    	}
    }
}