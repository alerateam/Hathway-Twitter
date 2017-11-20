package com.wearehathway.artemk.hathwaytwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.UserTimeline;
import com.wearehathway.artemk.hathwaytwitter.R;
import com.wearehathway.artemk.hathwaytwitter.adapters.MyCustomRecyclerViewAdapter;
import com.wearehathway.artemk.hathwaytwitter.fragments.TwitterItemDetailFragment;
import com.wearehathway.artemk.hathwaytwitter.interfaces.IOnTweetClickedListener;

/**
 * An activity representing a list of TwitterItems. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TwitterItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class TwitterItemListActivity extends AppCompatActivity implements IOnTweetClickedListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitteritem_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.twitteritem_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        // Showing the logged user's tweets
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        UserTimeline userTimeline = new UserTimeline.Builder().userId(session.getUserId()).build();

        /*
        Using a custom RecyclerView to implement the "on item click" since the Twitter's
        TweetTimelineRecyclerViewAdapter apparently only implements the "on link clicked" interface
        More detailed comment inside the custom adapter class
        */
        final MyCustomRecyclerViewAdapter adapter = new MyCustomRecyclerViewAdapter(TwitterItemListActivity.this,
                userTimeline,
                R.style.tw__TweetLightWithActionsStyle,
                new Callback<Tweet>() {
                    @Override
                    public void success(Result<Tweet> result) {
                        Log.d("Callback", "success");
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.d("Callback", "failure");
                    }
                });
        adapter.setOnTweetClickedListener(this);

        RecyclerView recyclerView = findViewById(R.id.twitteritem_list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTweetClicked(long tweetId) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putLong(TwitterItemDetailFragment.ARG_TWEET, tweetId);
            TwitterItemDetailFragment fragment = new TwitterItemDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.twitteritem_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, TwitterItemDetailActivity.class);
            intent.putExtra(TwitterItemDetailFragment.ARG_TWEET, tweetId);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                // Clear current session
                TwitterCore.getInstance().getSessionManager().clearActiveSession();

                // Launch login activity and close the current one
                startActivity(new Intent(TwitterItemListActivity.this,
                        LoginActivity.class));
                TwitterItemListActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
