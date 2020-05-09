package entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Article {
	
	private String title;
	
	private String description;
	
	private String picURL;
	
	private String URL;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicURL() {
		return picURL;
	}
	public void setPicURL(String picURL) {
		this.picURL = picURL;
	}
	public String getURL() {
		return URL;
	}
	public void setURL(String uRL) {
		URL = uRL;
	}
	public Article(String title, String description, String picURL, String uRL) {
		super();
		this.title = title;
		this.description = description;
		this.picURL = picURL;
		URL = uRL;
	}
	
}
