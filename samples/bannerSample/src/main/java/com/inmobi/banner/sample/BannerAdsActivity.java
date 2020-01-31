package com.inmobi.banner.sample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiBanner;
import com.inmobi.ads.exceptions.SdkNotInitializedException;
import com.inmobi.ads.listeners.BannerAdEventListener;
import com.inmobi.banner.PlacementId;
import com.inmobi.banner.utility.Constants;
import com.inmobi.banner.utility.DataFetcher;
import com.inmobi.banner.utility.NewsSnippet;
import com.inmobi.sdk.InMobiSdk;
import com.inmobi.unification.sdk.InitializationStatus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.inmobi.banner.utility.Constants.BANNER_HEIGHT;
import static com.inmobi.banner.utility.Constants.BANNER_WIDTH;
import static com.inmobi.banner.utility.Constants.FALLBACK_IMAGE_URL;

public class BannerAdsActivity extends AppCompatActivity {

    private static final String TAG = BannerAdsActivity.class.getSimpleName();

    private InMobiBanner mBannerAd;
    private ListView mNewsListView;

    @NonNull
    private final Handler mHandler = new Handler();
    private List<NewsSnippet> mItemList = new ArrayList<>();
    private NewsFeedAdapter mAdapter;

    interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }

    private OnHeadlineSelectedListener mCallback = new OnHeadlineSelectedListener() {
        @Override
        public void onArticleSelected(int position) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_banner_ads);

        setupListView();
        getHeadlines();
        setupBannerAd();
    }

    private void setupBannerAd() {
        try {
            mBannerAd = new InMobiBanner(BannerAdsActivity.this, PlacementId.YOUR_PLACEMENT_ID);
            RelativeLayout adContainer = (RelativeLayout) findViewById(R.id.ad_container);
            mBannerAd.setAnimationType(InMobiBanner.AnimationType.ROTATE_HORIZONTAL_AXIS);
            mBannerAd.setListener(new BannerAdEventListener() {
                @Override
                public void onAdLoadSucceeded(InMobiBanner inMobiBanner) {
                    super.onAdLoadSucceeded(inMobiBanner);
                    Log.d(TAG, "onAdLoadSucceeded");
                }

                @Override
                public void onAdLoadFailed(InMobiBanner inMobiBanner, InMobiAdRequestStatus inMobiAdRequestStatus) {
                    super.onAdLoadFailed(inMobiBanner, inMobiAdRequestStatus);
                    Log.d(TAG, "Banner ad failed to load with error: " +
                            inMobiAdRequestStatus.getMessage());
                }

                @Override
                public void onAdClicked(InMobiBanner inMobiBanner, Map<Object, Object> map) {
                    super.onAdClicked(inMobiBanner, map);
                    Log.d(TAG, "onAdClicked");
                }

                @Override
                public void onAdDisplayed(InMobiBanner inMobiBanner) {
                    super.onAdDisplayed(inMobiBanner);
                    Log.d(TAG, "onAdDisplayed");
                }

                @Override
                public void onAdDismissed(InMobiBanner inMobiBanner) {
                    super.onAdDismissed(inMobiBanner);
                    Log.d(TAG, "onAdDismissed");
                }

                @Override
                public void onUserLeftApplication(InMobiBanner inMobiBanner) {
                    super.onUserLeftApplication(inMobiBanner);
                    Log.d(TAG, "onUserLeftApplication");
                }

                @Override
                public void onRewardsUnlocked(InMobiBanner inMobiBanner, Map<Object, Object> map) {
                    super.onRewardsUnlocked(inMobiBanner, map);
                    Log.d(TAG, "onRewardsUnlocked");
                }
            });
            setBannerLayoutParams();
            adContainer.addView(mBannerAd);
            mBannerAd.load();
        } catch (SdkNotInitializedException e) {
            Log.e(TAG, "InMobiBanner Object can't be created, InMobi SDK should be initialized successfully" +
                    "Check logs for more information");
            Toast.makeText(this, "Problem creating InMobiBanner Object," +
                    " Check logs for more information", Toast.LENGTH_LONG).show();
        }
    }

    private void setBannerLayoutParams() {
        int width = toPixelUnits(BANNER_WIDTH);
        int height = toPixelUnits(BANNER_HEIGHT);
        RelativeLayout.LayoutParams bannerLayoutParams = new RelativeLayout.LayoutParams(width, height);
        bannerLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        bannerLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        mBannerAd.setLayoutParams(bannerLayoutParams);
    }

    private int toPixelUnits(int dipUnit) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dipUnit * density);
    }

    private void setupListView() {
        mNewsListView = (ListView) findViewById(R.id.lvNewsContainer);
        mAdapter = new NewsFeedAdapter(this, mItemList);
        mNewsListView.setAdapter(mAdapter);
        mNewsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, final long id) {
                AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(BannerAdsActivity.this);
                confirmationDialog.setTitle("Delete Item?");
                confirmationDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewsSnippet newsSnippet = mItemList.get(position);
                        mItemList.remove(newsSnippet);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                confirmationDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                confirmationDialog.show();
                return true;
            }
        });

        mNewsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallback.onArticleSelected(position);
                mNewsListView.setItemChecked(position, true);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getHeadlines() {
        new DataFetcher().getFeed(Constants.FEED_URL, new DataFetcher.OnFetchCompletedListener() {
            @Override
            public void onFetchCompleted(@Nullable final String data, @Nullable final String message) {
                if (null != data) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadHeadlines(data);
                        }
                    });
                }
            }
        });
    }

    private void loadHeadlines(String data) {
        try {
            JSONArray feed = new JSONObject(data).
                    getJSONArray(Constants.FeedJsonKeys.FEED_LIST);
            for (int i = 0; i < feed.length(); i++) {
                JSONObject item = feed.getJSONObject(i);
                Log.v(TAG, item.toString());
                NewsSnippet feedEntry = new NewsSnippet();
                try {
                    feedEntry.title = item.getString(Constants.FeedJsonKeys.CONTENT_TITLE);
                    JSONObject enclosureObject = item.getJSONObject(Constants.FeedJsonKeys.CONTENT_ENCLOSURE);
                    if (!enclosureObject.isNull(Constants.FeedJsonKeys.CONTENT_LINK)) {
                        feedEntry.imageUrl = item.getJSONObject(Constants.FeedJsonKeys.CONTENT_ENCLOSURE).
                                getString(Constants.FeedJsonKeys.CONTENT_LINK);
                    } else {
                        feedEntry.imageUrl = FALLBACK_IMAGE_URL;
                    }
                    feedEntry.landingUrl = item.getString(Constants.FeedJsonKeys.CONTENT_LINK);
                    feedEntry.content = item.getString(Constants.FeedJsonKeys.FEED_CONTENT);
                    feedEntry.isSponsored = false;
                    mItemList.add(feedEntry);
                } catch (JSONException e) {
                    Log.d(TAG, e.toString());
                }
            }
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            Log.d(TAG, "JSONException for loadHeadlines", e);
        }
    }
}
