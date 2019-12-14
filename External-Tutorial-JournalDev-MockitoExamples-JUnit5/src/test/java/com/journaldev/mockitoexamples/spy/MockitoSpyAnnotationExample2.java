package com.journaldev.mockitoexamples.spy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MockitoSpyAnnotationExample2 {

	@Spy 
	List<String> spyOnList = new ArrayList<>();
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void test() {
		spyOnList.add("A");
		assertEquals(1, spyOnList.size());
		assertEquals("A", spyOnList.get(0));
		
		spyOnList.add("E");
		assertEquals(2, spyOnList.size());
		assertEquals("E", spyOnList.get(1));

		when(spyOnList.size()).thenReturn(10);
		assertEquals(10, spyOnList.size());
	}
}
