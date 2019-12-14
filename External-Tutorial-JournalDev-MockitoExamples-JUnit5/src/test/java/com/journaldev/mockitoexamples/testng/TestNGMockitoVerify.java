package com.journaldev.mockitoexamples.testng;

import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.testng.annotations.Test;

import java.util.List;

import static org.mockito.Mockito.verify;

public class TestNGMockitoVerify {
	@Test
	public void test_mockito_verify() {

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
