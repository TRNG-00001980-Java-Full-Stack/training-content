package com.example.MockDemo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserManagementTest {
	
	@Test
	public void testRegisterUser()
	{
		// create a mock object for EmailService
		EmailService em=Mockito.mock(EmailService.class);
		
		// create an instance of usermanager with the mock object
		UserManager usm=new UserManager(em);
		
		//call the registerusermethod
		usm.registerUser("sample@eg.com");
		
		//verify that the sendEmail method of emailservice was called with the arguments
		
		Mockito.verify(em).sendEmail("sample@eg.com", "Welcome", "Thank you for registering");
	}
}
