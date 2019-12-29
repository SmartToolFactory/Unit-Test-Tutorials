package com.journaldev.mockitoexamples.injectmocks;


import com.journaldev.mockitoexamples.injectmocksservices.AppServices;
import com.journaldev.mockitoexamples.injectmocksservices.AppServices1;
import com.journaldev.mockitoexamples.injectmocksservices.AppServices2;
import com.journaldev.mockitoexamples.injectmocksservices.EmailService;
import com.journaldev.mockitoexamples.injectmocksservices.SMSService;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class MockitoInjectMocksExamples extends BaseTestCase {

    @Mock
    EmailService emailService;

    @Mock
    SMSService smsService;

    @InjectMocks
    AppServices appServicesConstructorInjectionMock;

    @InjectMocks
    AppServices1 appServicesSetterInjectionMock;

    @InjectMocks
    AppServices2 appServicesFieldInjectionMock;

    @Test
    void test_constructor_injection_mock() {
        when(appServicesConstructorInjectionMock.sendEmail("Email")).thenReturn(true);
        when(appServicesConstructorInjectionMock.sendSMS(anyString())).thenReturn(true);

        assertTrue(appServicesConstructorInjectionMock.sendEmail("Email"));
        assertFalse(appServicesConstructorInjectionMock.sendEmail("Unstubbed Email"));

        assertTrue(appServicesConstructorInjectionMock.sendSMS("SMS"));

    }

    @Test
    void test_setter_injection_mock() {
        when(appServicesSetterInjectionMock.sendEmail("New Email")).thenReturn(true);
        when(appServicesSetterInjectionMock.sendSMS(anyString())).thenReturn(true);

        assertTrue(appServicesSetterInjectionMock.sendEmail("New Email"));
        assertFalse(appServicesSetterInjectionMock.sendEmail("Unstubbed Email"));

        assertTrue(appServicesSetterInjectionMock.sendSMS("SMS"));

    }

    @Test
    void test_field_injection_mock() {
        when(appServicesFieldInjectionMock.sendEmail(anyString())).thenReturn(true);
        when(appServicesFieldInjectionMock.sendSMS(anyString())).thenReturn(true);

        assertTrue(appServicesFieldInjectionMock.sendEmail("Email"));
        assertTrue(appServicesFieldInjectionMock.sendEmail("New Email"));

        assertTrue(appServicesFieldInjectionMock.sendSMS("SMS"));

    }
}
