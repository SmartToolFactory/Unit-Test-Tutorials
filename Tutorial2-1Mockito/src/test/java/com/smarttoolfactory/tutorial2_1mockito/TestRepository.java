package com.smarttoolfactory.tutorial2_1mockito;

import com.smarttoolfactory.tutorial2_1mockito.model_repo.DatabaseDAO;
import com.smarttoolfactory.tutorial2_1mockito.model_repo.NetworkDAO;
import com.smarttoolfactory.tutorial2_1mockito.model_repo.Repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestRepository {

    @InjectMocks
    Repository repository;

    @Mock
    DatabaseDAO databaseDAO;

    @Mock
    NetworkDAO networkDAO;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testValidate() {
        boolean saved = repository.save("temp.txt");
        assertEquals(true, saved);
    }

    @Test
    public void testVerify() {
        boolean saved = repository.save("temp.txt");
        assertEquals(true, saved);

        // verify makes sure that this method is called at least number of time that is put into times() method
        verify(databaseDAO, times(1)).save("temp.txt");
        verify(networkDAO, times(1)).save("temp.txt");
    }
}