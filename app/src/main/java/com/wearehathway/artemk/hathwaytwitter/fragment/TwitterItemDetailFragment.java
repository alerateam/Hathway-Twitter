package com.wearehathway.artemk.hathwaytwitter.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wearehathway.artemk.hathwaytwitter.R;
import com.wearehathway.artemk.hathwaytwitter.activity.TwitterItemDetailActivity;
import com.wearehathway.artemk.hathwaytwitter.activity.TwitterItemListActivity;

/**
 * A fragment representing a single TwitterItem detail screen.
 * This fragment is either contained in a {@link TwitterItemListActivity}
 * in two-pane mode (on tablets) or a {@link TwitterItemDetailActivity}
 * on handsets.
 */
public class TwitterItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TwitterItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.twitteritem_detail, container, false);


        return rootView;
    }
}
