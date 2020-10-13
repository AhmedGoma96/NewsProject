package com.example.newsproject.Api.Model.NewsSourceById;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.example.newsproject.Api.Model.NewsSoursceResponse.SourcesItem;
import com.google.gson.annotations.SerializedName;
@Entity(foreignKeys = @ForeignKey(entity = SourcesItem.class,
		parentColumns = "id",
		childColumns = "sourceId",
		onDelete = ForeignKey.CASCADE),indices = {@Index(value = "sourceId",unique = false)})
public class ArticlesItem{
	@SerializedName("publishedAt")
	private String publishedAt;
	@SerializedName("author")
	private String author;
	@SerializedName("urlToImage")
	private String urlToImage;
	@SerializedName("description")
	private String description;
@Ignore
	@SerializedName("source")
	private SourcesItem source;
String sourceId;
String sourceName;
	@SerializedName("title")
	private String title;
@PrimaryKey
@NonNull
	@SerializedName("url")
	private String url;
	@SerializedName("content")
	private String content;
	public void setPublishedAt(String publishedAt){
		this.publishedAt = publishedAt;
	}
	public String getPublishedAt(){
		return publishedAt;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public void setAuthor(String author){
		this.author = author;
	}
	public String getAuthor(){
		return author;
	}
	public void setUrlToImage(String urlToImage){
		this.urlToImage = urlToImage;
	}
	public String getUrlToImage(){
		return urlToImage;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return description;
	}
	public void setSource(SourcesItem source){
		this.source = source;
	}
	public SourcesItem getSource(){
		return source;
	}
	public void setTitle(String title){
		this.title = title;
	}
	public String getTitle(){
		return title;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return url;
	}
	public void setContent(String content){
		this.content = content;
	}
	public String getContent(){
		return content;
	}
	@Override
 	public String toString(){
		return 
			"ArticlesItem{" + 
			"publishedAt = '" + publishedAt + '\'' + 
			",author = '" + author + '\'' + 
			",urlToImage = '" + urlToImage + '\'' + 
			",description = '" + description + '\'' + 
			",source = '" + source + '\'' + 
			",title = '" + title + '\'' + 
			",url = '" + url + '\'' + 
			",content = '" + content + '\'' + 
			"}";
		}
}