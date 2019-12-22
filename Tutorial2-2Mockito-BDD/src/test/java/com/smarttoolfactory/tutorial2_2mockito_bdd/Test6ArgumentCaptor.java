package com.smarttoolfactory.tutorial2_2mockito_bdd;

import com.smarttoolfactory.tutorial2_2mockito_bdd.model_login_manager.ILoginManager;
import com.smarttoolfactory.tutorial2_2mockito_bdd.model_login_manager.LoginManagerImpl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
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
    public void validate_pass() {

        // Given
        given(iLoginManager.validate("hello", "world")).willReturn("hello-world");

        // When
        String expected = loginManagerImpl.validate("hello", "world");

        // Then
        then(iLoginManager).should().validate(argumentCaptor.capture(), argumentCaptor.capture());
        // 🔥🔥 argumentCaptor MUST be called after mock method is called
        List<String> arguments = argumentCaptor.getAllValues();

        assertThat("hello", is(arguments.get(0)));
        assertThat("world", is(arguments.get(1)));

        assertThat(expected, is("hello-world"));

    }


}