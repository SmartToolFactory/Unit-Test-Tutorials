package com.smarttoolfactory.tutorial2_2mockito_bdd;


import com.smarttoolfactory.tutorial2_2mockito_bdd.model_phone_service.PhoneBookRepository;
import com.smarttoolfactory.tutorial2_2mockito_bdd.model_phone_service.PhoneBookService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.BDDMockito.*;

public class Test1Basics {


    String momContactName = "Mom";
    String momPhoneNumber = "01234";
    String xContactName = "x";
    String tooLongPhoneNumber = "01111111111111";


    @InjectMocks
    PhoneBookService phoneBookService;

    @Mock
    PhoneBookRepository phoneBookRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testReturnFixedValue() {

        when(phoneBookRepository.contains(momContactName)).thenReturn(false);

        phoneBookService.register(xContactName, "");

        verify(phoneBookRepository, never()).insert(momContactName, momPhoneNumber);


    }

    @Test
    public void testReturnFixedValueBDD() {

        // Given
        given(phoneBookRepository.contains(momContactName))
                .willReturn(false);

        // When
        phoneBookService.register(xContactName, "");

        // Then
        then(phoneBookRepository)
                .should(never())
                .insert(momContactName, momPhoneNumber);


    }

}
