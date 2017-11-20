package com.wearehathway.artemk.hathwaytwitter.interfaces.view;

import com.twitter.sdk.android.core.models.Tweet;

/**
 * Created by Artem on 11/20/17.
 */

public interface DetailsView {
    void showTweet(Tweet tweet);
    void showProgress();
    void hideProgress();
    void showError(String message);
}
