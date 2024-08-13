package com.example.MockDemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class MathServiceTest {
	@Test
	public void testPerformSubtraction()
	{
		// Let's create a mock obj for Calculator class
		Calculator cal=Mockito.mock(Calculator.class);
		
		//stub the sub method to return a specific value
		Mockito.when(cal.sub(10,5)).thenReturn(5);
		
		// create the instance of MathService with the mock cal object
		MathService ms=new MathService(cal);
		
		//call the performSubtraction method
		int res=ms.performSubtraction(10,5);
		
	
		
		//Assert that the resulr is what we stubbed above
		Assertions.assertEquals(5, res);
		//verify that the sub method was called
		Mockito.verify(cal).sub(10,5);
	}
}
