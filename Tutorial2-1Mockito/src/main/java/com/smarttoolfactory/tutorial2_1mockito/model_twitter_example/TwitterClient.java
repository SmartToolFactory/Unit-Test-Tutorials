package com.smarttoolfactory.tutorial2_1mockito.model_twitter_example;

public class TwitterClient {

    public void sendTweet(ITweet tweet) {
        String message = tweet.getMessage();

        // send the message to Twitter
    }
}