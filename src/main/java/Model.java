import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/** 
 * Model Class
 * The model for the grade calculator
 * @author William San
 * @since 01/12/20 
 */ 
public class Model {
	private View view; // The view for the calculator
	
	private double desiredMark = 85;  // The user's desired final mark
	private double examMark;          // The required mark that the user needs on the exam 
	private String course = "Course"; // The name of the course
	
	private ArrayList<Grade> grades = new ArrayList<Grade>(); // All grades entered
	
	private String[] headers = {"Assessment", "Mark", "Weight", "Weighted Mark"};
	
	/** 
	 * Returns the final exam mark needed
	 * @return The final exam mark to receive desired mark
	 */
	public double getExamMark() {
		return this.examMark;
	}
	
	/** 
	 * Sets view for the calculations
	 * @param view The GUI view 
	 */
	public void setView(View view) {
		this.view = view;
	}
	
	/**
	 * Removes all grades currently stored in the array
	 */
	public void resetGrades() {
		this.grades = new ArrayList<Grade>();
	}
	
	/** 
	 * Sets the course name to the argument provided 
	 * @param course The name of the course
	 */
	public void setCourse(String course) {
		this.course = course;
	}
	
	/** 
	 * Adds a grade according to the arguments provided 
	 * @param assessment The name of the assessment
	 * @param mark       The mark received on that assessment
	 * @param weight     The weight of that assessment 
	 */
	public void setGrade(String assessment, double mark, double weight) {
		grades.add(new Grade(assessment, roundDecimal(mark), roundDecimal(weight)));
	}
	
	/** 
	 * Sets the desired mark to the argument provided 
	 * @param desiredMark The user's desiredMark
	 */
	public void setDesiredMark(double desiredMark) {
		this.desiredMark = desiredMark;
	}

	/** 
	 * Returns a number rounded to 2 decimal places 
	 * @param num The number to convert to 2 decimal places
	 * @return The number with 2 decimal places
	 */
	public static double roundDecimal(double num) {
		return Math.round(num * 100) / 100.0;
	}
	
	public void generateMark() {
		Workbook workbook = new XSSFWorkbook();
		//CreationHelper createHelper = workbook.getCreationHelper();
		Sheet sheet = workbook.createSheet(course); // TODO: Allow user to select course name
		
		// Header information
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 12);
		headerFont.setColor(IndexedColors.BLACK.getIndex());
		
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);
		
		Row headerRow = sheet.createRow(0);
		
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(headers[i]);
			cell.setCellStyle(headerCellStyle);
		}
		
		// Fill in data
		int rowNum = 1;
		double currentWeight = 0;
		double currentMark = 0;
		for (Grade grade: grades) {
			Row row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue(grade.getAssessment());
			row.createCell(1).setCellValue(grade.getMark());
			row.createCell(2).setCellValue(grade.getWeight());
			row.createCell(3).setCellFormula("B" + rowNum + "*C" + rowNum + "/100");
			
			currentMark += grade.getMark() * grade.getWeight() / 100;
			currentWeight += grade.getWeight();
		}
		
		// Calculate the required exam mark to get desired mark
		if (getReqMark(rowNum, sheet, currentWeight, currentMark, desiredMark)) {
			rowNum++;
		}
		
		// Enter final mark of the course
		Row row = sheet.createRow(rowNum++);						
		row.createCell(0).setCellValue("Final Mark");
		row.createCell(3).setCellFormula("SUM(D2:D" + (rowNum - 1) + ")");
		
		// Resize columns
		for (int i = 0; i < headers.length; i++) {
			sheet.autoSizeColumn(i);
		}
		
		// Write to an excel file
		FileOutputStream file;
		try {
			file = new FileOutputStream(course + "-grades.xlsx");
			workbook.write(file);
			file.close();
			workbook.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < grades.size(); i++) {
			System.out.println("Assessment: " + grades.get(i).getAssessment());
			System.out.println("      Mark: " + grades.get(i).getMark());
			System.out.println("    Weight: " + grades.get(i).getWeight());
		}
	}
	
	/**
	 * Calculates the required exam mark needed to end a course with desired mark.
	 * @param rowNum        The current row being used in the excel file
	 * @param sheet         The sheet for the excel file
	 * @param currentWeight The total weight of all previous assessments
	 * @param currentMark   The total weighted mark of all previous assessments
	 * @param desiredMark   The final mark that the user wants to achieve
	 * @return              True if an exam mark was successfully generated
	 */
	public boolean getReqMark(int rowNum, Sheet sheet, double currentWeight, double currentMark, double desiredMark) {
		double examWeight = 100 - currentWeight;
		if (examWeight == 0) {
			return false;
		}
		else if (examWeight < 0) {
			System.out.println("Invalid weightings.");
			return false;
		}
		else {
			double requiredMark = roundDecimal((desiredMark - currentMark) / examWeight * 100);
			this.examMark = requiredMark;
			
			Row row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue("Final Exam");
			row.createCell(1).setCellValue(requiredMark);
			row.createCell(2).setCellValue(examWeight);
			row.createCell(3).setCellFormula("B" + rowNum + "*C" + rowNum + "/100");
			
			return true;
		}
	}
}
