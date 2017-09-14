package com.inmobi.banner.utility;

/**
 * Created by rahul.raja on 1/23/17.
 */

public class Constants {

    public static final String FEED_URL = "https://api.rss2json.com/v1/api.json?rss_url=http://rss.nytimes.com/services/xml/rss/nyt/World.xml";
    public static final String FALLBACK_IMAGE_URL = "https://s3-ap-southeast-1.amazonaws.com/inmobi-surpriseme/notification/notif2.jpg";

    public interface FeedJsonKeys {
        String FEED_LIST = "items";
        String CONTENT_TITLE = "title";
        String CONTENT_ENCLOSURE = "enclosure";
        String CONTENT_LINK = "link";
        String FEED_CONTENT = "content";
    }

    public static final int BANNER_WIDTH = 320;
    public static final int BANNER_HEIGHT = 50;

}
