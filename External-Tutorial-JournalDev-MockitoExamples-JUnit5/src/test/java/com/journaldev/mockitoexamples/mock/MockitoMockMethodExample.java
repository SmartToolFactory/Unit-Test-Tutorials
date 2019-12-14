package com.journaldev.mockitoexamples.mock;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MockitoMockMethodExample {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		//1. using Mockito.mock() method
		List<String> mockList = mock(List.class);
		when(mockList.size()).thenReturn(5);
		assertTrue(mockList.size()==5);
	}
	
}
