package com.example.myretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.ArrayList;

public class favoritesActivity extends AppCompatActivity implements Serializable {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        ArrayList<Movie> list = (ArrayList<Movie>) getIntent().getSerializableExtra("datasource");
        RecyclerView recyclerView = findViewById(R.id.lastRecycler);
        LastAdapter lastAdap = new LastAdapter( list, this );
        recyclerView.setAdapter(lastAdap);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button bt = findViewById(R.id.turnBackFromFav);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
