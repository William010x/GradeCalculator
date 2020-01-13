import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/** 
 * View Class
 * The GUI view for the grade calculator
 * @author William San
 * @since 01/12/20 
 */ 
public class View extends JPanel {
	// Initialize variables
	private static final int DEFAULT_ENTRIES = 4; // Default number of entries when starting
	public static final int ASSESSMENT_MODE = 1; // Mode for getting assessment component from an entry
	public static final int GRADE_MODE = 3;      // Mode for getting grade component from an entry
	public static final int WEIGHT_MODE = 5;     // Mode for getting weight component from an entry
	
	Model model;
	
	JButton add;       // Add button
	JButton calculate; // Calculate button
	JButton reset;     // Reset button
	
	static JFrame error = new JFrame(); // Frame for displaying errorPane
	static JOptionPane errorPane;       // Pane showing error message
	JPanel namePanel;                   // Panel where user can enter name of course
	JPanel input;                       // Panel containing all input fields
	JScrollPane inputPane;              // Scroll pane containing input panel
	JPanel addPanel;                    // Panel containing add button
	JPanel markPanel;                   // Panel containing final mark calculations
	JPanel desired;                     // Panel containing desired mark
	JPanel exam;                        // Panel containing final exam mark required
	JPanel buttonPanel;                 // Panel containing the buttons

	JTextField courseText;  // Text box for name of course
	JTextField desiredText; // Text box for desired mark input
	JTextField examText;    // Text box for final exam mark required
	
	ArrayList<JPanel> entries = new ArrayList<JPanel>(); // List of all components of entries to enter grades
  
	/** 
	 * Constructor
	 * @param model Model containing all data for grades 
	 */
	public View(Model model) {
		super();
		this.model = model;
		this.model.setView(this);
		this.layoutView();
		this.registerControllers();
		this.update();
	}
	
	/** 
	 * Creates a new input field with text boxes and a remove button
	 * @return The panel containing all input field components 
	 */
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
	
	/** 
	 * Get the text field for an entry
	 * @param index The index of the entry
	 * @param mode  The type of component to return (assessment, grade or weight)
	 * @return The corresponding JTextField for an entry
	 */
	public JTextField getEntryField(int index, int mode) {
		return (JTextField)(this.entries.get(index).getComponent(mode));
	}
  
	/** Lays out the components on the JPanel */
	private void layoutView() {
		// Initializing components
		for (int i = 0; i < DEFAULT_ENTRIES; i++) {
			entries.add(createEntry());
		}
		
		namePanel = new JPanel();
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

		courseText = new JTextField(15);
		desiredText = new JTextField(7);
		examText = new JTextField(7);
		desiredText.setText("85");
    	desiredText.setHorizontalAlignment(JTextField.CENTER);
    	examText.setHorizontalAlignment(JTextField.CENTER);
    	examText.setEditable(false);

		JLabel courseLabel = new JLabel("Course name: ");
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
    	namePanel.setMaximumSize(new Dimension(300, 600));
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
    	namePanel.add(courseLabel);
    	namePanel.add(courseText);
    	
    	input.add(Box.createRigidArea(new Dimension(0, 10)));
    	for (int i = 0; i < entries.size(); i++) {
    		input.add(entries.get(i));
    		
    		JButton remove = (JButton)(this.entries.get(i).getComponent(7));
    		for (ActionListener oldController: remove.getActionListeners()) {
    			remove.removeActionListener(oldController);
    		}
    		remove.addActionListener(new RemoveController(this, i));
    		
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
    	this.add(namePanel);
    	this.add(inputPane);
    	this.add(markPanel);
    	this.add(Box.createRigidArea(new Dimension(0, 10)));
    	this.add(buttonPanel);
	}
	
	/** 
	 * Registers the controllers for text fields and buttons 
	 */
	private void registerControllers() {
		AddController addController = new AddController(this);
		ResetController resetController = new ResetController(this);
		CalculateController calculateController = new CalculateController(this, this.model);
	    
	    this.add.addActionListener(addController);
	    this.reset.addActionListener(resetController);
	    this.calculate.addActionListener(calculateController);
	}
	
	/** 
	 * Adds a new input field to the view
	 */
	public void addEntry() {
		this.entries.add(createEntry());
		this.update();
	}
	
	/** 
	 * Removes an input field from the view
	 */
	public void removeEntry(int index) {
		this.entries.remove(index);
		this.update();
	}
	
	/** 
	 * Adds a new input field to the view
	 */
	public void reset() {
		for (int i = 0; i < this.entries.size(); i++) {
			JTextField assessment = this.getEntryField(i, ASSESSMENT_MODE);
			JTextField grade = this.getEntryField(i, WEIGHT_MODE);
			JTextField weight = this.getEntryField(i, GRADE_MODE);
			
			assessment.setText("");
			grade.setText("");
			weight.setText("");
		}
		this.courseText.setText("");
		this.desiredText.setText("85");
		this.examText.setText("");
		this.update();
	}
	
	/** 
	 * Updates the input fields after adding/removing/generating/resetting 
	 */
	public void update() {
		this.input.removeAll();
		
		this.input.add(Box.createRigidArea(new Dimension(0, 10)));
    	for (int i = 0; i < this.entries.size(); i++) {
    		this.input.add(entries.get(i));
    		
    		JButton remove = (JButton)(this.entries.get(i).getComponent(7));
    		for (ActionListener oldController: remove.getActionListeners()) {
    			remove.removeActionListener(oldController);
    		}
    		remove.addActionListener(new RemoveController(this, i));
    		
    		this.input.add(Box.createRigidArea(new Dimension(0, 10)));
    	}
    	this.input.add(Box.createRigidArea(new Dimension(0, 10)));
    	this.addPanel.add(add);
    	this.input.add(addPanel);
    	
    	this.inputPane.setViewportView(input);
  	}
}
