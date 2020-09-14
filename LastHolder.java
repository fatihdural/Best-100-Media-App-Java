package com.example.myretrofit;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class LastHolder extends RecyclerView.ViewHolder  {
    CardView cardView;
    TextView txtName;
    TextView txtId;

    public LastHolder(@NonNull View itemView) {
        super(itemView);
        this.cardView = itemView.findViewById(R.id.cardMovieLast);
        this.txtName = itemView.findViewById(R.id.txtNameLast);
    }
}
