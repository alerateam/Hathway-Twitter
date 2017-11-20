package com.wearehathway.artemk.hathwaytwitter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.wearehathway.artemk.hathwaytwitter.R;

public class LoginActivity extends AppCompatActivity {
    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        if (session != null) {
            // We already have a session
            launchTimelineActivity();
        } else {
            loginButton = (TwitterLoginButton) findViewById(R.id.login_button);
            loginButton.setCallback(new Callback<TwitterSession>() {
                @Override
                public void success(Result<TwitterSession> result) {
                    launchTimelineActivity();
                }

                @Override
                public void failure(TwitterException exception) {
                    // Showing error message
                    String message = exception != null ?
                            LoginActivity.this.getString(R.string.failed_to_login_msg) + ", " + exception.getLocalizedMessage() :
                            LoginActivity.this.getString(R.string.failed_to_login_msg);
                    Toast.makeText(LoginActivity.this,
                            message,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void launchTimelineActivity() {
        LoginActivity.this.startActivity(new Intent(LoginActivity.this,
                TwitterItemListActivity.class));
        // We don't want to have LoginActivity in the activity back stack
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        if (loginButton != null) {
            loginButton.onActivityResult(requestCode, resultCode, data);
        }
    }
}
