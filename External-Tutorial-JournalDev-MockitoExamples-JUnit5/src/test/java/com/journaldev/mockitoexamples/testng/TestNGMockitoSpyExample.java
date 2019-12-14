package com.journaldev.mockitoexamples.testng;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

public class TestNGMockitoSpyExample {

	@Test
	public void test_mockito_spy() {
		List<String> list = new ArrayList<>();
		List<String> listSpy = spy(list);

		listSpy.add("first-element");
		System.out.println(listSpy.get(0));

		assertEquals("first-element", listSpy.get(0));
		when(listSpy.get(0)).thenReturn("second-element");

		System.out.println(listSpy.get(0));
		assertEquals("second-element", listSpy.get(0));

		// call the real method on Spied object since it's not stubbed
		assertEquals(1, listSpy.size());

	}
}
