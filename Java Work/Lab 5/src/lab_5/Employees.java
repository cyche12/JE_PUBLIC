package lab_5;
import java.util.Scanner;

/**
This class represents the employee. An employee is paid the same (particular) amount regardless of the hours (additional) the employee worked.
*/
/**
TO DO: TASK 1 - DECLARE YOUR CLASS AND ANY VARIABLES HERE.
*/
	public class Employees extends Millers {
		
		String name;
		double pay;
		/**
   Constructs an employee with a given name and an annual pay.
   @param name the name of this employee
   @param pay the annual pay of the employee
 */

		Scanner input = new Scanner(System.in);

		@override
		public Millers(String name, double Pay) {
			this.name = input.next();
			this.annualPay = pay;
			
			return (name + pay);
}
		

// TO DO: TASK - This portion of your code computes the pay for this category of miller.

// YOUR CODE GOES IN HERE.
		public double biWeeklyPay(double employeeWage)	{
		   
		return employeeWage;
		}
}

