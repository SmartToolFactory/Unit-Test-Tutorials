package com.smarttoolfactory.tutorial2_1mockito;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.AbstractList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class TestVoidMethod2 {


    /**
     * <li>1- Simple Mocking and Verifying</li>
     */

    @Test
    public void whenAddCalledVerified() {
        MyList myList = mock(MyList.class);

        doNothing().when(myList).add(isA(Integer.class), isA(String.class));

        myList.add(0, "");

        verify(myList, times(1)).add(0, "");
    }

    /*
        However, doNothing() is Mockito's default behavior for void methods.

     */
    @Test
    public void whenAddCalledVerified2() {
        MyList myList = mock(MyList.class);
        myList.add(0, "");

        verify(myList, times(1)).add(0, "");
    }

    @Test(expected = Exception.class)
    public void givenNull_AddThrows() {
        MyList myList = mock(MyList.class);
        doThrow().when(myList).add(isA(Integer.class), isNull());

        myList.add(0, null);
    }

    /**
     *  <li>2- Argument Capture</li>
     * In the example above verify() is used to check the arguments passed to add().
     * However, we may need to capture the arguments and do something more with them.
     * In these cases, we use doNothing() just as we did above, but with an ArgumentCaptor:
     */

    @Test
    public void whenAddCalledValueCaptured() {

        MyList myList = mock(MyList.class);

        ArgumentCaptor valueCapture = ArgumentCaptor.forClass(String.class);
        doNothing().when(myList).add(any(Integer.class), (String) valueCapture.capture());
        myList.add(0, "captured");

        assertEquals("captured", valueCapture.getValue());
    }


    /**
     * <li>3- Answering a Call to Void</li>
     * A method may perform more complex behavior than merely adding or setting value.
     * For these situations we can use Mockito’s Answer to add the behavior we need:
     */
    @Test
    public void whenAddCalledAnswered() {

        MyList myList = mock(MyList.class);

        doAnswer(invocation -> {
            Object arg0 = invocation.getArgument(0);
            Object arg1 = invocation.getArgument(1);

            assertEquals(3, arg0);
            assertEquals("answer me", arg1);

            return null;
        }).when(myList).add(any(Integer.class), any(String.class));

        myList.add(3, "answer me");

    }

    /**
     * <li>4- Partial Mocking</li>
     * Partial mocks are an option, too. Mockito's <strong>doCallRealMethod()</strong> can be used for void methods:
     * <p></p><strong>This allows us to call the actual method is called and verify it at the same time.</strong>
     */
    @Test
    public void whenAddCalledRealMethodCalled() {

        MyList myList = mock(MyList.class);
        doCallRealMethod().when(myList).add(any(Integer.class), any(String.class));

        myList.add(1, "real");

        verify(myList, times(1)).add(1, "real");
    }

    class MyList extends AbstractList<String> {

        @Override
        public String get(int index) {
            return null;
        }

        @Override
        public void add(int index, String element) {
            // no-op
        }

        @Override
        public int size() {
            return 0;
        }
    }

}
