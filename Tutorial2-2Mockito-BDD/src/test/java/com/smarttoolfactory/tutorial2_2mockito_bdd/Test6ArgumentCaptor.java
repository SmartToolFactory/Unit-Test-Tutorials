package com.smarttoolfactory.tutorial2_2mockito_bdd;

import com.smarttoolfactory.tutorial2_2mockito_bdd.model_login_manager.ILoginManager;
import com.smarttoolfactory.tutorial2_2mockito_bdd.model_login_manager.LoginManagerImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;


@RunWith(MockitoJUnitRunner.class)
public class Test6ArgumentCaptor {

    @InjectMocks
    LoginManagerImpl loginManagerImpl;

    @Mock
    ILoginManager iLoginManager;


    @Captor
    private ArgumentCaptor<String> argumentCaptor;


    @Test
    public void validate() {

        // Given
        given(iLoginManager.validate(argumentCaptor.capture(), argumentCaptor.capture()))
                .willAnswer((Answer<String>) invocation -> {
                    List<String> credentials = argumentCaptor.getAllValues();
                    return credentials.get(0) + "-" + credentials.get(1);
                });


        // When
        String expected = loginManagerImpl.validate("hello", "world");
        // 🔥🔥 argumentCaptor MUST be called after mock method is called
        List<String> credentials = argumentCaptor.getAllValues();

        // Then
        then(iLoginManager).should().validate(credentials.get(0), credentials.get(1));

        assertThat(expected, is("hello-world"));


    }


}