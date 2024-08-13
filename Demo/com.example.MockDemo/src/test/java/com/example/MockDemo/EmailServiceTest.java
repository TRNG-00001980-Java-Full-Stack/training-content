package com.example.MockDemo;

import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;

import org.junit.jupiter.api.Test;

public class EmailServiceTest {

	@Test
	public void testSendEmail()
	{
		// create a mock object for EmailService
		
		EmailService ems=mock(EmailService.class,withSettings().defaultAnswer(CALLS_REAL_METHODS));
		
		// call the sendEmail method on the mock object
		
		ems.sendEmail("test@example.com","sample mail", "Hello this is sample mail");
		
		//verify that the sendEmail method was called with the expected arguments
		
		verify(ems).sendEmail("test@example.com","sample mail", "Hello this is sample mail");
		
		
	}
	
}
