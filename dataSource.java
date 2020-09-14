package com.example.myretrofit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;

public class dataSource implements Serializable{
    private SQLiteDatabase database;
    private sqliteClass myDatabase;
    private boolean favourite;
    dataSource(Context context){
        myDatabase = new sqliteClass(context);
    }
    void open(){
        database = myDatabase.getWritableDatabase();
    }
    public void close(){
        myDatabase.close();
    }
    void createMovie(Movie movie){
        ContentValues val = new ContentValues();
        val.put("moviename", movie.getMovieName());
        database.insert("movies", null, val);
    }
    void deleteMovie(String movieName){
        database.delete("movies","moviename=?",new String[]{movieName});
    }
    public boolean isFavourite(String name){
        SQLiteDatabase db = myDatabase.getReadableDatabase();
        String query = String.format("SELECT * FROM movies WHERE moviename='%s'", name);
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    ArrayList<Movie> createList(){
        String kolonlar[] = {"id", "moviename"};
        Cursor c = database.query("movies", kolonlar,null,null,null
                ,null,null);
        c.moveToFirst();
        ArrayList<Movie> listTemp = new ArrayList<Movie>();
        while( !c.isAfterLast() ){
            String movieName = c.getString(1);
            Movie temp = new Movie(movieName);
            listTemp.add(temp);
            c.moveToNext();
        }
        c.close();
        return listTemp;
    }
}
