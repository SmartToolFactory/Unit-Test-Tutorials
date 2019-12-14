package com.journaldev.mockitoexamples;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockSingleElementTest {

	@Test
	public void testMethod() {
		ArrayList<String> mockedList = mock(ArrayList.class);

		when(mockedList.get(0)).thenReturn("first-element");

		System.out.println(mockedList.get(0));
		assertEquals("first-element", mockedList.get(0));
		
		// "null" gets printed as get(1) is not stubbed
		System.out.println(mockedList.get(1));
	}

}
