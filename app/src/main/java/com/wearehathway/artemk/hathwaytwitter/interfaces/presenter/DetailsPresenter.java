package com.wearehathway.artemk.hathwaytwitter.interfaces.presenter;

/**
 * Created by Artem on 11/20/17.
 */

public interface DetailsPresenter<T>  {
    void attachView(T view);
    void detachView();
    void loadTweet(long tweetId);
}

