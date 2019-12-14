package com.journaldev.mockitoexamples.testng;



import com.journaldev.mockitoexamples.AddService;
import com.journaldev.mockitoexamples.CalcService;

import org.mockito.Mockito;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TestNGMockitoExample {

	@Test(dataProvider = "dp")
	public void test_mock_object(int i, int j) {
		System.out.println("**--- Test testCalc executed ---**");

		AddService addService;
		CalcService calcService;

		addService = Mockito.mock(AddService.class);
		calcService = new CalcService(addService);

		int expected = i + j;
		when(addService.add(i, j)).thenReturn(expected);

		int actual = calcService.calc(i, j);

		assertEquals(expected, actual);
	}

	@DataProvider
	public Object[][] dp() {
		return new Object[][] { new Object[] { 1, 1 }, new Object[] { 2, 2 }, };
	}
}
