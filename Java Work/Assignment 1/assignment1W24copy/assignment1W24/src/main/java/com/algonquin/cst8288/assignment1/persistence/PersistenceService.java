package com.algonquin.cst8288.assignment1.persistence;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import com.algonquin.cst8288.assignment1.employee.Employee;

public class PersistenceService {
	
	
//	private Employee person;
//	private String filename;
	/**
	 * Write data into file but it violates DIP
	 * 
	 * @param employee
	 * @throws IOException
	 * 
	 */
	public void saveEmployee(Employee person, String filename) throws IOException {
		Formatter formatter = new JSONFormatter();
		try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
			writer.println(formatter.format(person));
			writer.flush();
		}
	}
}