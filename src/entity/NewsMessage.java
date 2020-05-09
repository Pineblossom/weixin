package entity;

import java.util.*;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class NewsMessage extends BaseMessage {
	
	@XStreamAlias("ArticleCount")
	private String articleCount;
	
	private List<Article> article = new ArrayList<>();
	public String getArticleCount() {
		return articleCount;
	}
	public void setArticleCount(String articleCount) {
		this.articleCount = articleCount;
	}
	public List<Article> getArticle() {
		return article;
	}
	public void setArticle(List<Article> article) {
		this.article = article;
	}
	public NewsMessage(Map<String, String> requestMap, String articleCount, List<Article> article) {
		super(requestMap);
		this.setMsgType("news");
		this.articleCount = articleCount;
		this.article = article;
	}
	
}
