package com.example.andriod.livenewsfeed;

public class NewsFeed {

    //@param Publication Date
    private String mWebPublicationDate;
    //@param Title of the Publication
    private String mWebTitle;
    //@param URL of the article
    private String mWebURL;
    //@param Section Name of the article
    private String mWebSectionName;
    //@param Author Name
    private String mAuthorName;

    public NewsFeed(String webPublicationDate, String webTitle, String webURL, String webSectionName, String AuthorName){
        this.mWebPublicationDate = webPublicationDate;
        this.mWebTitle           = webTitle;
        this.mWebURL             = webURL;
        this.mWebSectionName     = webSectionName;
        this.mAuthorName         = AuthorName;
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

    public String getWebSectionName(){
        return mWebSectionName;
    }

    public String getAuthorName(){
        return mAuthorName;
    }
}
