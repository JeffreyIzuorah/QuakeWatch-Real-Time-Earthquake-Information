package com.jeffrey.earthquakeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class FullView extends AppCompatActivity {

    TextView nameView, descriptionView, dateView, magnitudeView, locationView;
    EarthQuakeItem earthQuakeItem = new EarthQuakeItem();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nameView = findViewById(R.id.titleTextView);
        descriptionView = findViewById(R.id.descriptionTextView);
        dateView = findViewById(R.id.dateTextView);
        // Remaking the earthquake object from the values that were just passed from the other page
        Intent intent = getIntent();
        if(intent != null) {
            String name ="" , desc ="", date ="";
            name = intent.getStringExtra("name");
            desc = intent.getStringExtra("description");
            date = intent.getStringExtra("date");
            earthQuakeItem.setDate(date);
            earthQuakeItem.setName(name);
            earthQuakeItem.setDescription(desc);

            nameView.setText(name);
            dateView.setText(date);
            descriptionView.setText(desc);

            // Make the name on the toolbar, the same name as item selected
            getSupportActionBar().setTitle(name);
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // What makes the back button go back to the previous page
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
