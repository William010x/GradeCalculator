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
	private View view;   // The GUI view for display
	private Model model; // The model for calculations
	
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
				System.out.println("Error. Invalid input.");
				break;
			}
  		}
  		
  		// Store desired mark information
  		if (validInput(this.view.desiredText)) {
  			this.model.setDesiredMark(Double.parseDouble(this.view.desiredText.getText()));
  		}
  		else {
  			System.out.println("Error. Invalid input.");
  		}
  		
  		// Calculate final mark
  		this.model.generateMark();
  		
  		this.view.examText.setText(Double.toString(this.model.getExamMark()));
  	}
  	
  	
  	
  	/** 
  	 * Validating if the inputs are actually numbers 
  	 */
    private boolean validInput(JTextField input) {
    	try {
    		Double.parseDouble(input.getText());
    		return true;
    	} 
    	catch (NumberFormatException e) {
    		return false;
    	}
    }
}