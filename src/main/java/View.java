import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
	private static final int DEFAULT_ENTRIES = 3; // Default number of entries when starting
	// Model model;
	
	JButton add;       // Add button
	JButton calculate; // Calculate button
	JButton reset;     // Reset button
	
	static JFrame error = new JFrame(); // Frame for displaying errorPane
	static JOptionPane errorPane;       // Pane showing error message
	JPanel input;                       // Panel containing all input fields
	JScrollPane inputPane;              // Scroll pane containing input panel
	JPanel addPanel;                    // Panel containing add button
	JPanel markPanel;                   // Panel containing final mark calculations
	JPanel desired;                     // Panel containing desired mark
	JPanel exam;                        // Panel containing final exam mark required
	JPanel buttonPanel;                 // Panel containing the buttons
	
	JTextField desiredText;
	JTextField examText;
	
	ArrayList<JPanel> entries = new ArrayList<JPanel>(); // List of all components of entries to enter grades
  
	/** 
	 * Constructor
	 * @param model Model containing all data for grades 
	 */
	public View() {
		super();
		//this.model = model;
		//this.model.setView(this);
		this.layoutView();
		this.registerControllers();
		this.update();
	}
  
	private JPanel createEntry() {
		// Initializing components
		JTextField assessment = new JTextField(25);
		JTextField grade = new JTextField(7);
		JTextField weight = new JTextField(7);
		
		assessment.setHorizontalAlignment(JTextField.CENTER);
		grade.setHorizontalAlignment(JTextField.CENTER);
		weight.setHorizontalAlignment(JTextField.CENTER);
		
		JButton remove = new JButton("Remove");
		
		// Setting layout
		JPanel entry = new JPanel();
		//FlowLayout layout = new FlowLayout();
		//layout.setHgap(10);
		BoxLayout layout = new BoxLayout(entry, BoxLayout.X_AXIS);
		entry.setBackground(Color.white);
		entry.setLayout(layout);
		entry.setBorder(BorderFactory.createLoweredBevelBorder());
				
		// Setting borders
		assessment.setBorder(BorderFactory.createTitledBorder("Assessment"));
		grade.setBorder(BorderFactory.createTitledBorder("Grade (%)"));
		weight.setBorder(BorderFactory.createTitledBorder("Weight (%)"));

		// Setting sizes
		assessment.setMaximumSize(new Dimension(250, 40));
		grade.setMaximumSize(new Dimension(100, 40));
		weight.setMaximumSize(new Dimension(100, 40));
		remove.setMaximumSize(new Dimension(60, 25));		
		
		// Adding components
		entry.add(Box.createRigidArea(new Dimension(5, 0)));
		entry.add(assessment);
		entry.add(Box.createRigidArea(new Dimension(10, 0)));
		entry.add(grade);
		entry.add(Box.createRigidArea(new Dimension(10, 0)));
		entry.add(weight);
		entry.add(Box.createRigidArea(new Dimension(10, 0)));
		entry.add(remove);
		entry.add(Box.createRigidArea(new Dimension(10, 0)));

		return entry;
	}
  
	/** Lays out the components on the JPanel */
	private void layoutView() {
		// Initializing components
		for (int i = 0; i < DEFAULT_ENTRIES; i++) {
			entries.add(createEntry());
		}
		
		input = new JPanel();
		addPanel = new JPanel();
		markPanel = new JPanel();
		desired = new JPanel();
    	exam = new JPanel();
		buttonPanel = new JPanel();
		errorPane = new JOptionPane();
		
		add = new JButton("Add another grade");
		calculate = new JButton("Calculate");
		reset = new JButton("Reset");
		
		desiredText = new JTextField(7);
		examText = new JTextField(7);
		desiredText.setText("85");
    	desiredText.setHorizontalAlignment(JTextField.CENTER);
    	examText.setEnabled(false);

		JLabel desiredLabel = new JLabel("DESIRED MARK: ");
    	JLabel examLabel = new JLabel("REQUIRED EXAM MARK: ");
    	
		inputPane = new JScrollPane();
    	inputPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    
		// Setting layouts
		//BorderLayout layout = new BorderLayout();
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		//GridLayout inputLayout = new GridLayout(0, 1);
    	//FlowLayout inputLayout = new FlowLayout();
		BoxLayout inputLayout = new BoxLayout(input, BoxLayout.Y_AXIS);
		BoxLayout markLayout = new BoxLayout(markPanel, BoxLayout.X_AXIS);
    	BoxLayout buttonLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
    	
    	this.setLayout(layout);
    	input.setLayout(inputLayout);
    	markPanel.setLayout(markLayout);
    	buttonPanel.setLayout(buttonLayout);

    	// Set borders
    	//input.setBorder(BorderFactory.createEtchedBorder());
    	desired.setBorder(BorderFactory.createLoweredBevelBorder());
    	exam.setBorder(BorderFactory.createLoweredBevelBorder());
    
    	// Setting sizes
    	add.setMaximumSize(new Dimension(90, 30));
    	addPanel.setMaximumSize(new Dimension(620, 40));
    	desiredText.setMaximumSize(new Dimension(100, 40));
    	examText.setMaximumSize(new Dimension(100, 40));
    	markPanel.setMaximumSize(new Dimension(620, 500));
    	calculate.setMaximumSize(new Dimension(200, 70));
    	reset.setMaximumSize(new Dimension(200, 70));
    	
    	desired.setBackground(Color.white);
    	exam.setBackground(Color.white);
    	
    	Font font = new Font("Dialog", Font.BOLD, 14);
    	desiredLabel.setFont(font);
    	examLabel.setFont(font);
    	
    
    	// Adding sub-components
    	input.add(Box.createRigidArea(new Dimension(0, 10)));
    	for (int i = 0; i < entries.size(); i++) {
    		input.add(entries.get(i));
    		input.add(Box.createRigidArea(new Dimension(0, 10)));
    	}
    	input.add(Box.createRigidArea(new Dimension(0, 10)));
    	addPanel.add(add);
    	input.add(addPanel);
    	
    	inputPane.setViewportView(input);
    	
    	desired.add(desiredLabel);
    	desired.add(desiredText);
    	exam.add(examLabel);
    	exam.add(examText);
    	
    	markPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    	markPanel.add(desired);
    	markPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    	markPanel.add(exam);
    	markPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    	
    	buttonPanel.add(calculate);
    	//buttonPanel.add(Box.createRigidArea(new Dimension(25, 0)));
    	buttonPanel.add(new Box.Filler(new Dimension(5, 70), new Dimension(5, 70), new Dimension(70, 70)));
    	buttonPanel.add(reset);
    
    	error.add(errorPane);
    	
    	// Adding final components
    	this.add(inputPane);
    	this.add(markPanel);
    	this.add(Box.createRigidArea(new Dimension(0, 10)));
    	this.add(buttonPanel);
	}
	
	/** 
	 * Registers the controllers for text fields and buttons 
	 */
	private void registerControllers() {

	}
  
	/** 
	 * Updates the input fields after adding/removing/generating/resetting 
	 */
	public void update() {
    
  	}
}
