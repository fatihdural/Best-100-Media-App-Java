package com.example.myretrofit;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    private boolean detay;
    private FragmentManager manager;
    private ArrayList<Post> list = new ArrayList<>();
    private ArrayList<Post> nonSortList = new ArrayList<>();
    private Context context;
    private View view;
    private RecyclerView recyclerView;
    private recyclerFragment recyclerFragment;
    private dataSource dataSource;
    private ArrayList<String> favorites = new ArrayList<>();
    private int dbNum = 0;
    private ArrayList<Movie> beginList;

    public MovieAdapter(ArrayList<Post> list, Context context, FragmentManager manager, RecyclerView recyclerView, com.example.myretrofit.recyclerFragment recyclerFragment, ArrayList<Movie> beginList, boolean detay) {
        this.manager = manager;
        this.list = list;
        nonSortList = list;
        this.context = context;
        this.recyclerView = recyclerView;
        this.recyclerFragment = recyclerFragment;
        this.dataSource = new dataSource(context);
        this.beginList = beginList;
        this.detay = detay;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        this.view = view;
//        sqliteClass sqlite = new sqliteClass(view.getContext());
        MovieHolder movieHolder = new MovieHolder(view);
        return movieHolder;
    }

    public dataSource getDataSource(){
        return dataSource;
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieHolder holder, final int position) {
        // Use the provided View Holder on the onCreateViewHolder method to populate the current row on the RecyclerView
        holder.txtName.setText(list.get(position).getName());
        holder.txtId.setText("Producer: " + list.get(position).getDirector());
        Picasso.get().load(list.get(position).getImg()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(holder.imgMovie);

        String s = list.get(position).getName();
        for(int i = 0; i < beginList.size(); i++){
            String a;
            a = beginList.get(i).getMovieName();
            favorites.add(a);
            System.out.println(a);
            if(  a.equals(s) ){
                holder.favoriteButton.setImageResource(R.drawable.favorite_full);
            }
        }


        if ( favorites.contains(  list.get(position).getName() ) )
        {
            holder.favoriteButton.setImageResource(R.drawable.favorite_full);
        }
        else
            holder.favoriteButton.setImageResource(R.drawable.favorite_empt);
        dataSource.open();

        Animation animFadeOut = AnimationUtils.loadAnimation(view.getContext(), R.anim.fadeout);

        Animation animFadeIn = AnimationUtils.loadAnimation(view.getContext(), R.anim.fadein);

        animFadeOut.reset();
        holder.favoriteButton.clearAnimation();
        holder.favoriteButton.startAnimation(animFadeOut);


        animFadeIn.reset();
        holder.favoriteButton.clearAnimation();
        holder.favoriteButton.startAnimation(animFadeIn);

        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = list.get(position).getName();
                Movie movie = new Movie(item);
                ImageView img = view.findViewById(R.id.favoriteButton);
                if( favorites.contains( item ) ){
                    img.setImageResource(R.drawable.favorite_empt);
                    dataSource.deleteMovie( movie.getMovieName() );
                    favorites.remove(item);
                }
                else{
                    img.setImageResource(R.drawable.favorite_full);
                    dataSource.createMovie(movie);
                    favorites.add( item );
                }
            }
        });


        if(detay){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                StringBuffer sb = new StringBuffer();
                                String newline;
                                Document document = Jsoup.connect(list.get(position).getInformation()).get();
                                String inputEncoding = document.outerHtml();
                                Reader inputString = new StringReader(inputEncoding);
                                BufferedReader bufferedReader = new BufferedReader(inputString);
                                while( ( newline = bufferedReader.readLine() ) != null ){
                                    sb.append(newline);
                                    sb.append("\n");
                                }
                                int iend = sb.indexOf("data-test-bidi"); //this finds the first occurrence of "."
                                String subString = "";
                                String info = "";
                                if (iend != -1)
                                {
                                    subString = sb.substring(iend+15); //this will give abc
                                }
                                iend = subString.indexOf("</p>");
                                if(iend != -1){
                                    info = subString.substring(0, iend);
                                }
                                Fragment detayFrag = new detayFragment(manager);
                                Bundle args = new Bundle();
                                args.putString("movieName", list.get(position).getName());
                                args.putString("info", info);
                                args.putString("img", list.get(position).getImg());
                                detayFrag.setArguments(args);
                                FragmentTransaction transaction = manager.beginTransaction();
                                transaction.add(R.id.mainFrame, detayFrag, "detayFrag");
                                transaction.hide(recyclerFragment);
                                transaction.commit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
    // Insert a new item to the RecyclerView on a predefined position
    public void insert(int position, Post data) {
        list.add(position, data);
        notifyItemInserted(position);
    }

    // Remove a RecyclerView item containing a specified Data object
    public void remove(Post data) {
        int position = list.indexOf(data);
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void sortAlfebetik(boolean sort) {
        if( sort ){
            ArrayList<Post> listTemp = new ArrayList<>(list);
            for(int i = 0; i < listTemp.size(); i++){
                for(int j = 0; j < listTemp.size(); j++){
                    if( ( (int) listTemp.get(i).getName().charAt(0) <
                            (int) listTemp.get(j).getName().charAt(0)) )
                    {
                        Post temp = listTemp.get(i);
                        listTemp.set(i, listTemp.get(j));
                        listTemp.set(j, temp);

                    }
                    else if( ( (int) listTemp.get(i).getName().charAt(0) ==
                            (int) listTemp.get(j).getName().charAt(0)) )
                    {
                        if( ( (int) listTemp.get(i).getName().charAt(1) <
                                (int) listTemp.get(j).getName().charAt(1)) )
                        {
                            Post temp = listTemp.get(i);
                            listTemp.set(i, listTemp.get(j));
                            listTemp.set(j, temp);
                        }
                    }
                }
            }
            list = listTemp;
        }
        else{
            list = nonSortList;
        }
        notifyDataSetChanged();
    }
}
