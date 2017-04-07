package com.inmobi.nativead.sample.utility;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataFetcher {

    private static final String LOG_TAG = DataFetcher.class.getSimpleName();

    public interface OnFetchCompletedListener {
        void onFetchCompleted(String data, String message);
    }

    private String readStream(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public void getFeed(final String urlString, final OnFetchCompletedListener onFetchCompletedListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL(urlString);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    final String data = readStream(in);

                    urlConnection.disconnect();
                    onFetchCompletedListener.onFetchCompleted(data, "");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(LOG_TAG, "Some error occurred while loading feed" + e.toString());
                    onFetchCompletedListener.onFetchCompleted(null, e.getMessage());
                }
            }
        }).start();
    }
}
