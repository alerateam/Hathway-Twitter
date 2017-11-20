package com.wearehathway.artemk.hathwaytwitter.presenters;

import android.text.TextUtils;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetUtils;
import com.wearehathway.artemk.hathwaytwitter.interfaces.presenter.DetailsPresenter;
import com.wearehathway.artemk.hathwaytwitter.interfaces.view.DetailsView;

/**
 * Created by Artem on 11/20/17.
 */

public class DetailsPresenterImpl implements DetailsPresenter<DetailsView> {

    private DetailsView mDetailsView;

    @Override
    public void attachView(DetailsView view) {
        mDetailsView = view;
    }

    @Override
    public void detachView() {
        mDetailsView = null;
    }

    @Override
    public void loadTweet(long tweetId) {
        if (mDetailsView != null) {
            if (tweetId < 0) {
                sendLoadFailedErrorMessage("Incorrect tweet id");
                return;
            }

            mDetailsView.showProgress();

            TweetUtils.loadTweet(tweetId, new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    mDetailsView.hideProgress();
                    if (result != null) {
                        mDetailsView.showTweet(result.data);
                    } else {
                        sendLoadFailedErrorMessage(null);
                    }
                }

                @Override
                public void failure(TwitterException exception) {
                    mDetailsView.hideProgress();
                    sendLoadFailedErrorMessage(exception.getMessage());
                }
            });
        }
    }

    private void sendLoadFailedErrorMessage(String reason) {
        String errorMessage = "Failed to load tweet";
        if (!TextUtils.isEmpty(reason)) {
            errorMessage = errorMessage + ": " + reason;
        }

        if (mDetailsView != null) {
            mDetailsView.showError(errorMessage);
        }
    }
}