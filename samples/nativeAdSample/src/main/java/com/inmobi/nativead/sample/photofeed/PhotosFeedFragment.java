package com.inmobi.nativead.sample.photofeed;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.nativead.sample.DataFetcher;
import com.inmobi.nativead.sample.PlacementId;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotosFeedFragment extends ListFragment implements NativeProvider {

    private static final String TAG = PhotosFeedFragment.class.getSimpleName();
    private static final int MAX_ADS = 50;
    private static final String FALLBACK_IMAGE_URL = "https://s3-ap-southeast-1.amazonaws.com/inmobi-surpriseme/notification/notif2.jpg";


    @NonNull
    private final Handler mHandler = new Handler();
    private Map<PhotosFeedItem, WeakReference<InMobiNative>> mNativeAdMap = new HashMap<>();
    private List<PhotosFeedItem> mItemList = new ArrayList<>();
    private InMobiNative[] mNativeAds = new InMobiNative[MAX_ADS];
    private PhotosAdapter mAdapter;
    private DataFetcher mDataFetcher = new DataFetcher();

    private static final String FEED_URL = "https://ajax.googleapis.com/ajax/services/feed/load?v=1.0&num=30&q=http://rss.nytimes.com/services/xml/rss/nyt/World.xml";
    private static final int[] AD_PLACEMENT_POSITIONS = new int[]{2, 4, 8, 13, 18};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new PhotosAdapter(getActivity(), mItemList, this);
        setListAdapter(mAdapter);
        getPhotos();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void getPhotos() {
        mDataFetcher.getFeed(FEED_URL, new DataFetcher.OnFetchCompletedListener() {
            @Override
            public void onFetchCompleted(final String data, final String message) {
                if (null != data) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            loadPhotos(data);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // if the item at this position is an ad handle this
        // if the item at this position is an ad handle this
        PhotosFeedItem photoItem = mItemList.get(position);
        final WeakReference<InMobiNative> nativeAdRef = mNativeAdMap.get(photoItem);
        if (nativeAdRef != null && nativeAdRef.get() != null) {
            nativeAdRef.get().reportAdClickAndOpenLandingPage(null);
        }
        getListView().setItemChecked(position, true);
    }

    private void loadPhotos(String data) {
        try {
            Log.d(TAG, "loading photos");
            JSONArray feed = new JSONObject(data).
                    getJSONObject("responseData").
                    getJSONObject("feed").
                    getJSONArray("entries");
            for (int i = 0; i < feed.length(); i++) {
                JSONObject item = feed.getJSONObject(i);
                Log.d(TAG, item.toString());
                PhotosFeedItem feedEntry = new PhotosFeedItem();
                try {
                    feedEntry.title = item.getString("title");
                    if (item.isNull("mediaGroups")) {
                        feedEntry.imageUrl = FALLBACK_IMAGE_URL;
                    } else {
                        feedEntry.imageUrl = item.getJSONArray("mediaGroups").getJSONObject(0).getJSONArray("contents").getJSONObject(0).getString("url");
                    }
                    feedEntry.landingUrl = item.getString("link");
                    mItemList.add(feedEntry);
                } catch (JSONException e) {
                    Log.d(TAG, e.toString());
                }
            }
            mAdapter.notifyDataSetChanged();
            placeNativeAds();
        } catch (JSONException e) {
            Log.d(TAG, e.toString());
        }
    }

    private void placeNativeAds() {
        Log.d(TAG, "loading ADS");

        for (int i = 0; i < AD_PLACEMENT_POSITIONS.length; i++) {
            final int position = AD_PLACEMENT_POSITIONS[i];
            InMobiNative nativeAd = new InMobiNative(getActivity(), PlacementId.YOUR_PLACEMENT_ID, new InMobiNative.NativeAdListener() {
                @Override
                public void onAdLoadSucceeded(final InMobiNative inMobiNative) {
                    try {
                        JSONObject content = new JSONObject((String) inMobiNative.getAdContent());
                        Log.d(TAG, "onAdLoadSucceeded" + content.toString());
                        PhotosFeedItem item = new PhotosFeedItem();
                        item.title = content.getString("title");
                        item.landingUrl = content.getString("click_url");
                        item.imageUrl = content.getJSONObject("image_xhdpi").getString("url");
                        mItemList.add(position, item);
                        mNativeAdMap.put(item, new WeakReference<>(inMobiNative));
                        mAdapter.notifyDataSetChanged();
                        Log.d(TAG, "Placed ad unit (" + inMobiNative.hashCode() +
                                ") at position " + position);
                    } catch (JSONException e) {
                        Log.d(TAG, e.toString());
                    }
                }

                @Override
                public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus inMobiAdRequestStatus) {
                    Log.d(TAG, "Failed to load ad. " + inMobiAdRequestStatus.getMessage());
                }

                @Override
                public void onAdDismissed(InMobiNative inMobiNative) {
                    Log.d(TAG, "onAdDismissed photos");

                }

                @Override
                public void onAdDisplayed(InMobiNative inMobiNative) {
                    Log.d(TAG, "onAdDisplayed photos");

                }

                @Override
                public void onUserLeftApplication(InMobiNative inMobiNative) {
                    Log.d(TAG, "onUserLeftApplication photos");

                }
            });
            nativeAd.load();
            mNativeAds[i] = nativeAd;
        }
    }


    @Override
    public void onDetach() {
        mNativeAdMap.clear();
        mItemList.clear();
        super.onDetach();
    }

    @Override
    public WeakReference<InMobiNative> provideInmobiNative(PhotosFeedItem photosFeedItem) {
        return mNativeAdMap == null ? null : mNativeAdMap.get(photosFeedItem);
    }
}
