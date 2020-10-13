package com.example.newsproject.Repos;

import android.app.Application;

import com.example.newsproject.DataBase.MyDataBase;
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MyDataBase.init(this);

    }//endOnCreate()
}//endClass
