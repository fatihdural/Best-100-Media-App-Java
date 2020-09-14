package com.example.myretrofit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.security.AccessControlContext;
import java.util.ArrayList;

public class LastAdapter extends RecyclerView.Adapter {
    private ArrayList<Movie> list;
    private LayoutInflater inflater;
    LastAdapter(ArrayList<Movie> list, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.list = list;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_favourites, parent, false);
        return new LastHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holde, int position) {
        LastHolder holder = (LastHolder) holde;
        holder.txtName.setText("Media: " + list.get(position).getMovieName());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
