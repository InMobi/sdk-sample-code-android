package com.inmobi.nativead.sample;

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

    public interface AdJsonKeys {
        String AD_TITLE = "title";
        String AD_CLICK_URL = "landingURL";
        String AD_IMAGE_OBJECT = "icon";
        String AD_IMAGE_URL = "url";
    }
}
