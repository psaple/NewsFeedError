package com.example.andriod.livenewsfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Date;

import java.util.List;
import java.text.SimpleDateFormat;

public class NewsFeedAdapter extends ArrayAdapter<NewsFeed> {

    /**
     * Constructs a new (@Link NewsFeedAdapter)
     * @param context of the app
     * @param news_feed is the list of the news (data source of the adapter)
     */
    public NewsFeedAdapter(Context context, List<NewsFeed> news_feed){
        super(context,0,news_feed);
    }

    /**
     * Returns a list item view that displays information about the news at a given position
     * in list of news
     */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item,parent,false);
        }
        //Find the news at the given position in the list of News
        NewsFeed currentNewsFeed = getItem(position);

        //Find the text view with View ID webPublicationDate
        TextView webPublicationDateView = (TextView) listItemView.findViewById(R.id.webPublicationDate);
        String dateStr = currentNewsFeed.getWebPublicationDate().substring(0,10);
        SimpleDateFormat dateObj = new SimpleDateFormat("yyyy-mm-dd");
        Date date = null;
        try {
            date = dateObj.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        dateObj = new SimpleDateFormat("MMM dd, yyyy");
        String FormatedDateString = dateObj.format(date);
        webPublicationDateView.setText(FormatedDateString);

        //Find the text view with View ID webTitle
        TextView webTitleView = (TextView) listItemView.findViewById(R.id.webTitle);
        webTitleView.setText(currentNewsFeed.getWebTitle());

        //Find the text view with View ID SectionName
        TextView webSectionNameView = (TextView) listItemView.findViewById(R.id.webSectionName);
        webSectionNameView.setText(currentNewsFeed.getWebSectionName());

        //Find the text view with View ID AuthorName
        TextView AuthorNameView = (TextView) listItemView.findViewById(R.id.AuthorName);
        AuthorNameView.setText(currentNewsFeed.getAuthorName());

        return listItemView;
    }
}

