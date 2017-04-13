package com.inmobi.nativead.sample.prefetch;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.nativead.sample.NativeApplication;
import com.inmobi.nativead.sample.newsheadline.FeedAdapter;
import com.inmobi.nativead.sample.newsheadline.NativeProvider;
import com.inmobi.nativead.sample.newsheadline.NewsHeadlinesFragment;
import com.inmobi.nativead.sample.newsheadline.NewsSnippet;
import com.inmobi.nativead.sample.utility.Constants;
import com.inmobi.nativead.sample.utility.DataFetcher;
import com.inmobi.nativead.sample.utility.NativeFetcher;

import android.app.Activity;
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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class NewsHeadlinesPrefetchFragment extends ListFragment implements NativeProvider {

    private static final String TAG = NewsHeadlinesPrefetchFragment.class.getSimpleName();
    private NativeApplication mNativeApplication;
    private NativeFetcher mNativeFetcher;

    @NonNull
    private final Handler mHandler = new Handler();
    private Map<NewsSnippet, WeakReference<InMobiNative>> mNativeAdMap = new HashMap<>();
    private List<NewsSnippet> mItemList = new ArrayList<>();
    private FeedAdapter mAdapter;
    private static final int[] AD_PLACEMENT_POSITIONS = new int[]{2, 4, 8, 13, 18};
    private ConcurrentHashMap<Integer, Boolean> mPostionMap = new ConcurrentHashMap<>(5, 0.9f, 5);
    private ArrayList<InMobiNative> mNativeList = new ArrayList<>();

    private NewsHeadlinesFragment.OnHeadlineSelectedListener mCallback;
    private AtomicInteger mForcedRetry = new AtomicInteger(0);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        for (Integer pos : AD_PLACEMENT_POSITIONS) {
            mPostionMap.put(pos, false);
        }

        Activity activity = getActivity();
        if (null != activity) {
            mAdapter = new FeedAdapter(getActivity(), mItemList, this);
            setListAdapter(mAdapter);
            mCallback = (NewsHeadlinesFragment.OnHeadlineSelectedListener) getActivity();

            mNativeApplication = (NativeApplication) getActivity().getApplication();

            mNativeFetcher = new NativeFetcher() {
                @Override
                public void onFetchSuccess() {
                    placeNativeAds();
                }

                @Override
                public void onFetchFailure() {
                    if (mForcedRetry.getAndIncrement() < 2) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mNativeApplication.fetchNative(mNativeFetcher);
                            }
                        }, 2000);
                    }
                }
            };

            getHeadlines();

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
                    try {
                        JSONObject enclosureObject = item.getJSONObject(Constants.FeedJsonKeys.CONTENT_ENCLOSURE);
                        if (!enclosureObject.isNull(Constants.FeedJsonKeys.CONTENT_LINK)) {
                            feedEntry.imageUrl = item.getJSONObject(Constants.FeedJsonKeys.CONTENT_ENCLOSURE).
                                    getString(Constants.FeedJsonKeys.CONTENT_LINK);
                        } else {
                            feedEntry.imageUrl = Constants.FALLBACK_IMAGE_URL;
                        }
                    } catch (JSONException e) {
                        feedEntry.imageUrl = Constants.FALLBACK_IMAGE_URL;
                    }
                    feedEntry.landingUrl = item.getString(Constants.FeedJsonKeys.CONTENT_LINK);
                    feedEntry.content = item.getString(Constants.FeedJsonKeys.FEED_CONTENT);
                    mItemList.add(feedEntry);
                } catch (JSONException e) {
                    Log.e(TAG, "JSONException", e);
                }
            }

            mAdapter.notifyDataSetChanged();
            Bundle args = getArguments();
            for (int i = 0; i < AD_PLACEMENT_POSITIONS.length; i++) {
                placeNativeAds();
            }
        } catch (JSONException e) {
            Log.e(TAG, "JSONException", e);
        }
    }

    private void placeNativeAds() {
        InMobiNative nativeAd = mNativeApplication.getNative();
        if (null == nativeAd) {
            mNativeApplication.fetchNative(mNativeFetcher);
            return;
        }
        nativeAd.setNativeAdListener(new InMobiNative.NativeAdListener() {
            @Override
            public void onAdLoadSucceeded(final InMobiNative inMobiNative) {
                try {
                    JSONObject content = new JSONObject((String) inMobiNative.getAdContent());
                    Log.e(TAG, "onAdLoadSucceeded" + content.toString());
                    NewsSnippet item = new NewsSnippet();
                    item.title = content.getString(Constants.AdJsonKeys.AD_TITLE);
                    item.landingUrl = content.getString(Constants.AdJsonKeys.AD_CLICK_URL);
                    item.imageUrl = content.getJSONObject(Constants.AdJsonKeys.AD_IMAGE_OBJECT).
                            getString(Constants.AdJsonKeys.AD_IMAGE_URL);

                    int mPosition = getEmptyPosition();
                    if (mPosition > mItemList.size()) {
                        mPosition = mItemList.size();
                    }

                    Log.d(TAG, "Strand loaded at position " + mPosition);
                    mPostionMap.put(mPosition, true);
                    mItemList.add(mPosition, item);
                    mNativeAdMap.put(item, new WeakReference<>(inMobiNative));
                    mAdapter.notifyDataSetChanged();
                    Log.d(TAG, "Placed ad unit (" + inMobiNative.hashCode() +
                            ") at position " + mPosition);
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
        //Providing activity context to show ad
        nativeAd.load(getActivity());
        mNativeList.add(nativeAd);
    }


    @Override
    public void onDetach() {
        mCallback = null;
        mNativeAdMap.clear();
        mItemList.clear();
        mPostionMap.clear();
        mForcedRetry.set(0);
        mNativeList.clear();
        super.onDetach();
    }

    private int getEmptyPosition() {
        int min = Integer.MAX_VALUE;
        for (Map.Entry<Integer, Boolean> entry : mPostionMap.entrySet()) {
            int pos = entry.getKey();
            boolean filled = entry.getValue();
            if (!filled) {
                if (min > pos) {
                    min = pos;
                }
            }
        }
        return min;
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