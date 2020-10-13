package com.example.newsproject.Api;
import android.util.Log;
import org.jetbrains.annotations.NotNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class ApiManager {
    private static Retrofit retrofitInstance;
    private static Retrofit getInstance(){
        if(retrofitInstance==null){
            HttpLoggingInterceptor loggingInterceptor=new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(@NotNull String s) {
                    Log.e("api",s);
                }
            });
            OkHttpClient client =new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
            retrofitInstance=new Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
 return retrofitInstance;
    }//endGetInstance()
    public static Services getAllNews(){
        return getInstance().create(Services.class);
    }
}//endClass
