package com.algonquin.cst8288.assignment1.emoloyee;

import java.util.Date;

/**
 * 
 * EmployeeService interface
 * 
 */

public interface EmployeeService {

	public double calculateTotalCompensation(Employee employee);
	
	public double pensionContribution(Employee employee);
	
	public double calculateBonus(Employee employee);
	
	public Date renewalDate();
	
}
