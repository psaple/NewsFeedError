package com.example.andriod.livenewsfeed;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Loads NewsFeed by using an AsyncTask to perform the networkrequest to URL
 */
public class NewsFeedLoader extends AsyncTaskLoader<List<NewsFeed>> {
    private static final String LOG_TAG = NewsFeedLoader.class.getName();
    /** Query URL */
    private String mUrl;
    /**
     * Constructs a new {@link NewsFeedLoader}.
     * @param context of the activity
     * @param url to load data from
     */
    public NewsFeedLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }
    protected void onStartLoading() {
        Log.i(LOG_TAG,"TEST: onStartLoading()called");
        forceLoad();
    }
    /**
    * This is on a background thread.
    */
    @Override
    public List<NewsFeed> loadInBackground() {
        Log.i(LOG_TAG,"TEST: loadInBackground called");
        if (mUrl == null) {
           return null;
        }
        // Perform the network request, parse the response, and extract a NewsFeed.
        List<NewsFeed> news_feed = QueryUtils.extractNews(mUrl);
        return news_feed;
    }
}
