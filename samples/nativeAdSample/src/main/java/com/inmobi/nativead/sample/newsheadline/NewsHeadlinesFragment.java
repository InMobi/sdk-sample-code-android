package com.inmobi.nativead.sample.newsheadline;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.nativead.sample.DataFetcher;
import com.inmobi.nativead.sample.PlacementId;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsHeadlinesFragment extends ListFragment implements NativeProvider {

    public static final String ARGS_PLACE_NATIVE_ADS = "should_place_native_ads";

    private static final String TAG = NewsHeadlinesFragment.class.getSimpleName();
    private static final int MAX_ADS = 50;

    @NonNull
    private final Handler mHandler = new Handler();
    private Map<NewsSnippet, WeakReference<InMobiNative>> mNativeAdMap = new HashMap<>();
    private List<NewsSnippet> mItemList = new ArrayList<>();
    private InMobiNative[] mNativeAds = new InMobiNative[MAX_ADS];
    private FeedAdapter mAdapter;

    private static final String FEED_URL = "https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=30&q=http://rss.nytimes.com/services/xml/rss/nyt/World.xml";
    private static final String FALLBACK_IMAGE_URL = "https://s3-ap-southeast-1.amazonaws.com/inmobi-surpriseme/notification/notif2.jpg";
    private static final int[] AD_PLACEMENT_POSITIONS = new int[]{2, 4, 8, 13, 18};

    private OnHeadlineSelectedListener mCallback;


    public interface OnHeadlineSelectedListener {
        void onArticleSelected(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new FeedAdapter(getActivity(), mItemList, this);
        setListAdapter(mAdapter);
        mCallback = (OnHeadlineSelectedListener) getActivity();
        getHeadlines();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void getHeadlines() {
        new DataFetcher().getFeed(FEED_URL, new DataFetcher.OnFetchCompletedListener() {
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int position, final long id) {
                AlertDialog.Builder confirmationDialog = new AlertDialog.Builder(getActivity());
                confirmationDialog.setTitle("Delete Item?");

                confirmationDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewsSnippet newsSnippet = mItemList.get(position);
                        mItemList.remove(newsSnippet);
                        WeakReference<InMobiNative> nativeAdRef = mNativeAdMap.remove(newsSnippet);
                        if (nativeAdRef != null) {
                            InMobiNative nativeAd = nativeAdRef.get();
                            if (nativeAd != null) {
                                InMobiNative.unbind(view);
                            }
                        }
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
    }

    private void loadHeadlines(String data) {
        try {
            JSONArray feed = new JSONObject(data).
                    getJSONObject("responseData").
                    getJSONObject("feed").
                    getJSONArray("entries");
            for (int i = 0; i < feed.length(); i++) {
                JSONObject item = feed.getJSONObject(i);
                Log.v(TAG, item.toString());
                NewsSnippet feedEntry = new NewsSnippet();
                try {
                    feedEntry.title = item.getString("title");
                    if (item.isNull("mediaGroups")) {
                        feedEntry.imageUrl = FALLBACK_IMAGE_URL;
                    } else {
                        feedEntry.imageUrl = item.getJSONArray("mediaGroups").getJSONObject(0).getJSONArray("contents").getJSONObject(0).getString("url");
                    }
                    feedEntry.landingUrl = item.getString("link");
                    feedEntry.content = item.getString("contentSnippet");
                    mItemList.add(feedEntry);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException", e);
                }
            }

            mAdapter.notifyDataSetChanged();
            Bundle args = getArguments();
            boolean shouldPlaceNativeAds = args.getBoolean(ARGS_PLACE_NATIVE_ADS, true);
            if (shouldPlaceNativeAds) {
                placeNativeAds();
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
        }
    }

    private void placeNativeAds() {
        for (int i = 0; i < AD_PLACEMENT_POSITIONS.length; i++) {
            final int position = AD_PLACEMENT_POSITIONS[i];
            InMobiNative nativeAd = new InMobiNative(getActivity(), PlacementId.YOUR_PLACEMENT_ID, new InMobiNative.NativeAdListener() {
                @Override
                public void onAdLoadSucceeded(final InMobiNative inMobiNative) {
                    try {
                        JSONObject content = new JSONObject((String) inMobiNative.getAdContent());
                        Log.e(TAG, "onAdLoadSucceeded" + content.toString());
                        NewsSnippet item = new NewsSnippet();
                        item.title = content.getString("title");
                        item.landingUrl = content.getString("click_url");
                        item.imageUrl = content.getJSONObject("image_xhdpi").getString("url");
                        mItemList.add(position, item);
                        mNativeAdMap.put(item, new WeakReference<>(inMobiNative));
                        mAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Placed ad unit (" + inMobiNative.hashCode() +
                                ") at position " + position);
                    } catch (JSONException e) {
                        Log.e(TAG, e.toString());
                    }
                }

                @Override
                public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus inMobiAdRequestStatus) {
                    Log.e(TAG, "Failed to load ad. " + inMobiAdRequestStatus.getMessage());
                }

                @Override
                public void onAdDismissed(InMobiNative inMobiNative) {

                }

                @Override
                public void onAdDisplayed(InMobiNative inMobiNative) {

                }

                @Override
                public void onUserLeftApplication(InMobiNative inMobiNative) {

                }
            });
            nativeAd.load();
            mNativeAds[i] = nativeAd;
        }
    }


    @Override
    public void onDetach() {
        mCallback = null;
        mNativeAdMap.clear();
        mItemList.clear();
        super.onDetach();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // if the item at this position is an ad handle this
        NewsSnippet newsSnippet = mItemList.get(position);
        final WeakReference<InMobiNative> nativeAdRef = mNativeAdMap.get(newsSnippet);
        if (nativeAdRef != null && nativeAdRef.get() != null) {
            nativeAdRef.get().reportAdClickAndOpenLandingPage(null);
        }
        mCallback.onArticleSelected(position);
        getListView().setItemChecked(position, true);
    }

    @Override
    public WeakReference<InMobiNative> provideInmobiNative(NewsSnippet newsSnippet) {
        return mNativeAdMap == null ? null : mNativeAdMap.get(newsSnippet);
    }
}
