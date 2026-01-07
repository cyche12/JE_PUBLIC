/**
 *File Name: YourPurchasesTest.java
 *Author@Jake Elliott
 *Course : CST8284
 *Assignment : Lab 04
 *Section : 302
 *Date : 10/4/23
 *Professor : Islam Gomaa
 *Student : Jake Elliott
 *Purpose : This class contains test cases for all getters, methods and functions of the class YourPuchases.
 *@see package lab4
 */

package lab4;

import org.junit.Assert;
import org.junit.Test;

	/**
	 * Testing class for the YourPuchases class
	 */
public class YourPurchasesTest {
	
	/** static final double EPSILON for testing purposes */
	private static final double EPSILON = 1E-12;
	
	/** Test case 1 for CalculateChange method */
	@Test
	public void testCalculateChange() {
		YourPurchases aPurchase = new YourPurchases();
		aPurchase.recordPurchase(1.5);
		aPurchase.receivePayment(5, 0, 0, 0, 0); //Five dollars of payment.
		double changeResult = aPurchase.CalculateChange();
		double expected = 3.50; //Expecting 3.5 dollars of change.
	    Assert.assertEquals(expected, changeResult, EPSILON);
	}
	
	/** Test case 2 for CalculateChange method */
	@Test
	public void testCalculateChange1() {
		YourPurchases aPurchase = new YourPurchases();
		aPurchase.recordPurchase(1.5);
		aPurchase.receivePayment(5, 0, 0, 0, 0); //Five dollars of payment.
		double changeResult = aPurchase.giveChange();
		double expected = 3.50; //Expecting 3.5 dollars of change.
	    Assert.assertEquals(expected, changeResult, EPSILON);
	}
	
	/** Test case 1 for getPurchase getter */
	@Test
	public void testgetPurchase() {
		YourPurchases aPurchase = new YourPurchases();
		aPurchase.recordPurchase(1); //Get purchase set to 1.
		double purchaseResult = aPurchase.getPurchase();
		double expected = 0; //Expecting 0.
	    Assert.assertEquals(expected, purchaseResult, EPSILON);
	}
	
	/** Test case 2 for getPurchase getter */
	@Test
	public void testgetPurchase1() {
		YourPurchases aPurchase = new YourPurchases();
		aPurchase.recordPurchase(0); //Get purchase set to 0.
		double purchaseResult = aPurchase.getPurchase();
		double expected = 0; //Expecting 0.
	    Assert.assertEquals(expected, purchaseResult, EPSILON);
	}
	
	/** Test case 1 for getPayment getter */
	@Test
	public void testgetPayment() {
		YourPurchases aPayment = new YourPurchases();
		aPayment.receivePayment(1, 0, 0, 0, 0); //Receive payment set to 1 dollar.//
		double paymentResult = aPayment.getPayment();
		double expected = 0; //Expecting 0.
	    Assert.assertEquals(expected, paymentResult, EPSILON);
	}
	
	/** Test case 2 for getPayment getter */
	@Test
	public void testgetPayment1() {
		YourPurchases aPayment = new YourPurchases();
		aPayment.receivePayment(0, 0, 0, 0, 0); //Receive payment set to 0.//
		double paymentResult = aPayment.getPayment();
		double expected = 0; //Expecting 0.//
	    Assert.assertEquals(expected, paymentResult, EPSILON);
	}
	
	/** Test case 1 for giveChange method */
	@Test
	public void testGiveChange() {
		YourPurchases agChange = new YourPurchases(); 
		agChange.recordPurchase(5); //Give change set to 5.//
		double changeResult = agChange.giveChange();
		double expected = 0; //Expecting 0.//
	    Assert.assertEquals(expected, changeResult, EPSILON);
	}
	
	/** Test case 2 for giveChange method */
	@Test
	public void testGiveChange1() {
		YourPurchases agChange = new YourPurchases();
		agChange.recordPurchase(0); //Give change set to 0.//
		double changeResult = agChange.giveChange();
		double expected = 0; //Expecting 0.
	    Assert.assertEquals(expected, changeResult, EPSILON);
	}
	
	/** Test case 1 for RecordPurchase function */
	@Test
	public void testRecordPurchase() {
		YourPurchases aRecordPurchase = new YourPurchases();
		aRecordPurchase.recordPurchase(1); //Purchase set to 1//
		double rPurchaseResults = aRecordPurchase.getPurchase();
		double expected = 0; //Expecting 0.//
	    Assert.assertEquals(expected, rPurchaseResults, EPSILON);
	}
	
	/** Test case 2 for RecordPurchase function */
	@Test
	public void testRecordPurchase1() {
		YourPurchases aRecordPurchase = new YourPurchases();
		aRecordPurchase.recordPurchase(0); //Purchase set to 0//
		double rPurchaseResults = aRecordPurchase.getPurchase();
		double expected = 0; //Expecting 0.//
	    Assert.assertEquals(expected, rPurchaseResults, EPSILON);
	}
	
	/** Test case 1 for ReceivePayment function */
	@Test
	public void testReceivePayment() {
		YourPurchases aReceivePayment = new YourPurchases();
		aReceivePayment.receivePayment(5, 0, 0, 0, 0); //Payment received set to 5 dollars.
		double rPaymentResults = aReceivePayment.getPayment();
		double expected = 0; //Expecting 0.
	    Assert.assertEquals(expected, rPaymentResults, EPSILON);
	}
	
	/** Test case 2 for ReceivePayment function */
	@Test
	public void testReceivePayment1() {
		YourPurchases aReceivePayment = new YourPurchases();
		aReceivePayment.receivePayment(0, 0, 0, 0, 0); //Payment received set to 0bu dollars.
		double rPaymentResults = aReceivePayment.getPayment();
		double expected = 0; //Expecting 0.
	    Assert.assertEquals(expected, rPaymentResults, EPSILON);
	}
}
	