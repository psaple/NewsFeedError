package com.example.andriod.livenewsfeed;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livenewsfeed);

        //Create a list of News Feed.
        ArrayList<NewsFeed> news_feed = QueryUtils.extractNews();
        ListView newsFeedListView = (ListView) findViewById(R.id.list);
        NewsFeedAdapter adapter = new NewsFeedAdapter(this,news_feed);
        newsFeedListView.setAdapter(adapter);
    }
}
