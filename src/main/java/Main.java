import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
	public static void main(String[] args) throws Exception {
		boolean done = false;
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		Integer menu; // Determines which operation user wants to perform on main menu
		int change;   // Index of grade that user wants to modify
		String input;
		String name;
		Double mark;
		Double weight;
		double desiredMark = 85;
		String course = "Course";
		String[] headers = {"Category", "Mark", "Weight", "Weighted Mark"};
		ArrayList<Grade> grades = new ArrayList<Grade>();
		
		while (done == false) {
			System.out.println("0: Add another grade");
			System.out.println("1: Generate final mark");
			System.out.println("2: Change course name");
			System.out.println("3: Change desired mark");
			System.out.println("4: Change a mark");
			System.out.println("5: Delete a mark");
			System.out.println("6: Exit");
			System.out.print("Enter an option (0-6): ");
			input = reader.readLine();
			menu = parseInt(input);
			if (menu != null && menu >= 0 && menu <= 6) {
				switch (menu) {
					// Add another grade
					case 0:
						System.out.print("Enter the name of the assessment: ");
						name = reader.readLine();
						
						System.out.print("Enter the mark received (%): ");
						input = reader.readLine();
						mark = parseDouble(input);
						
						System.out.print("Enter the weighting of the assessment (%): ");
						input = reader.readLine();
						weight = parseDouble(input);
						
						if (mark != null && weight != null) {
							grades.add(new Grade(name, roundDecimal(mark), roundDecimal(weight)));
						}
						else {
							System.out.println("Invalid entry.");
						}
						break;
					// Generate final mark
					case 1:
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
							
							row.createCell(0).setCellValue(grade.getCategory());
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
						FileOutputStream file = new FileOutputStream(course + "-grades.xlsx");
						workbook.write(file);
						file.close();
						workbook.close();
						
						for (int i = 0; i < grades.size(); i++) {
							System.out.println("Category: " + grades.get(i).getCategory());
							System.out.println("    Mark: " + grades.get(i).getMark());
							System.out.println("  Weight: " + grades.get(i).getWeight());
						}
						break;
					// Change course name
					case 2:
						System.out.print("Enter the name of the course: ");
						course = reader.readLine();
						break;
					// Change desired mark
					case 3:
						System.out.print("Enter your desired mark: ");
						desiredMark = parseDouble(reader.readLine());
						break;
					// Change an entered mark
					case 4:
						if (grades.size() > 0) {
							for (int i = 0; i < grades.size(); i++) {
								System.out.println(i + ": " + grades.get(i).getCategory() + " = " + grades.get(i).getMark());
							}
							System.out.print("Choose a grade to change (0-" + (grades.size() - 1) + "): ");
							change = parseInt(reader.readLine());
							if (change >= 0 && change < grades.size()) {
								System.out.print("Enter the new mark: ");
								double newMark = parseDouble(reader.readLine());
								grades.get(change).setMark(newMark);				
							}
							else {
								System.out.println("Invalid option.");
							}
						}
						else {
							System.out.println("No grades are currently entered.");
						}
						break;
					// Delete an entered mark
					case 5:
						if (grades.size() > 0) {
							for (int i = 0; i < grades.size(); i++) {
								System.out.println(i + ": " + grades.get(i).getCategory() + " = " + grades.get(i).getMark());
							}
							System.out.print("Choose a grade to delete (0-" + (grades.size() - 1) + "): ");
							change = parseInt(reader.readLine());
							if (change >= 0 && change < grades.size()) {
								grades.remove(change);
							}
							else {
								System.out.println("Invalid option.");
							}
						}
						else {
							System.out.println("No grades are currently entered.");
						}
						break;
					// Exit
					case 6:
						System.out.println("Exiting.");
						done = true;
						break;
						
					default:
						System.out.println("Invalid option.");
						break;
				}
			}
		}
	}
	
	public static Double parseDouble(String text) {
		try {
			return Double.parseDouble(text);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input.");
			return null;
		}
	}
	
	public static Integer parseInt(String text) {
		try {
			return Integer.parseInt(text);
		}
		catch (NumberFormatException e) {
			System.out.println("Invalid input.");
			return null;
		}
	}
	
	public static double roundDecimal(double num) {
		return Math.round(num * 100) / 100.0;
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
	public static boolean getReqMark(int rowNum, Sheet sheet, double currentWeight, double currentMark, double desiredMark) {
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
			
			Row row = sheet.createRow(rowNum++);
			
			row.createCell(0).setCellValue("Final Exam");
			row.createCell(1).setCellValue(requiredMark);
			row.createCell(2).setCellValue(examWeight);
			row.createCell(3).setCellFormula("B" + rowNum + "*C" + rowNum + "/100");
			
			return true;
		}
	}
}
