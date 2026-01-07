package com.algonquin.cst8288.assignment1.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.algonquin.cst8288.assignment1.employee.Employee;
import com.algonquin.cst8288.assignment1.persistence.Formatter;
import com.algonquin.cst8288.assignment1.persistence.JSONFormatter;
import com.algonquin.cst8288.assignment1.persistence.PersistenceService;

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
		EmployeeInfoValidator employeeinfovalidator = new EmployeeInfoValidator();
		PersistenceService persistenceservice = new PersistenceService();
		
		
		// Validate data
		if (!employeeinfovalidator.isValidEmployee(employee)) {
			return "FALIED";
		}
		
		
		// Store data
		persistenceservice.saveEmployee(employee, "employee_data.txt");
		return "SUCCESS";
	}


	}

