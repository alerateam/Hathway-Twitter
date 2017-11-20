package com.wearehathway.artemk.hathwaytwitter.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TweetView;
import com.wearehathway.artemk.hathwaytwitter.R;
import com.wearehathway.artemk.hathwaytwitter.activities.TwitterItemDetailActivity;
import com.wearehathway.artemk.hathwaytwitter.activities.TwitterItemListActivity;
import com.wearehathway.artemk.hathwaytwitter.interfaces.presenter.DetailsPresenter;
import com.wearehathway.artemk.hathwaytwitter.interfaces.view.DetailsView;
import com.wearehathway.artemk.hathwaytwitter.presenters.DetailsPresenterImpl;

/**
 * A fragment representing a single TwitterItem detail screen.
 * This fragment is either contained in a {@link TwitterItemListActivity}
 * in two-pane mode (on tablets) or a {@link TwitterItemDetailActivity}
 * on handsets.
 */
public class TwitterItemDetailFragment extends Fragment implements DetailsView {
    /**
     * The fragment argument representing the Tweet that this fragment
     * represents.
     */
    public static final String ARG_TWEET = "TwitterItemDetailFragment.ARG_TWEET";

    private long mTweetId;
    private RelativeLayout rootView;
    private ProgressBar mProgressBar;
    private DetailsPresenter<DetailsView> mDetailsPresenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TwitterItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        * This fragment is used to display the MVP approach.
        *
        * Fragment itself is a View, it implements the DetailsView interface and gets notified when
        * the UI needs to change.
        *
        * DetailsPresenterImpl is a presenter class, which does all the non-view related logic and
        * implements the interface for Presenter-View communication.
        *
        * */
        mDetailsPresenter = new DetailsPresenterImpl();
        mDetailsPresenter.attachView(this);

        if (getArguments().containsKey(ARG_TWEET)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mTweetId = getArguments().getLong(ARG_TWEET);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(getString(R.string.details_title));
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (RelativeLayout) inflater.inflate(R.layout.twitteritem_detail, container, false);
        mProgressBar = rootView.findViewById(R.id.progressBar);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDetailsPresenter.loadTweet(mTweetId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDetailsPresenter.detachView();
    }

    @Override
    public void showTweet(Tweet tweet) {
        if (rootView != null && tweet != null) {
            rootView.addView(new TweetView(getActivity(), tweet));
        }
    }

    @Override
    public void showProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(getActivity(),
                TextUtils.isEmpty(message) ? getString(R.string.unknown_error) : message,
                Toast.LENGTH_SHORT).show();
    }
}
