package com.example.newsproject.DataBase;
import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.newsproject.Api.Model.NewsSourceById.ArticlesItem;
import com.example.newsproject.Api.Model.NewsSoursceResponse.SourcesItem;
import com.example.newsproject.DataBase.DAOs.NewsDao;
import com.example.newsproject.DataBase.DAOs.SourcesDAO;
@Database(entities = {SourcesItem.class, ArticlesItem.class},
        version = 3, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    public abstract SourcesDAO sourcesDAO();
    public abstract NewsDao newsDAO();
    private static MyDataBase myDataBase;
    public static void init(Context context){
        if(myDataBase==null){//Create new object
            myDataBase =
                    Room.databaseBuilder(context.getApplicationContext(),
                            MyDataBase.class, "NEWS-DataBase")
                            .fallbackToDestructiveMigration()
                            .build();
        }

    }
    public static MyDataBase getInstance(){
        return myDataBase;
    }

}
