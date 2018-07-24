package com.example.andriod.livenewsfeed;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import android.text.TextUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

/**
 * Helper methods related to requesting and receiving News feed data from Guardian
 */
public final class QueryUtils {

    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils(){
    }
    /**
     * Return a list of {@link NewsFeed} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<NewsFeed> extractNews(String requestUrl) {
        Log.i(LOG_TAG,"TEST: extractNews()called");
        //Create URL object
        URL url = createUrl(requestUrl);
        //Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<NewsFeed> news_feed = extractResultsFromJson(jsonResponse);
        // Return the list of {@link NewsFeed}
        return news_feed;

    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<NewsFeed> extractResultsFromJson(String newsFeedJSON){
        if (TextUtils.isEmpty(newsFeedJSON)) {
            return null;
        }
        // Create an empty ArrayList that we can start adding newsfeed to
        List<NewsFeed> news_feed = new ArrayList<>();
        // Try to parse JSON RESPONSE STRING. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // build up a list of News objects with the corresponding data.
            JSONObject jsonObj = new JSONObject(newsFeedJSON);
            JSONObject currentResponse = jsonObj.getJSONObject("response");
            JSONArray newsArray = currentResponse.optJSONArray("results");
            for (int i=0;i<newsArray.length();i++) {
                JSONObject currentNews = newsArray.getJSONObject(i);
                String webPublicationDate = currentNews.getString("webPublicationDate");
                String webTitle           = currentNews.getString("webTitle");
                String webURL             = currentNews.getString("webUrl");
                String sectionName        = currentNews.getString("sectionName");
                // Extract JSONArray with tags (to retrieve ContributorName)
                JSONArray tagsArray = currentNews.getJSONArray("tags");
                String authorName = null;
                if (tagsArray.length() > 0){
                    for (int j = 0; j < tagsArray.length(); j++) {
                        JSONObject currentTag = tagsArray.getJSONObject(j);
                        try {
                            authorName = currentTag.getString("webTitle");
                        } catch (JSONException e){
                            Log.e("QueryUtils", "Missing one or more author'Name JSON object");
                        }
                    }
                }

                NewsFeed newsFeed = new NewsFeed(webPublicationDate,webTitle,webURL,sectionName,authorName);
                news_feed.add(newsFeed);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing News Feed JSON results", e);
        }
        // Return the list of news
        return news_feed;
    }
}
