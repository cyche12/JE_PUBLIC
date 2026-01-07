package lab_5;
import java.util.Scanner;
/**
This code in this file is for the Consultant Class. A Consultant is a miller who is paid for every hour worked.
*/
// TO DO: TASK 1 - YOUR CODE GOES HERE TO DECLARE THE CONSULTANT CLASS AND ITS VARIABLES.
public class Consultants extends Millers{

	double consultantWage;
	double hoursWorked;
	String name;
	
	/**
   TO DO: TASK 2 - This portion of your code constructs an hourly paid consultant with a given name and biweekly wage.
   @param name the name of this consultant
   @param wage the consultant wage per hour 
*/
// YOUR CODE FOR YOUR CONSULTANT GOES HERE!!
	Scanner input = new Scanner(System.in);
	
	public Consultants() {
		System.out.println("Enter your name");
		name = input.next();
		System.out.println("Enter your hours worked");
		hoursWorked = input.nextDouble();
	}
	public String showConsultant() {
		System.out.println();
	}
	

// TO DO: TASK 3 - THIS PORTION OF YOUR CODE COMPUTES THE 

	public double biWeeklyPay(double hoursWorked) {
		
		double pay = hoursWorked * consultantWage;

// In this part of your code, an overtime amount is added if the Consultant works for more than 76 hours bi-weekly.
// Review this portion of code carefully to include the code to fully satisfy this condition.
  
// SOME CODE GOES HERE BEFORE THE CODE BELOW:
		if(hoursWorked > 76) {
		
      pay = (pay + ((hoursWorked - 76) * 0.5) * consultantWage);
   
      return pay;
      
		}
		
		else {
		return pay;
}
}
}

