package com.wearehathway.artemk.hathwaytwitter.interfaces;

/**
 * Created by Artem on 11/20/17.
 */

public interface IOnTweetClickedListener {
    // This interface is used to communicate clicks on the concrete Tweet in the timeline
    void onTweetClicked(long tweetId);
}
