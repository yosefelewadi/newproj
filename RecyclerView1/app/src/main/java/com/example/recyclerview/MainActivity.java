package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    //vars
    private ArrayList<String>mNames=new ArrayList<>();
    private ArrayList<String>mMovieDates=new ArrayList<>();
    private ArrayList<String>mMovieTypes=new ArrayList<>();
    private ArrayList<String>mImageUrls=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started.");
        initRecyclerView();
        initImageBitmaps();
    }
    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing Bitmaps");
        mImageUrls.add("https://7ifz.com/_/201606/10-of-the-oddest-stories-about-pizza-2.jpg");
        mNames.add("Pizza");
        mMovieDates.add("20$");
        mMovieTypes.add("Sandwich");
        mImageUrls.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNVJ2-Y_3wEvQIhh8jGONh6kdmRTj_blnuT3Ece8Ie65sUTMwm&s");
        mNames.add("porger");
        mMovieDates.add("30$");
        mMovieTypes.add("Sandwich");
        mImageUrls.add("https://img-global.cpcdn.com/recipes/208240/751x532cq70/%D8%A7%D9%84%D8%B5%D9%88%D8%B1%D8%A9-%D8%A7%D9%84%D8%B1%D8%A6%D9%8A%D8%B3%D9%8A%D8%A9-%D9%84%D9%88%D8%B5%D9%81%D8%A9%D8%B4%D8%A7%D9%88%D8%B1%D9%85%D8%A7-%D8%AF%D8%AC%D8%A7%D8%AC-%D9%85%D9%86%D8%A7%D9%84-%D8%A7%D9%84%D8%B9%D8%A7%D9%84%D9%85.jpg");
        mNames.add("Shawarma");
        mMovieDates.add("15$");
        mMovieTypes.add("Sandwich");
        mImageUrls.add("https://img.atyabtabkha.com/jW-No9EXowsO72W0yl2Yi6sL0HQ=/640x360/https://harmony-assets-live.s3.amazonaws.com/image_source/37/f9/37f94929b37948514c26e0b263145ccd3c44d833.jpg");
        mNames.add("kapab");
        mMovieDates.add("40$");
        mMovieTypes.add("Sandwich");
        mImageUrls.add("https://ccute.cc/wp-content/uploads/2018/06/1816-9.jpg");
        mNames.add("kik");
        mMovieDates.add("10$");
        mMovieTypes.add("Sandwich");
        mImageUrls.add("https://www.mexatk.com/wp-content/uploads/2017/07/%D8%B5%D9%88%D8%B1-%D9%83%D9%8A%D9%83-%D8%A7%D8%B9%D9%8A%D8%A7%D8%AF-%D9%85%D9%8A%D9%84%D8%A7%D8%AF-1-348x450.jpg");
        mNames.add("fish");
        mMovieDates.add("60$");
        mMovieTypes.add("Sandwich");
        mImageUrls.add("https://rqeeqa.com/wp-content/uploads/2015/12/%D8%B5%D9%88%D8%B1-%D9%83%D9%8A%D9%83-%D8%B9%D9%8A%D8%AF-%D9%85%D9%8A%D9%84%D8%A7%D8%AF-6.jpg");
        mNames.add("kick");
        mMovieDates.add("50$");
        mMovieTypes.add("Sandwich");


        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init Recyclerview.");
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,mNames,mImageUrls,mMovieDates,mMovieTypes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
