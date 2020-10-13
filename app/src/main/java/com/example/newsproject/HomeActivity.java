package com.example.newsproject;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.example.newsproject.Adapter.NewsAdapter;
import com.example.newsproject.Api.Model.NewsSourceById.ArticlesItem;
import com.example.newsproject.Api.Model.NewsSoursceResponse.SourcesItem;
import com.example.newsproject.Base.BaseActivity;
import com.example.newsproject.Repos.NewsRepo;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;
import java.util.List;
public class HomeActivity extends BaseActivity {
TabLayout tabLayout;
RecyclerView recyclerView;
NewsRepo newsRepo;
String lang="en";
NewsAdapter newsAdapter;
RecyclerView.LayoutManager layoutManager;
ShimmerFrameLayout shimmerFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout=findViewById(R.id.tab_layout);
        shimmerFrameLayout=findViewById(R.id.shimmer);

        recyclerView=findViewById(R.id.recycler_view);

        showProgressBar(R.string.loading);
        newsAdapter=new NewsAdapter(null,this);
        layoutManager=new LinearLayoutManager(activity);
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(layoutManager);
        newsRepo=new NewsRepo(lang);
        newsRepo.getNewsSources(onSourcesPreparedListener);//endGetNewsSources()


   newsAdapter.setOnItemClickListener(new NewsAdapter.onItemClickListener() {
       @Override
       public void onItemClick(ArticlesItem articlesItem, int pos) {
        Intent intent=new Intent(HomeActivity.this,NewsDetails.class);
        intent.putExtra("title",articlesItem.getTitle());
           intent.putExtra("desc",articlesItem.getDescription());
           intent.putExtra("image",articlesItem.getUrlToImage());
        startActivity(intent);
       }
   });
   newsAdapter.setOnImageClickListener(new NewsAdapter.onImageClickListener() {
       @Override
       public void onImageClick(ArticlesItem articlesItem, int pos) {
           ImageDialogFragment dialogFragment=new ImageDialogFragment();
           dialogFragment.setImageUrl(articlesItem.getUrlToImage());
           dialogFragment.show(getSupportFragmentManager(),"");

       }
   });

            }//endOnCreate()
   NewsRepo.OnSourcesPreparedListener onSourcesPreparedListener= new NewsRepo.OnSourcesPreparedListener() {
        @Override
        public void onSourcesPrepares(final List<SourcesItem> sourcesItems) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    hideProgressBar();
                    addSourcesToTabLayout(sourcesItems);
                }
            });//endOnSourcesPrepares()


        }
    };

    private void addSourcesToTabLayout(final List<SourcesItem> sourcesItems) {
        if (sourcesItems == null)
            return;
        tabLayout.removeAllTabs();
        for (int i = 0; i < sourcesItems.size(); i++) {
            SourcesItem sourcesItem = sourcesItems.get(i);
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(sourcesItem.getName());

            tabLayout.addTab(tab);

        }//endFor
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                shimmerFrameLayout.startShimmer();
                SourcesItem sourcesItem=  sourcesItems.get(tab.getPosition());

             if(sourcesItem!=null) {

                 newsRepo.getNewsBySourcesId(lang,sourcesItem.getId(),onNewsPreparedListener);
             }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                shimmerFrameLayout.startShimmer();

                SourcesItem sourcesItem=  sourcesItems.get(tab.getPosition());
                if(sourcesItem!=null){
                newsRepo.getNewsBySourcesId(lang,sourcesItem.getId(),onNewsPreparedListener);
            }}
        });
        tabLayout.getTabAt(0).select();
    }//endAddSourcesToTabLayout()

        NewsRepo.OnNewsPreparedListener onNewsPreparedListener=new NewsRepo.OnNewsPreparedListener() {
        @Override
        public void onNewsPrepare(final List<ArticlesItem> articlesItems) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    newsAdapter.changeData(articlesItems);

                }
            });

        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.app_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_view);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               newsAdapter.getFilter().filter(newText);
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


}//endClass
