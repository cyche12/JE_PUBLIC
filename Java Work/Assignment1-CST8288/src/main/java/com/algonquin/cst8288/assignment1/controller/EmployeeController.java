package com.algonquin.cst8288.assignment1.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.algonquin.cst8288.assignment1.emoloyee.Employee;
import com.algonquin.cst8288.assignment1.persistence.Formatter;
import com.algonquin.cst8288.assignment1.persistence.JSONFormatter;

/**
 * 
 * Process, validate and save employee data.
 * 
 * 
 */

public class EmployeeController {

	public String processEmployee(Employee employee) throws IOException {

		
		// Process data
		// Calculate bonus, 
		// total compensation, 
		// pension contribution, 
		// renewal date etc.
		
		
		// Validate data
		if (!isValidEmployee(employee)) {
			return "FALIED";
		}
		
		
		// Store data
		this.saveEmployee(employee, "employee_data.txt");
		return "SUCCESS";
	}

	// Validates the Employee object
	private boolean isValidEmployee(Employee employee) {
		if (!isPresent(employee.getName())) {
			return false;
		}
		employee.setName(employee.getName().trim());

		if (!isValidAlphaNumeric(employee.getName())) {
			return false;
		}
		if (employee.getEmail() == null || employee.getEmail().trim().length() == 0) {
			return false;
		}
		employee.setEmail(employee.getEmail().trim());
		if (!isValidEmail(employee.getEmail())) {
			return false;
		}
		
		if (!isNoSalary(employee.getSalary())) {
			return false;
		}
		
		if (!isNoCompensation(employee.getTotalCompensation())) {
			return false;
		}
		
		if (!isNoServiceYear(employee.getNumberOfServiceYear())) {
			return false;
		}
		
		return true;
	}

	/**
	 * Check for null or empty
	 * 
	 * @param value
	 * @return
	 */
	private boolean isPresent(String value) {
		return value != null && value.trim().length() > 0;
	}

	/**
	 * Check for special character
	 * 
	 * @param value
	 * @return
	 */
	private boolean isValidAlphaNumeric(String value) {
		Pattern pattern = Pattern.compile("[^A-Za-z0-9]");
		Matcher matcher = pattern.matcher(value);
		return !matcher.find();
	}

	/**
	 * Check for valid email address
	 * 
	 * @param value
	 * @return
	 */
	private boolean isValidEmail(String value) {
		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher matcher = pattern.matcher(value);
		return matcher.find();
	}
	
	/**
	 * Check for salary
	 * 
	 * @param salary
	 * @return
	 */
	private boolean isNoSalary(double salary) {
		return salary <= 0 ? false : true ;
	}
	
	/**
	 * Check for total compensation
	 * 
	 * @param compensation
	 * @return
	 */
	private boolean isNoCompensation(double compensation) {
		return compensation <= 0 ? false : true ;
	}
	
	/**
	 * Check number of service year.
	 * 
	 * @param serviceYear
	 * @return
	 */
	private boolean isNoServiceYear(int serviceYear) {
		return serviceYear <= 0 ? false : true ;
	}
	

	/**
	 * Write data into file but it violates DIP
	 * 
	 * @param employee
	 * @throws IOException
	 * 
	 * 
	 */
	private void saveEmployee(Employee person, String filename) throws IOException {
		Formatter formatter = new JSONFormatter();
		try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
			writer.println(formatter.format(person));
			writer.flush();
		}
	}

}