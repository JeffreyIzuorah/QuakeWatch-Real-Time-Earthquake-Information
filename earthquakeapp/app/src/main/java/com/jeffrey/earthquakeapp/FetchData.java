package com.jeffrey.earthquakeapp;

import android.view.View;
import android.widget.ProgressBar;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchData {

    public static void fetchData( final CompletionResponse onComplete) {
//        progressBar.setVisibility(View.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection urlConnection = null;

                try {
                    URL url = new URL("http://quakes.bgs.ac.uk/feeds/WorldSeismology.xml" );
                    urlConnection = (HttpURLConnection) url.openConnection();

                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream()));
                    String line;

                    final StringBuilder sb = new StringBuilder();
                    while ((line = rd.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    onComplete.callback(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                    onComplete.callback(null);
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

//                progressBar.setVisibility(View.GONE);
            }
        }).start();
    }

    interface CompletionResponse {
        void callback(String response);
    }
}
