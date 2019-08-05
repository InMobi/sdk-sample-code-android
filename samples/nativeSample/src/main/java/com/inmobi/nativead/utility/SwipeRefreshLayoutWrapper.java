package com.inmobi.nativead.utility;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.ViewGroup;

public final class SwipeRefreshLayoutWrapper {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    public interface Listener {
        boolean canChildScrollUp();

        void onRefresh();
    }

    public static SwipeRefreshLayout getInstance(@NonNull Context context,
                                          @NonNull final Listener listener) {
        final SwipeRefreshLayout swipeRefreshLayout = new SwipeRefreshLayout(context) {

            @Override
            public boolean canChildScrollUp() {
                return listener.canChildScrollUp();
            }
        };
        swipeRefreshLayout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_dark,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_green_light);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                HANDLER.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.onRefresh();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
        return swipeRefreshLayout;
    }
}
