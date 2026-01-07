package com.algonquin.cst8288.assignment1.employee;

import java.util.Date;

	
public class PermanentEmployeeImpl implements EmployeeService {
	
	
	Date currentDate = new Date();
	
	@Override
	public double calculateTotalCompensation(Employee employee) {
		return (employee.getSalary() + employee.getBonus());
	}
	
	@Override
	public double pensionContribution(Employee employee) {
		return (0.01 * employee.getSalary());
	}
	
	@Override
	public double calculateBonus(Employee employee) {
		int yearsOfService = calculateYearsOfService(employee.getJoiningDate());
        return 0.25 * yearsOfService * employee.getSalary();
	}
	
	@Override
	public Date renewalDate(Employee employee) {
		return (currentDate - employee.getJoinDate())
				
	}
	
}
