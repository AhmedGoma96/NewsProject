package com.example.newsproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsDetails extends AppCompatActivity {
ImageView articleImageDetail;
TextView articleDescDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        articleImageDetail=findViewById(R.id.image_detail);
        articleDescDetail=findViewById(R.id.desc_detail);
        String articleName=getIntent().getStringExtra("title");
        String articleDesc=getIntent().getStringExtra("desc");
        String articleImage=getIntent().getStringExtra("image");
        articleDescDetail.setText(articleDesc);
        Glide.with(this).load(articleImage).into(articleImageDetail);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);



    }
}