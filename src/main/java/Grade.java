/** 
 * Grade Class
 * Stores data related to a grade
 * @author William San
 * @since 01/12/20 
 */ 
public class Grade {
	// Instance variables
	private String assessment; // Name of assessment
	private double mark;       // Mark achieved on assessment (%)
	private double weight;     // Weight of the assessment
	
	/**
	 * Default constructor
	 * @param assessment The name of the assessment
	 * @param mark       The mark on the assessment
	 * @param weight     The weight of the assessment
	 */
	public Grade(String assessment, double mark, double weight) {
		this.assessment = assessment;
		this.mark = mark;
		this.weight = weight;
	}
	
	/** 
	 * Sets the assessment name to the argument provided 
	 * @param newAssessment The assessment name for this grade
	 */
	public void setAssessment(String newAssessment) {
		this.assessment = newAssessment;
	}
	
	/** 
	 * Sets the mark to the argument provided 
	 * @param newMark The user's mark for this grade
	 */
	public void setMark(double newMark) {
		this.mark = newMark;
	}
	
	/** 
	 * Sets the weighting of the assessment to the argument provided 
	 * @param weight The weight of the assessment for this grade
	 */
	public void setWeight(double newWeight) {
		this.weight = newWeight;
	}
	
	/** 
	 * Returns the name of the assessment 
	 * @return The name of the assessment
	 */
	public String getAssessment() {
		return this.assessment;
	}
	
	/** 
	 * Returns the mark on the assessment 
	 * @return The mark on the assessment
	 */
	public double getMark() {
		return this.mark;
	}
	
	/** 
	 * Returns the weight of the assessment 
	 * @return The weight of the assessment
	 */
	public double getWeight() {
		return this.weight;
	}
}
