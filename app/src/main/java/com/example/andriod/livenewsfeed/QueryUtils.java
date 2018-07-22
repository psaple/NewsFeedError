package com.example.andriod.livenewsfeed;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving News feed data from Guardian
 */
public final class QueryUtils {
    /* Sample JSON response for Guardian query */
    private static final String SAMPLE_JSON_RESPONSE =
            "{\"response\":{\"status\":\"ok\",\"userTier\":\"developer\",\"total\":2049507,\"startIndex\":1,\"pageSize\":10,\"currentPage\":1,\"pages\":204951,\"orderBy\":\"newest\",\"results\":[{\"id\":\"commentisfree/2018/jul/21/designer-babies-gene-editing-curing-disease\",\"type\":\"article\",\"sectionId\":\"commentisfree\",\"sectionName\":\"Opinion\",\"webPublicationDate\":\"2018-07-22T08:00:00Z\",\"webTitle\":\"Fear of dystopian change should not blind us to the potential of gene editing | Kenan Malik\",\"webUrl\":\"https://www.theguardian.com/commentisfree/2018/jul/21/designer-babies-gene-editing-curing-disease\",\"apiUrl\":\"https://content.guardianapis.com/commentisfree/2018/jul/21/designer-babies-gene-editing-curing-disease\",\"isHosted\":false,\"pillarId\":\"pillar/opinion\",\"pillarName\":\"Opinion\"},{\"id\":\"environment/2018/jul/22/labour-promises-reinstate-agricultural-wages-board\",\"type\":\"article\",\"sectionId\":\"environment\",\"sectionName\":\"Environment\",\"webPublicationDate\":\"2018-07-21T23:04:48Z\",\"webTitle\":\"Labour pledges to reinstate Agricultural Wages Board\",\"webUrl\":\"https://www.theguardian.com/environment/2018/jul/22/labour-promises-reinstate-agricultural-wages-board\",\"apiUrl\":\"https://content.guardianapis.com/environment/2018/jul/22/labour-promises-reinstate-agricultural-wages-board\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"crosswords/everyman/3745\",\"type\":\"crossword\",\"sectionId\":\"crosswords\",\"sectionName\":\"Crosswords\",\"webPublicationDate\":\"2018-07-21T23:00:48Z\",\"webTitle\":\"Everyman crossword No 3,745\",\"webUrl\":\"https://www.theguardian.com/crosswords/everyman/3745\",\"apiUrl\":\"https://content.guardianapis.com/crosswords/everyman/3745\",\"isHosted\":false,\"pillarId\":\"pillar/lifestyle\",\"pillarName\":\"Lifestyle\"},{\"id\":\"business/grogonomics/2018/jul/22/greg-jericho-industry-super-funds-keep-outclassing-retail-ones-but-the-attacks-dont-stop\",\"type\":\"article\",\"sectionId\":\"business\",\"sectionName\":\"Business\",\"webPublicationDate\":\"2018-07-21T22:19:16Z\",\"webTitle\":\"Industry super funds are thrashing those run by banks â€“ and business is crying foul | Greg Jericho\",\"webUrl\":\"https://www.theguardian.com/business/grogonomics/2018/jul/22/greg-jericho-industry-super-funds-keep-outclassing-retail-ones-but-the-attacks-dont-stop\",\"apiUrl\":\"https://content.guardianapis.com/business/grogonomics/2018/jul/22/greg-jericho-industry-super-funds-keep-outclassing-retail-ones-but-the-attacks-dont-stop\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"environment/2018/jul/22/two-new-peacock-spiders-identified-in-western-australia\",\"type\":\"article\",\"sectionId\":\"environment\",\"sectionName\":\"Environment\",\"webPublicationDate\":\"2018-07-21T22:14:11Z\",\"webTitle\":\"Two new peacock spiders identified in Western Australia\",\"webUrl\":\"https://www.theguardian.com/environment/2018/jul/22/two-new-peacock-spiders-identified-in-western-australia\",\"apiUrl\":\"https://content.guardianapis.com/environment/2018/jul/22/two-new-peacock-spiders-identified-in-western-australia\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"football/2018/jul/21/anthony-martial-standoff-future-manchester-united\",\"type\":\"article\",\"sectionId\":\"football\",\"sectionName\":\"Football\",\"webPublicationDate\":\"2018-07-21T21:30:46Z\",\"webTitle\":\"Anthony Martial set for stand-off over future at Manchester United\",\"webUrl\":\"https://www.theguardian.com/football/2018/jul/21/anthony-martial-standoff-future-manchester-united\",\"apiUrl\":\"https://content.guardianapis.com/football/2018/jul/21/anthony-martial-standoff-future-manchester-united\",\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"},{\"id\":\"politics/2018/jul/21/brexit-chequers-deal-eu-deadline-summer\",\"type\":\"article\",\"sectionId\":\"politics\",\"sectionName\":\"Politics\",\"webPublicationDate\":\"2018-07-21T21:30:28Z\",\"webTitle\":\"May sends cabinet on mini-breaks to Europe to sell her Brexit deal\",\"webUrl\":\"https://www.theguardian.com/politics/2018/jul/21/brexit-chequers-deal-eu-deadline-summer\",\"apiUrl\":\"https://content.guardianapis.com/politics/2018/jul/21/brexit-chequers-deal-eu-deadline-summer\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"football/2018/jul/21/sean-dyche-burnley-europa-league-aberdeen\",\"type\":\"article\",\"sectionId\":\"football\",\"sectionName\":\"Football\",\"webPublicationDate\":\"2018-07-21T21:29:46Z\",\"webTitle\":\"Sean Dyche keeps Burnley focused on the familiar before Europa League bow\",\"webUrl\":\"https://www.theguardian.com/football/2018/jul/21/sean-dyche-burnley-europa-league-aberdeen\",\"apiUrl\":\"https://content.guardianapis.com/football/2018/jul/21/sean-dyche-burnley-europa-league-aberdeen\",\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"},{\"id\":\"society/2018/jul/21/nhs-beds-number-mental-health-patients-falls\",\"type\":\"article\",\"sectionId\":\"society\",\"sectionName\":\"Society\",\"webPublicationDate\":\"2018-07-21T21:21:35Z\",\"webTitle\":\"Number of NHS beds for mental health patients slumps by 30%\",\"webUrl\":\"https://www.theguardian.com/society/2018/jul/21/nhs-beds-number-mental-health-patients-falls\",\"apiUrl\":\"https://content.guardianapis.com/society/2018/jul/21/nhs-beds-number-mental-health-patients-falls\",\"isHosted\":false,\"pillarId\":\"pillar/news\",\"pillarName\":\"News\"},{\"id\":\"sport/2018/jul/21/mcilroy-and-fleetwood-disappointed-but-both-remain-in-open-contention\",\"type\":\"article\",\"sectionId\":\"sport\",\"sectionName\":\"Sport\",\"webPublicationDate\":\"2018-07-21T20:31:50Z\",\"webTitle\":\"McIlroy and Fleetwood disappointed but both remain in Open contention\",\"webUrl\":\"https://www.theguardian.com/sport/2018/jul/21/mcilroy-and-fleetwood-disappointed-but-both-remain-in-open-contention\",\"apiUrl\":\"https://content.guardianapis.com/sport/2018/jul/21/mcilroy-and-fleetwood-disappointed-but-both-remain-in-open-contention\",\"isHosted\":false,\"pillarId\":\"pillar/sport\",\"pillarName\":\"Sport\"}]}}";
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
        public static ArrayList<NewsFeed> extractNews(){

            // Create an empty ArrayList that we can start adding newsfeed to
            ArrayList<NewsFeed> news_feed = new ArrayList<>();
            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {
                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of News objects with the corresponding data.
                JSONObject root = new JSONObject(SAMPLE_JSON_RESPONSE);
                JSONObject currentResponse = root.getJSONObject("response");
                JSONArray newsArray = currentResponse.optJSONArray("results");
                for (int i=0;i<newsArray.length();i++) {
                    JSONObject currentNews = newsArray.getJSONObject(i);
                    String webPublicationDate = currentNews.getString("webPublicationDate");
                    String webTitle           = currentNews.getString("webTitle");
                    String webURL             = currentNews.getString("webURL");
                    NewsFeed newsFeed = new NewsFeed(webPublicationDate,webTitle,webURL);
                    news_feed.add(newsFeed);
                }
            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.

                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);

            }
            // Return the list of news
            return news_feed;
        }
    }
