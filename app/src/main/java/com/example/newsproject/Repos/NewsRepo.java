package com.example.newsproject.Repos;
import com.example.newsproject.Api.ApiManager;
import com.example.newsproject.Api.Model.NewsSourceById.ArticlesItem;
import com.example.newsproject.Api.Model.NewsSourceById.NewsResponseById;
import com.example.newsproject.Api.Model.NewsSoursceResponse.SourcesItem;
import com.example.newsproject.Api.Model.NewsSoursceResponse.SourcesResponse;
import com.example.newsproject.DataBase.MyDataBase;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class NewsRepo {
List<SourcesItem> sourcesItemList;
String lang;
    public NewsRepo(String lang) {
        this.lang = lang;
        sourcesItemList=new ArrayList<>();
    }//endConstructor
    private String APIKEy="7106cb8572474d5ba4cf9a3dc6c808af";
    public void getNewsSources(final OnSourcesPreparedListener onSourcesPreparedListener){
        ApiManager.getAllNews().getAllNews(APIKEy,lang).enqueue(new Callback<SourcesResponse>() {
            @Override
            public void onResponse(Call<SourcesResponse> call, Response<SourcesResponse> response) {
             if(response.isSuccessful()&&"ok".equals(response.body().getStatus())){
              sourcesItemList=response.body().getSources();
              if (onSourcesPreparedListener!=null)
                  onSourcesPreparedListener.onSourcesPrepares(sourcesItemList);
               new InsertSourcesIntoDataBase(response.body().getSources()).start();

            }//endIf
            }//endOnResponse()

            @Override
            public void onFailure(Call<SourcesResponse> call, Throwable t) {
          new GetSourcesFromDB(onSourcesPreparedListener).start();
            }//endOnFailure
        });//endInterface

    }//endGetNewsSources()
    public void getNewsBySourcesId(String lang, final String sourceId, final OnNewsPreparedListener onNewsPreparedListener){

        ApiManager.getAllNews().getNewsByResourceId(APIKEy,lang,sourceId).enqueue(new Callback<NewsResponseById>() {
     @Override
     public void onResponse(Call<NewsResponseById> call, Response<NewsResponseById> response) {

                 onNewsPreparedListener.onNewsPrepare(response.body().getArticles());
                 new InsertNewsIntoDataBase(response.body().getArticles()).start();



     }//endOnResponse()

     @Override
     public void onFailure(Call<NewsResponseById> call, Throwable t) {
         new GetNewsFromDataBase(sourceId,onNewsPreparedListener).start();

     }//endOnFailure()
 });//endInterface
    }//endGetNewsBySourceId()
    public interface OnSourcesPreparedListener{
        public void onSourcesPrepares(List<SourcesItem> sourcesItems);
    }//endInterface
    public interface OnNewsPreparedListener{
        public void onNewsPrepare(List<ArticlesItem> articlesItems);
    }//endInterface
    class InsertSourcesIntoDataBase extends Thread{
    List<SourcesItem> sourcesItemList;
    public InsertSourcesIntoDataBase( List<SourcesItem> sourcesItemList){
        this.sourcesItemList=sourcesItemList;
    }//endConstructor
    public void run(){
        MyDataBase.getInstance().sourcesDAO().addSources(sourcesItemList);
    }//endRun()

}//endInnerClass
    class GetSourcesFromDB extends Thread{
        OnSourcesPreparedListener onSourcesPreparedListener;
        public GetSourcesFromDB( OnSourcesPreparedListener onSourcesPreparedListener){
            this.onSourcesPreparedListener=onSourcesPreparedListener;
        }//endConstructor

        public void run(){
       List<SourcesItem>sourcesItemList=  MyDataBase.getInstance().sourcesDAO().getAllSources();
       onSourcesPreparedListener.onSourcesPrepares(sourcesItemList);
        }//endRun()

    }//endInnerClass
    class InsertNewsIntoDataBase extends Thread{
        List<ArticlesItem> articleItemList;
        public InsertNewsIntoDataBase( List<ArticlesItem> articlesItems){
            this.articleItemList=articlesItems;
        }//endConstructor
        public void run(){
            for(int i=0;i<articleItemList.size();i++)
            {
                ArticlesItem articlesItem= articleItemList.get(i);
                articlesItem.setSourceId(articlesItem.getSource().getId());
                articlesItem.setSourceName(articlesItem.getSource().getName());
            }
            MyDataBase.getInstance().newsDAO().inserNewList(articleItemList);
        }//endRun()

    }//endInnerClass
    class GetNewsFromDataBase extends Thread{
        OnNewsPreparedListener onNewsPreparedListener;
        String sourceId;
        public GetNewsFromDataBase(String sourceId,OnNewsPreparedListener onNewsPreparedListener){
            this.onNewsPreparedListener=onNewsPreparedListener;
            this.sourceId=sourceId;
        }
        public void run(){
            List<ArticlesItem> list=MyDataBase.getInstance().newsDAO().getArticlesItem(sourceId);
            onNewsPreparedListener.onNewsPrepare(list);
        }
    }


}//endClass
