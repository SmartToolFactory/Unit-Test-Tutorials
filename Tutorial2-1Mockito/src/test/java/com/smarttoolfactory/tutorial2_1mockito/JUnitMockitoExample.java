package com.smarttoolfactory.tutorial2_1mockito;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class JUnitMockitoExample {
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Test
	public void verifyInteractions() {		
		Set mockSet = mock(Set.class);
		Set<String> toAdd = new HashSet<String>();
		
		mockSet.addAll(toAdd);
		mockSet.clear();
		
		verify(mockSet).addAll(toAdd);
		verify(mockSet).clear();
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void stubMethodCalls() {
		Set mockSet = mock(Set.class);
		when(mockSet.size()).thenReturn(10);
		Assert.assertEquals(10, mockSet.size());
	}
	
	@Test
	public void testSpy() {
		List<String> list = new LinkedList<String>();
		List<String> spy = spy(list);

		try {
			when(spy.get(0)).thenReturn("foo");
		} catch(IndexOutOfBoundsException e) {
			// Expected
		}
		
		doReturn("foo").when(spy).get(0);
	}

}
