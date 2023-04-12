package com.jeffrey.earthquakeapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

class EarthquakeAdapter extends RecyclerView.Adapter<EarthquakeAdapter.EarthquakeViewHolder> {

    private List<EarthQuakeItem> earthquakeList;
    Context context;
    private static final double MAGNITUDE_THRESHOLD = 6.0;

    public EarthquakeAdapter(List<EarthQuakeItem> earthquakeList, Context context) {
        this.earthquakeList = earthquakeList;
        this.context = context;
    }

    @NonNull
    @Override
    public EarthquakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.one_item, parent, false);
        return new EarthquakeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EarthquakeViewHolder holder, int position) {
        EarthQuakeItem earthquake = earthquakeList.get(position);
        holder.title.setText(earthquake.getName());
        holder.date.setText(earthquake.getDate());
        holder.location.setText(earthquake.getLocation());

        double magnitude = earthquake.getMagnitude();
        holder.magnitude.setText(String.valueOf(magnitude));
        if (magnitude < 4.0) {
            holder.magnitude.setTextColor(ContextCompat.getColor(context, R.color.magnitude_green));
        } else if (magnitude < 6.5) {
            holder.magnitude.setTextColor(ContextCompat.getColor(context, R.color.magnitude_green));
        } else {
            holder.magnitude.setTextColor(ContextCompat.getColor(context, R.color.magnitude_red));
        }


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, FullView.class);
                intent.putExtra("name", earthquake.getName());
                intent.putExtra("date", earthquake.getDate());
                intent.putExtra("description", earthquake.getDescription());
                context.startActivity(intent);
                System.out.println("This was just clicked!");
            }
        });
    }


    @Override
    public int getItemCount() {
        return earthquakeList.size();
    }

    public static class EarthquakeViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView date;
        public TextView location;
        public TextView magnitude;
        LinearLayout card;

        public EarthquakeViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            date = itemView.findViewById(R.id.dateTextView);
            location = itemView.findViewById(R.id.locationTextView);
            magnitude = itemView.findViewById(R.id.magnitudeTextView);
            card = itemView.findViewById(R.id.cardItself);
        }
    }
}


