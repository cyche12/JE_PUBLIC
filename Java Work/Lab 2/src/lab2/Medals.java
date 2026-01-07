/**
 *File Name: Medals.java
 *Author@Islam Gomaa
 *Course : CST8284
 *Assignment : Lab 02
 *Date : 9/26/23
 *Professor : Islam Gomaa
 *Purpose : This program prints a table of medal winner counts with row totals.
 *@see package lab2 
 */

/**
 * Package Declaration
 */
package lab2;

/**
 * Public Class Medals
 */
public class Medals
{
   public static void main(String[] args)
   {
	   
/**Private class level declarations
 * final int COUNTRIES preset to 8
 * final int MEDALS preset to 3
*/
      final int COUNTRIES = 8;
      final int MEDALS = 3;

/**
 * String array named "countries"
 */
      String[] countries =
         {
            "Canada",
            "Italy",
            "Germany",
            "Japan",
            "Kazakhstan",
            "Russia",
            "South Korea",
            "United States"
         };

/**
 * Multidimensional int array named "counts"
 */
      
      int[][] counts =
         {
            { 0, 3, 0 },
            { 0, 0, 1 },
            { 0, 0, 1 },
            { 1, 0, 0 },
            { 0, 0, 1 },
            { 3, 1, 1 },
            { 0, 1, 0 },
            { 1, 0, 1 }
         };
      
      System.out.println("        Country    Gold  Silver  Bronze   Total");
      

         // Process the ith row
    	  //TO DO: Use printf() to print country name as a field of 15 bytes as a String value:
//         System.out.printf();
     
/**
 * for loop to print each row element
 */
         // Print each row element and update the row total
      for (int i = 0; i < COUNTRIES; i++) {
    	    System.out.printf("%15s", countries[i]);

    	    for (int j = 0; j < MEDALS; j++) {
    	        System.out.printf("%8d", counts[i][j]);
    	    }

    	    int total = 0; // Calculate the total count for the current country
    	    for (int m = 0; m < MEDALS; m++) {
    	        total += counts[i][m];
    	    }
    	    System.out.printf("%8d", total); // Print the total count for the current country
    	    System.out.println(" ");
    	}
      

            //To DO: calculate the total for each row
           /**
            * Calculation function for variable "total"
            */
         // Display the row total and print a new line

       System.out.printf("%15s", "Total");
       
       for (int k = 0; k< MEDALS; k++ ) {
       int columnTotal = 0;        
       for (int m = 0; m < COUNTRIES; m++){
    	   columnTotal = columnTotal + counts[m][k];
       }
       System.out.printf("%8d", columnTotal);
   } 
    
   }

      
      //Extra - To DO: define an array of size 3 to hold the total for each column. 
      //               Modify the above code to keep total for each medal type, and then use a for loop to print total line below
   }


