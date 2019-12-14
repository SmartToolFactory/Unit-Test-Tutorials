package com.smarttoolfactory.tutorial2_1mockito;


import com.smarttoolfactory.tutorial2_1mockito.model_void_method.Employee;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class TestVoidMethod {


    @Test
    public void testSetName_Name_NotNull() {
        Employee employee = mock(Employee.class);

        final String USER_NAME = "Test User";

        // 🔥 Alternative 1
//        doAnswer(new Answer() {
//            @Override
//            public String answer(InvocationOnMock invocation) {
//
//                System.out.println("Employee setName Argument = " + invocation.getArgument(0));
//                return null;
//            }
//        }).when(employee).setName(anyString());

        // 🔥 Alternative 2 With Lambda
        doAnswer((i) -> {
            System.out.println("Employee setName Argument = " + i.getArgument(0));

            return null;
        }).when(employee).setName(anyString());

        doThrow(new RuntimeException()).when(employee).setName(null);
        when(employee.getName()).thenReturn(USER_NAME);

        employee.setName(USER_NAME);

        assertEquals(USER_NAME, employee.getName());
        verify(employee, times(1)).setName(USER_NAME);


    }

    @Test
    public void testSetName_With_doNothing() {

        final String USER_NAME = "Test User";

        // Arrange
        Employee employee = mock(Employee.class);

        doNothing().when(employee).setName(anyString());

        // Act
        employee.setName(USER_NAME);

        verify(employee, times(1)).setName(USER_NAME);
    }

    @Test
    public void testSetName_With_doNothing_ArgumentCaptor() {

        final String USER_NAME = "Test User";

        // Arrange
        Employee employee = mock(Employee.class);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        doNothing().when(employee).setName(argumentCaptor.capture());


        // Act
        employee.setName(USER_NAME);

        // 🔥🔥 argumentCaptor MUST be called after mock method is called
        String actual = argumentCaptor.getValue();


        // Assert
        assertEquals(USER_NAME, actual);


    }

    @Test
    public void testSetName_Wİth_doCallRealMethod() {

        final String USER_NAME = "Test User";

        // Arrange
        Employee employee = mock(Employee.class);

        doCallRealMethod().when(employee).setName(USER_NAME);

        // Act
        employee.setName(USER_NAME);

        verify(employee, times(1)).setName(USER_NAME);

    }

    @Test(expected = RuntimeException.class)
    public void testSetName_NameNull() {
        Employee employee = mock(Employee.class);

        // Arrange
        doThrow(new RuntimeException("User is null")).when(employee).setName(null);

        // Act
        employee.setName(null);

        // Assert
        assertNull(employee.getName());
        verify(employee).setName(null);

    }

}
