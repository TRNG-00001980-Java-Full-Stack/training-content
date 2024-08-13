package com.example.MockDemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

public class PaymentProcessorTest {
	@Test
	public void testProcessorPayment()
	{
		//create a spy for the paymentProcessor Class
		PaymentProcessor payPro=spy(new PaymentProcessor());
		
		// stub the logTransaction method
		doReturn(null).when(payPro).processPayment(100);
		
		//call the processPayment method
		String res=payPro.processPayment(100);
		//Assert the expected result
		assertEquals("Processed Payement of $100", res);
		
		//verify that logTranscation was called with the expected argument
		
		verify(payPro).logTransaction(100);
	}
}
