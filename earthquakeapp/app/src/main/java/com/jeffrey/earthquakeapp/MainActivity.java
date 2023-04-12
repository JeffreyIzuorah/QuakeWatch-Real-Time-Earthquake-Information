package com.jeffrey.earthquakeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity implements FetchData.CompletionResponse {

    RecyclerView recyclerView;
    EditText searchBox;

    EditText dateSearchBox;

    ProgressBar progressBar;

    Button searchButton;

    private String selectedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

    List<EarthQuakeItem> earthQuakeItems = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBox = findViewById(R.id.searchBox);
        searchButton = findViewById(R.id.searchButton);
        dateSearchBox = findViewById(R.id.dateSearchBox);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doSearch();
            }
        });

        // --- Setting up the list of items
        recyclerView = findViewById(R.id.listOfItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // --- Load data from website
        FetchData.fetchData(this);

    }


    public  void doSearch(){

        String query = searchBox.getText().toString().toLowerCase().trim();
        String dateQuery = dateSearchBox.getText().toString().trim();

        List<EarthQuakeItem> filteredList = new ArrayList<>();
        for (EarthQuakeItem item : earthQuakeItems) {
            if (!query.isEmpty() && item.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
            // Check if the date is not empty
            if (!dateQuery.isEmpty()) {
                SimpleDateFormat sdfIn = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.ENGLISH);
                SimpleDateFormat sdfOut = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
                try {
                    Date itemDate = sdfIn.parse(item.getDate());
                    String itemDateString = sdfOut.format(itemDate);
                    // Check if the formatted date matches the search date
                    if (itemDateString.equals(dateQuery)) {
                        filteredList.add(item);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }





        EarthquakeAdapter adapter = new EarthquakeAdapter(filteredList, this);
        recyclerView.setAdapter(adapter);
    }
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void callback(String response) {
        if (response == null) {
            Log.e(TAG, "Error fetching data");
            return;
        }
        try {
            // Parse the XML and inflate the RecyclerView on the main thread
            runOnUiThread(() -> parseXMLFromStringAndInflate(response));
            progressBar.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(TAG, "Error parsing XML: " + e.getMessage());
        }
    }


    public void parseXMLFromStringAndInflate(String xmlString){
        // This is where we use pull parser to extract the items
        earthQuakeItems = new ArrayList<>();
        if (xmlString == null || xmlString.isEmpty()) {
            // Handle null or empty string
            return;
        }
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));

            EarthQuakeItem earthquakeItem = null;

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                if (eventType == XmlPullParser.START_TAG) {
                    if (tagName.equalsIgnoreCase("item")) {
                        earthquakeItem = new EarthQuakeItem();
                    } else if (earthquakeItem != null) {
                        switch (tagName.toLowerCase()) {
                            case "title":
                                earthquakeItem.setName(parser.nextText());
                                break;
                            case "description":
                                String description = parser.nextText();
                                // Extract magnitude value from the description
                                String magnitudeStr = description.substring(description.lastIndexOf("Magnitude:")+10);
                                double magnitude = Double.parseDouble(magnitudeStr);
                                earthquakeItem.setMagnitude(magnitude);
                                earthquakeItem.setDescription(description);
                                break;
                            case "pubdate":
                                earthquakeItem.setDate(parser.nextText());
                                break;
                            case "geo:lat":
                                earthquakeItem.setLocation(parser.nextText());
                                break;
                        }


                    }
                } else if (eventType == XmlPullParser.END_TAG && tagName.equalsIgnoreCase("item")) {
                    earthQuakeItems.add(earthquakeItem);
                }

                eventType = parser.next();
            }

            // Inflate the recycler view here with the items
            EarthquakeAdapter adapter = new EarthquakeAdapter(earthQuakeItems,this);
            recyclerView.setAdapter(adapter);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}