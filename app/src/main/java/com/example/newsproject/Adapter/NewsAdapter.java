package com.example.newsproject.Adapter;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.newsproject.Api.Model.NewsSourceById.ArticlesItem;
import com.example.newsproject.R;
import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.github.marlonlom.utilities.timeago.TimeAgoMessages;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements Filterable {
     List<ArticlesItem> articlesItemList;
    List<ArticlesItem>   articlesItemListAll;
    onItemClickListener onItemClickListener;
    onImageClickListener onImageClickListener;
    Context context;
    public void setOnItemClickListener(NewsAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnImageClickListener(NewsAdapter.onImageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public NewsAdapter(List<ArticlesItem> articlesItemList, Context context) {
        this.articlesItemList = articlesItemList;
        this.context=context;

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_news,parent,false);

        return new ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
                final ArticlesItem articlesItem=articlesItemList.get(position);
                if(articlesItem.getSource()==null)
                    holder.source_name.setText(articlesItem.getSourceName());
                else
                holder.source_name.setText(articlesItem.getSource().getName());
                holder.title.setText(articlesItem.getTitle());
        String moment_ago=articlesItem.getPublishedAt();
        String text="";
        Locale LocaleBylanguageTag = Locale.forLanguageTag("ar");
        TimeAgoMessages messages = new TimeAgoMessages.Builder().withLocale(LocaleBylanguageTag).build();


        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date d = simpleDateFormat.parse(moment_ago);
            long milliseconds = d.getTime();
          text  = TimeAgo.using(milliseconds,messages);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        holder.date.setText(text);
                Glide.with(holder.itemView).load(articlesItem.getUrlToImage()).into(holder.image);
                holder.title.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onItemClickListener!=null){
                            onItemClickListener.onItemClick(articlesItem,position);
                        }
                    }
                });
                holder.image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onImageClickListener!=null){
                            onImageClickListener.onImageClick(articlesItem,position);
                        }
                    }
                });

    }
    @Override
    public int getItemCount() {
        if(articlesItemList==null)return 0;
        return articlesItemList.size();
    }
    public void changeData(List<ArticlesItem> articlesItems){
        articlesItemList=articlesItems;
        articlesItemListAll  = new ArrayList<>();
        articlesItemListAll.addAll(articlesItemList);
        notifyDataSetChanged();

    }
    @Override
    public Filter getFilter() {
        return myFilter;
    }
    Filter myFilter = new Filter() {

        //Automatic on background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<ArticlesItem> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(articlesItemListAll);
            } else {
                for (ArticlesItem articlesItem: articlesItemListAll) {
                    if (articlesItem.getTitle().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                        filteredList.add(articlesItem);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        //Automatic on UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            articlesItemList.clear();
            articlesItemList.addAll((Collection<? extends ArticlesItem>) filterResults.values);
            notifyDataSetChanged();
        }
    };



    public class ViewHolder extends RecyclerView.ViewHolder {
ImageView image;
TextView title,date,source_name;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           image=itemView.findViewById(R.id.image);
           title=itemView.findViewById(R.id.title_name);
           date=itemView.findViewById(R.id.date);
           source_name=itemView.findViewById(R.id.source_name);

       }
   }
  public interface onItemClickListener{
        public void onItemClick(ArticlesItem articlesItem,int pos);

   }
   public interface onImageClickListener{
       public void onImageClick(ArticlesItem articlesItem,int pos);
   }




}//endClass
