package com.algonquin.cst8288.assignment1.persistence;

import java.io.IOException;

import com.algonquin.cst8288.assignment1.employee.Employee;

public interface Formatter {
	
	public String format(Employee employee) throws IOException;
	
}

