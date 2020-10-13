package com.example.newsproject.DataBase.DAOs;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.newsproject.Api.Model.NewsSoursceResponse.SourcesItem;
import java.util.List;
@Dao
public interface SourcesDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addSources(List<SourcesItem> items);
    @Query("select * from SourcesItem;")
    List<SourcesItem> getAllSources();
}
