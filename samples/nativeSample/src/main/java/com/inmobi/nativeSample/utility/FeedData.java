package com.inmobi.nativeSample.utility;

import android.databinding.BaseObservable;
import android.view.View;

import com.inmobi.ads.InMobiNative;

import java.util.ArrayList;
import java.util.Random;

public final class FeedData {

    private static FeedItem[] mSampleFeedItems = new FeedItem[5];

    static {
        mSampleFeedItems[0] = new FeedItem("Neha Jha", "Product Manager", "1:50 AM", "Looking out for a Sponsorship Manager with 5+ yrs exp for a sports tourism company in Bangalore with strong grasp of media planning principles & excellent understanding of target segment, market intelligence and media medium technicalities. For more infos contact me at neha@zyoin.com", "neha_jha", "neha_jha_big");
        mSampleFeedItems[1] = new FeedItem("Nazia Firdose", "HR", "9:50 AM", "Please pray for these children in Syria after the death of their mother. The oldest sister has to take care of her younger siblings. -Ayad L Gorgees. ***Please don't scroll past without Typing Amen! because they need our prayers!!", "nazia", "nazia_big");
        mSampleFeedItems[2] = new FeedItem("Dharmesh Shah", "Founder at HubSpot", "4:50 PM", "Why, dear God, haven't you started marketing yet? http://dharme.sh/1Ewu63k by @gjain via @Inboundorg", "dharmesh", "dharmesh_big");
        mSampleFeedItems[3] = new FeedItem("Piyush Shah", "CPO", "6:50 PM", "With mobile being accepted as the definitive medium to access consumers’ minds and wallets, Brands have begun a multi-million dollar spending race to allure and retain customers.  Read on: https://lnkd.in/e8mcUfc", "piyush", "piyush_big");
        mSampleFeedItems[4] = new FeedItem("Jeff Weiner", "CEO at Linkedin", "4:10 AM", "Honored to represent LinkedIn's Economic Graph capabilities at the White House earlier today and partnering to Upskill America.", "jeff", "jeff_big");
    }

    public static class AdNativeData extends BaseObservable {

        private InMobiNative adNative;

        private int width;

        public InMobiNative getAdNative() {
            return adNative;
        }

        public AdNativeData() {
        }

        public AdNativeData(InMobiNative adNative) {
            this.adNative = adNative;
        }

        public void bindData(InMobiNative adNative) {
            this.adNative = adNative;
        }

        public void onPause() {
            if (null != adNative) {
                adNative.pause();
            }
        }

        public void onResume() {
            if (null != adNative) {
                adNative.resume();
            }
        }

        public String getIcon() {
            return adNative.getAdIconUrl();
        }


        public String getTitle() {
            return adNative.getAdTitle();
        }


        public String getCtaText() {
            return adNative.getAdCtaText();
        }

        public String getDescription() {
            return adNative.getAdDescription();
        }

        public float getRating() {
            return adNative.getAdRating();
        }

        public void onAdClick(View view) {
            adNative.reportAdClickAndOpenLandingPage();
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getWidth() {
            return width;
        }
    }


    public static class FeedItem extends BaseObservable {
        @Override
        public String toString() {
            return "FeedItem{" +
                    "title='" + title + '\'' +
                    ", subtitle='" + subtitle + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    ", description='" + description + '\'' +
                    ", thumbImage='" + thumbImage + '\'' +
                    ", bigImage='" + bigImage + '\'' +
                    '}';
        }

        private String title;
        private String subtitle;
        private String timestamp;
        private String description;
        private String thumbImage;
        private String bigImage;

        public FeedItem(String title, String subtitle, String time_tt, String description_tt, String thumb_image, String big_image) {
            this.title = title;
            this.subtitle = subtitle;
            this.timestamp = time_tt;
            this.description = description_tt;
            this.thumbImage = thumb_image;
            this.bigImage = big_image;
        }

        public String getTitle() {
            return title;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getDescription() {
            return description;
        }

        public String getThumbImage() {
            return thumbImage;
        }

        public String getBigImage() {
            return bigImage;
        }
    }

    public static ArrayList<FeedItem> generateFeedItems(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("generateFeedItems - Invalid count:" + count);
        }
        final ArrayList<FeedItem> feedItems = new ArrayList<>(count);
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            feedItems.add(mSampleFeedItems[random.nextInt(mSampleFeedItems.length)]);
        }
        return feedItems;
    }
}
