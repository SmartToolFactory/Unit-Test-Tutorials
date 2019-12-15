package com.smarttoolfactory.tutorial2_1mockito;

import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;


public class TestArgumentCaptor {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Captor
    private ArgumentCaptor<List<String>> captor;


    @Test
    public final void shouldContainCertainListItem() {

        List<String> asList = Arrays.asList("someElement_test", "someElement");
        final List<String> mockedList = mock(List.class);

        mockedList.addAll(asList);

        // 🔥 Without this test FAILS
        verify(mockedList).addAll(captor.capture());

        final List<String> capturedArgument = captor.getValue();
        assertThat(capturedArgument, hasItem("someElement"));
    }
}