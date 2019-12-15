package com.smarttoolfactory.tutorial2_2mockito_bdd;


import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;

public class Test3OrderedVerification2VoidMethods {


    @Test
    public void testOrderWithBDD() {

        // Given
        ServiceClassA firstMock = mock(ServiceClassA.class);
        ServiceClassB secondMock = mock(ServiceClassB.class);

        //create inOrder object passing any mocks that need to be verified in order
        InOrder inOrder = inOrder(firstMock, secondMock);

        willDoNothing().given(firstMock).methodOne();
        willDoNothing().given(secondMock).methodTwo();

        // When
        // 🔥 void methods
        firstMock.methodOne();
        secondMock.methodTwo();

        // Then
        then(firstMock).should(inOrder).methodOne();
        then(secondMock).should(inOrder).methodTwo();

    }


    public class ServiceClassA {
        void methodOne() {
        }
    }

    public class ServiceClassB {
        void methodTwo() {
        }
    }
}
