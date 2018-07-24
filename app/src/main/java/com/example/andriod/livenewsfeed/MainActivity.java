package com.example.andriod.livenewsfeed;


import android.app.LoaderManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.content.Intent;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderCallbacks<List<NewsFeed>>{

    private static final String LOG_TAG = MainActivity.class.getName();
    /**
     * URL for the Guardian API site
    */
    private static final String GUARDIAN_REQUEST_URL=
            "https://content.guardianapis.com/search";

    /**
     * Constant value for the NewsFeed loader ID. We can choose any integer.
     */
    private static final int NEWSFEED_LOADER_ID = 1;
    /**
     * Adapter for NewsFeed
     */
    private NewsFeedAdapter mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG,"TEST: MainActivity onCreate()called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livenewsfeed);

        ListView newsFeedListView = (ListView) findViewById(R.id.list);
        mAdapter = new NewsFeedAdapter(this,new ArrayList<NewsFeed>());
        newsFeedListView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsFeedListView.setEmptyView(mEmptyStateTextView);

        newsFeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Find the current article that was clicked on
                NewsFeed currentNews = mAdapter.getItem(position);
                //Convert the string URL into URI object (to pass to Intent constructor)
                Uri NewsFeedUri = Uri.parse(currentNews.getWebURL());
                // Create a new intent to view News Feed URI
                Intent webSiteIntent = new Intent(Intent.ACTION_VIEW,NewsFeedUri);
                //Send the intent to launch a new activity
                startActivity(webSiteIntent);
            }
        });
        //Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            //Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            //Initialize the loader. Pass in the int ID constant defined above and pass in null for
            //the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            //because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWSFEED_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            }
        }
    @Override
    public Loader<List<NewsFeed>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG,"TEST: onCreateLoader()called");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        // getString retrieves a String value from the preferences. The second parameter is the default value for this preference.
        String sectionValue = sharedPrefs.getString(
                getString(R.string.settings_section_name_key),
                getString(R.string.settings_section_name_default));
        // parse breaks apart the URI string that's passed into its parameter
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        // buildUpon prepares the baseUri that we just parsed so we can add query parameters to it
        Uri.Builder uriBuilder = baseUri.buildUpon();
        // Append query parameter and its value.
        uriBuilder.appendQueryParameter("section", sectionValue);
        uriBuilder.appendQueryParameter("api-key", "test");
        return new NewsFeedLoader(this, uriBuilder.toString());
    }
    @Override
    public void onLoadFinished(Loader<List<NewsFeed>> loader, List<NewsFeed> news_feed) {
        Log.i(LOG_TAG,"TEST: onLoadFinished()called");

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.No_News);

        // Clear the adapter of previous newsfeed data
        mAdapter.clear();
        // If there is a valid list of {@link NewsFeed}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news_feed != null && !news_feed.isEmpty()) {
            mAdapter.addAll(news_feed);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<NewsFeed>> loader){
        Log.i(LOG_TAG,"TEST: onLoaderReset()called");
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}


