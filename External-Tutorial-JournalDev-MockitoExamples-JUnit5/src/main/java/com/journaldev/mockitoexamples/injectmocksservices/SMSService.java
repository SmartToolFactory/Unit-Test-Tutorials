package com.journaldev.mockitoexamples.injectmocksservices;

public class SMSService implements Service {

	@Override
	public boolean send(String msg) {
		System.out.println("Sending SMS");
		return true;
	}
}
