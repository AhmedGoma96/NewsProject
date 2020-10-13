package com.example.newsproject.DataBase.DAOs;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.newsproject.Api.Model.NewsSourceById.ArticlesItem;
import java.util.List;
@Dao
public interface NewsDao {
   @Insert(onConflict = OnConflictStrategy.REPLACE)
   public void inserNewList (List<ArticlesItem> artticleItems);
   @Query("select * from ArticlesItem where sourceId=:sourceId ")
   List<ArticlesItem> getArticlesItem(String sourceId);
}
