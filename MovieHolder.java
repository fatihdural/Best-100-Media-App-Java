package com.example.myretrofit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MovieHolder  extends RecyclerView.ViewHolder  {
    CardView cardView;
    TextView txtName;
    TextView txtId;
    ImageView imgMovie;
    ImageView favoriteButton;

    public MovieHolder(@NonNull View itemView) {
        super(itemView);
        this.cardView = itemView.findViewById(R.id.cardMovie);
        this.txtName = itemView.findViewById(R.id.txtName);
        this.txtId = itemView.findViewById(R.id.txtId);
        this.imgMovie = itemView.findViewById(R.id.imgMovie);
        this.favoriteButton = itemView.findViewById(R.id.favoriteButton);
    }
}
