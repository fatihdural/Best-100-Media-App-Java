package com.example.myretrofit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.squareup.picasso.Picasso;

public class detayFragment extends androidx.fragment.app.Fragment {
    private boolean isImageFitToScreen;
    private Fragment fragment;
    private FragmentTransaction transaction;
    private FragmentManager manager;
    detayFragment(FragmentManager manager){
        this.manager = manager;
    }
    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment = this;
        View view = (View) inflater.inflate(R.layout.detay_fragment, container, false);
        Bundle bundle = getArguments();
        final String information = (String) bundle.get("info");
        final String movieName = (String) bundle.get("movieName");
        final String imgUri = (String) bundle.get("img");
        Button button = view.findViewById(R.id.buttonGeri);
        final TextView textDetay = view.findViewById(R.id.detayText);
        TextView textMovie = view.findViewById(R.id.movieName);
        textDetay.setText("     " + information);
        textMovie.setText(movieName);
        final ImageView imageViewDetay = view.findViewById(R.id.imgMovieDetay);
        Picasso.get().load( imgUri ).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(imageViewDetay);
        ImageView imageShare = view.findViewById(R.id.imageButton);
        imageShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "Movie: " +  movieName;
                // String shareSub;
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share the movie"));
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transaction = manager.beginTransaction();
                transaction.show(manager.getFragments().get(0));
                transaction.remove(fragment);
                transaction.commit();
            }
        });

        imageViewDetay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isImageFitToScreen) {
                    isImageFitToScreen=false;
                    imageViewDetay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                }else{
                    isImageFitToScreen=true;
                    imageViewDetay.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    imageViewDetay.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });
        return view;
    }
}
