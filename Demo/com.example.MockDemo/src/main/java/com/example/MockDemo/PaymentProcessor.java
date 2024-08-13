package com.example.MockDemo;

public class PaymentProcessor {
	public String processPayment(int amount) {
		logTransaction(amount);
		return "Processed payment of $"+amount;
	}
	void logTransaction(int amount) {
		System.out.println("Logged transaction of $"+amount);
	}
	
}
