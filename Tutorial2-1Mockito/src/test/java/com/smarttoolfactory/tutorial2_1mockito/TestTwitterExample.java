package com.smarttoolfactory.tutorial2_1mockito;


import com.smarttoolfactory.tutorial2_1mockito.model_twitter_example.ITweet;
import com.smarttoolfactory.tutorial2_1mockito.model_twitter_example.TwitterClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class TestTwitterExample {


    @Test
    public void testSendingTweet() {

        TwitterClient twitterClient = new TwitterClient();

        ITweet iTweet = mock(ITweet.class);

        // Mock interface behavior
        when(iTweet.getMessage()).thenReturn("Using mockito is great");

        twitterClient.sendTweet(iTweet);

        // We verify that iTweet interface getMessage method is invoked at least once
        verify(iTweet, atLeastOnce()).getMessage();

    }
}
