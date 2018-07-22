package com.example.andriod.livenewsfeed;

public class NewsFeed {

    //@param Publication Date
    private String mWebPublicationDate;
    //@param Title of the Publication
    private String mWebTitle;
    //@param URL of the article
    private String mWebURL;

    public NewsFeed(String webPublicationDate, String webTitle, String webURL){
        this.mWebPublicationDate = webPublicationDate;
        this.mWebTitle           = webTitle;
        this.mWebURL             = webURL;
    }

    public String getWebPublicationDate(){
        return mWebPublicationDate;
    }

    public String getWebTitle(){
        return mWebTitle;
    }

    public String getWebURL(){
        return mWebURL;
    }
}
