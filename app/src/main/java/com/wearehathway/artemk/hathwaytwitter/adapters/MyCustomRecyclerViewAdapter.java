package com.wearehathway.artemk.hathwaytwitter.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.TweetBuilder;
import com.twitter.sdk.android.tweetui.CompactTweetView;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;
import com.wearehathway.artemk.hathwaytwitter.interfaces.IOnTweetClickedListener;

/**
 * Created by Artem on 11/20/17.
 */

public class MyCustomRecyclerViewAdapter extends TweetTimelineRecyclerViewAdapter {

    private IOnTweetClickedListener listener;

    public MyCustomRecyclerViewAdapter(Context context, Timeline<Tweet> timeline) {
        super(context, timeline);
    }

    public MyCustomRecyclerViewAdapter(Context context, Timeline<Tweet> timeline, int styleResId, Callback<Tweet> cb) {
        super(context, timeline, styleResId, cb);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public TweetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final Tweet tweet = new TweetBuilder().build();
        final CompactTweetView compactTweetView = new CompactTweetView(parent.getContext(), tweet, styleResId);
        compactTweetView.setOnActionCallback(actionCallback);

        /*
        * Apparently there is an issue of getting the click event on the CompactTweetView class.
        * According to
        * https://twittercommunity.com/t/how-to-customize-tweet-timeline-recycler-view-adapter/91698/2
        * Twitter SDK only provides an "onLinkClick" event, which only triggers when actual URLs are clicked.
        * This is not an option for our use case.
        *
        * So, in order to workaround it, the OnTouchListener is used here. This is not a recommended
        * approach in terms of android guidelines for RecyclerViews, but for this particular case
        * and the limited time line can be used.
        *
        * Other possible workaround here would be to get rid of the Twitter's RecyclerView and use their regular
        * ListAdapter. But again, time for the task in limited, so not worth spending time on this change.
        * */

        compactTweetView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                CompactTweetView tweetView = (CompactTweetView) v;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    Log.d("AAA setOnTouchListener",
                            "tweetView.getTweetId()= " + tweetView.getTweetId());

                    if (listener != null) {
                        listener.onTweetClicked(tweetView.getTweet().getId());
                    }
                    return true;
                }
                return false;
            }
        });
        return new TweetViewHolder(compactTweetView);
    }

    public void setOnTweetClickedListener(IOnTweetClickedListener listener) {
        this.listener = listener;
    }
}
