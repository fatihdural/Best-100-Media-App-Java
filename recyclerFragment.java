package com.example.myretrofit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class recyclerFragment extends Fragment {
    private View view;
    private FragmentManager manager;
    private ProgressDialog dialog;
    private boolean isDialog;
    private MovieAdapter adapter;
    private boolean isSort=true;
    private String type;
    private boolean detay;

    public recyclerFragment(ProgressDialog dialog, String type) {
        this.dialog = dialog;
        this.type = type;
        isDialog = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = (View) inflater.inflate(R.layout.recycler_fragment, container, false);
        this.manager = getFragmentManager();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rss.itunes.apple.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create( JsonPlaceHolderApi.class );
        Call<Post> call=null;
        if( type.equals("Music") ){
            call = jsonPlaceHolderApi.getMusics();
            detay = false;
        }
        else if ( type.equals("Movies") ){
            call = jsonPlaceHolderApi.getMovies();
            detay = true;
        }
        else if( type.equals("Apps") ){
            call = jsonPlaceHolderApi.getApps();
            detay = false;
        }
        else if( type.equals("Books") ){
            call = jsonPlaceHolderApi.getBooks();
            detay = true;
        }
        else if( type.equals("Clips") ){
            call = jsonPlaceHolderApi.getClips();
            detay = false;
        }
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();
                Post feed = post.getFeed();
                ArrayList<Post> results = feed.getResults();

                dataSource dt = new dataSource(view.getContext());
                dt.open();
                ArrayList<Movie> beginList = dt.createList();
                RecyclerView recyclerContacts = view.findViewById(R.id.recylerview);
                adapter = new MovieAdapter(results, getContext(), getFragmentManager(), recyclerContacts, recyclerFragment.this, beginList, detay);

                recyclerContacts.setLayoutManager(new LinearLayoutManager(view.getContext()));
                recyclerContacts.setAdapter(adapter);

                if(isDialog)
                    dialog.dismiss();



                BottomNavigationView showFav = view.findViewById(R.id.bottom_navigation);
                showFav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        if( item.getItemId() == R.id.navigation_fav ){
                            dataSource dt = adapter.getDataSource();
                            ArrayList<Movie> list = dt.createList();
                            Intent i = new Intent(getContext(), favoritesActivity.class);
                            i.putExtra("datasource", (Serializable) list);
                            startActivity(i);
                            return true;
                        }
                        else if( item.getItemId() == R.id.navigation_search ){
                            adapter.sortAlfebetik(isSort);
                            if( isSort )
                                isSort = false;
                            else{
                                isSort = true;
                            }
                        }
                        else if( item.getItemId() == R.id.navigation_main ){
                            Intent i = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            getActivity().finish();
                        }
                        return false;
                    }
                });
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) { }
        });
        return view;
    }
}
