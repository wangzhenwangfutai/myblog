package com.ziqiang.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.ziqiang.mapper.ArticleMapper;
import com.ziqiang.mapper.TagMapper;
import com.ziqiang.model.Article;
import com.ziqiang.model.Tag;
import com.ziqiang.util.FTPUtil;
import com.ziqiang.util.Pager;

@Service
public class ArticleService {

	private Logger logger = LoggerFactory.getLogger(ArticleService.class);

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private TagMapper tagMapper;

	public int addArticle(Article article) {
		int count = articleMapper.addArticle(article);

		for (Tag tag : article.getTagList()) {
			article.setTagId(tag.getId());
			articleMapper.addArticleTag(article);
		}
		return count;
	}

	/**
	 * 保存文章
	 * 
	 * @param article
	 * @return int
	 */
	public int vueAddArticle(Article article) {
		int id = article.getId();
		// 删除原来的文章
		articleMapper.deleteArticle(id);
		// 删除原来文章对应的tag
		articleMapper.deleteArticleTag(id);
		article.setId(-1);
		// 截取substring
		String htmlContent = article.getHtmlContent();
		if (StringUtils.isNotEmpty(htmlContent)) {
			article.setDescription(htmlContent.substring(0, htmlContent.length() > 300 ? 300 : htmlContent.length()));
		}
		int count = articleMapper.insertSelective(article);
		// 保存标签
		addTags(article);
		return count;

	}

	/**
	 * 
	 * @param file
	 * @param path
	 * @return
	 */
	public String upload(MultipartFile file, String path) {
		String fileName = file.getOriginalFilename();
		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
		logger.info("开始上传文件，上传文件的文件名：{},上传的路径{},新文件名{}", fileName, path, fileExtensionName);

		File fileDir = new File(path);
		if (!fileDir.exists()) {
			fileDir.setWritable(true);
			fileDir.mkdirs();
		}
		File targetFile = new File(path, uploadFileName);
		try {
			file.transferTo(targetFile);
			FTPUtil ftpUtil = new FTPUtil();
			FTPUtil.uploadFile(Lists.newArrayList(targetFile));
			targetFile.delete();
		} catch (IOException e) {
			logger.error("上传文件异常", e);
			return null;
		}

		return targetFile.getName();
	}

	/**
	 * 添加标签
	 * 
	 * @param article
	 * @return int
	 */
	public int addTags(Article article) {
		// 保存标签
		for (Tag tag : article.getTagList()) {
			// 删除原来的标签
			tagMapper.deleteTagByName(tag.getTagName());
			// 保存新标签
			tagMapper.addTag(tag);
			article.setTagId(tag.getId());
			articleMapper.addArticleTag(article);
		}

		return 1;
	}

	/**
	 * 编辑文章
	 * 
	 * @param article
	 * @return int
	 */
	public int editArticle(Article article) {
		// 删除原来的文章
		int m = articleMapper.deleteArticle(article.getId());
		// 删除原来文章对应的tag
		int n = articleMapper.deleteArticleTag(article.getId());

		int count = articleMapper.addArticle(article);

		for (Tag tag : article.getTagList()) {
			article.setTagId(tag.getId());
			articleMapper.addArticleTag(article);
		}

		return count;
	}


	public List<Article> getLastArticleList(HashMap<String, Object> paramMap) {
		List<Article> articleList = articleMapper.getLastArticleList(paramMap);
		return articleList;
	}

	public String getArticleImageUrl(String id) {
		return articleMapper.getArticleImageUrl(id);
		
	}

	public void incrArticleShowCount(String id) {
		articleMapper.incrArticleShowCount(id);
	}

	public Article getArticleById(Integer id) {
		return articleMapper.getArticleById(id);
	}

	/**
	 * 获取文章列表
	 * 
	 * @param id
	 * @return
	 */
	public Article getVueArticleById(Integer id) {
		Article article = articleMapper.getArticleById(id);
		List<Tag> tags = tagMapper.getArticleTagList(id);
		article.setTagList(tags);
		return article;
	}

	/**
	 * 修改博客的状态
	 * 
	 * @return
	 */
	public int updateArticleStauts(Integer id, Integer flag) {
		int count = 0;
		Article article = new Article();
		article.setId(id);
		article.setStatue(flag);
		count = articleMapper.updateStatue(article);
	    return count;
	}


	public Article getBeforeArticle(HashMap<String, Object> param) {
		return articleMapper.getBeforeArticle(param);
	}

	public Article getNextArticle(HashMap<String, Object> param) {
		return articleMapper.getNextArticle(param);
	}

	public List<Article> getArticleList(HashMap<String, Object> paramMap, Pager<Article> pager) {
		List<Article> articleList = null;
		articleList = articleMapper.getVueAritcleList(paramMap);
		return articleList;
	}

	/**
	 * 删除文章
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteArticle(Long[] ids) {
		int i = 0;
		for (Long string : ids) {
			i++;
			articleMapper.deleteArticle(string.intValue());

		}
		return i;
	}

	public int deleteArticleById(Integer id) {
		return articleMapper.deleteArticle(id);
	}

	public int getAllArticle() {

		return articleMapper.getAllArticle();
	}

}
