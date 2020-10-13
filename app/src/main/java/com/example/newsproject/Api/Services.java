package com.example.newsproject.Api;
import com.example.newsproject.Api.Model.NewsSourceById.NewsResponseById;
import com.example.newsproject.Api.Model.NewsSoursceResponse.SourcesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
public interface Services {
    @GET("sources")
    Call<SourcesResponse> getAllNews(@Query("apiKey") String apiKey,@Query("language") String language);
    @GET("everything")
    Call<NewsResponseById> getNewsByResourceId(@Query("apiKey") String apiKey, @Query("language") String language, @Query("sources") String sourceId);
    @GET("everything")
    Call<NewsResponseById> getNewsBySearch(@Query("q") String q,@Query("apiKey") String apiKey );
}
