package com.ziqiang.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziqiang.model.Article;


public interface ArticleMapper {

	/**
	 * 根据文章id 删除文章
	 * 
	 * @param id
	 * @return int
	 */
	public int deleteArticle(@Param("id") Integer id);


	/**
	 * 删除文章对应的标签
	 * 
	 * @param articleId
	 * @return int
	 */
	public int deleteArticleTag(@Param("articleId") Integer articleId);

	public int addArticle(Article article);
	

	public void addArticleTag(Article article);
		

	public void addArticleImage(Article article);


	public List<Article> getLastArticleList(HashMap<String, Object> paramMap);


	public String getArticleImageUrl(String id);


	public void incrArticleShowCount(String id);


	public Article getArticleById(@Param("id") Integer id);


	public Article getBeforeArticle(HashMap<String, Object> param);


	public Article getNextArticle(HashMap<String, Object> param);


	public int getArticleCount(HashMap<String, Object> paramMap);


	public List<Article> getArticleList(HashMap<String, Object> paramMap);
	
	public List<Article> getVueAritcleList(HashMap<String, Object> paramMap);

	int insertSelective(Article record);

	int updateByPrimaryKeySelective(Article record);

	int updateStatue(Article record);

	int getAllArticle();
}
