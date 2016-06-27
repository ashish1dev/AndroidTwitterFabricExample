package net.simplifiedcoding.androidtablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.support.v7.app.AppCompatActivity;

import android.widget.Toast;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.*;
import com.twitter.sdk.android.core.identity.*;
import io.fabric.sdk.android.Fabric;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import android.os.Bundle;
import android.content.Intent;
/**
 * Created by Belal on 2/3/2016.
 */

//Our class extending fragment
public class Tab1 extends Fragment {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "tTcbCrBUxkUzhviXJzpAeEmM6";
    private static final String TWITTER_SECRET = "ttObDdkCEB2gw6JqoyrqC0RDDMuf59geWfLcVjBglF4ByDsQ0r";


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };


    public static TwitterLoginButton loginButton;
    private static final int TWEET_COMPOSER_REQUEST_CODE = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(getContext(), new Twitter(authConfig),  new TweetComposer());

    }

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab1, null);

        //Returning the layout file after inflating
        //Change R.layout.tab1 in you classes

        loginButton = (TwitterLoginButton) view.findViewById(R.id.twitter_login_button);
        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // TODO: Remove toast and use the TwitterSession's userID
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();

                Intent intent = null;
                try {
                    intent = new TweetComposer.Builder(getContext())
                            .text("Tweet from Fabric!")
                            .url(new URL("http://www.twitter.com"))
                            .createIntent();
                    startActivityForResult(intent, TWEET_COMPOSER_REQUEST_CODE);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });
        return view;
    }




}
