package com.smarttoolfactory.tutorial2_1mockito;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.calls;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class MockitoVerifyExamples {

    @Test
    public void test() {
        List<String> mockList = mock(List.class);
        mockList.add("Pankaj");
        mockList.size();

        verify(mockList).add("Pankaj");
        verify(mockList).add(anyString());
        verify(mockList).add(any(String.class));
        verify(mockList).add(ArgumentMatchers.any(String.class));

        verify(mockList, times(1)).size();
        verify(mockList, atLeastOnce()).size();
        verify(mockList, atMost(2)).size();
        verify(mockList, atLeast(1)).size();
        verify(mockList, never()).clear();

        // all interactions are verified, so below will pass
        verifyNoMoreInteractions(mockList);

        mockList.isEmpty();
        // isEmpty() is not verified, so below will fail
        // verifyNoMoreInteractions(mockList);
        Map mockMap = mock(Map.class);
        Set mockSet = mock(Set.class);
        verify(mockList).isEmpty();
        verifyZeroInteractions(mockList, mockMap, mockSet);

        mockMap.isEmpty();
        verify(mockMap, only()).isEmpty();

        // testing order of mock method calls
        // can skip methods but order should be followed
        InOrder inOrder = inOrder(mockList, mockMap);
        inOrder.verify(mockList).add("Pankaj");
        inOrder.verify(mockList, calls(1)).size();
        inOrder.verify(mockList).isEmpty();
        inOrder.verify(mockMap).isEmpty();

    }
}
