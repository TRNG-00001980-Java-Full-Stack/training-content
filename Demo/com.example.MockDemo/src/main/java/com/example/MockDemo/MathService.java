package com.example.MockDemo;

public class MathService {
	private Calculator cal;
	public MathService(Calculator cal)
	{
		this.cal=cal;
	}
	public int performSubtraction(int a,int b)
	{
		return cal.sub(a, b);
	}
}
