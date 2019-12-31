
public class Grade {
	private String category;
	private double mark;
	private double weight;
	
	public Grade(String category, double mark, double weight) {
		this.category = category;
		this.mark = mark;
		this.weight = weight;
	}
	
	public void setCategory(String newCategory) {
		this.category = newCategory;
	}
	
	public void setMark(double newMark) {
		this.mark = newMark;
	}
	
	public void setWeight(double newWeight) {
		this.weight = newWeight;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public double getMark() {
		return this.mark;
	}
	
	public double getWeight() {
		return this.weight;
	}
}
