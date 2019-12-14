package com.journaldev.mockitoexamples;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import java.util.List;

import static org.mockito.Mockito.verify;

public class VerifyInteractionTest {
	@Test
	public void testMethod() {
		@SuppressWarnings("unchecked")
		List<String> mockedList = Mockito.mock(List.class);

		mockedList.add("first-element");
		mockedList.add("second-element");
		mockedList.add("third-element");
		mockedList.add("third-element");
		mockedList.clear();

		verify(mockedList).add("first-element");
		verify(mockedList).add("second-element");
		verify(mockedList, VerificationModeFactory.times(2)).add("third-element");

		verify(mockedList).clear();
	}

}
