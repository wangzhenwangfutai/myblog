package com.ziqiang.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ziqiang.model.Article;
import com.ziqiang.model.Category;
import com.ziqiang.model.Friend;
import com.ziqiang.model.Manager;
import com.ziqiang.model.Tag;
import com.ziqiang.service.ArticleService;
import com.ziqiang.service.CategoryService;
import com.ziqiang.service.FriendService;
import com.ziqiang.service.TagService;
import com.ziqiang.util.Constant;
import com.ziqiang.util.JsonUtil;
import com.ziqiang.util.Message;
import com.ziqiang.util.Pager;
import com.ziqiang.util.SuprUtil;



@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private FriendService friendService;
	
	@RequestMapping(value = "/admin/article/list")
	public String list(HttpSession session,ModelMap map) {
		Manager manager = (Manager)session.getAttribute(Constant.USERINFO);
		if (manager == null) {
			return "redirect:/login";
		}
		map.put("manager", manager);
		
		// 栏目列表
		List<Category> categoryList = categoryService.getCategoryList();
		// 标签列表
		List<Tag> tagList = tagService.getTagList();
		map.put("categoryList", categoryList);
		map.put("tagList", tagList);
		return "/admin/article/article_list";
	}
	
	@RequestMapping(value = "/admin/article/load")
	public String loadArticlePageList(HttpSession session,ModelMap map,String param,Pager<Article> pager) throws UnsupportedEncodingException {
		Manager manager = (Manager)session.getAttribute(Constant.USERINFO);
		map.put("manager", manager);
		HashMap<String, Object> paramMap = new HashMap<String,Object>();
		Article article = JsonUtil.fromJson(param, Article.class);
		if(!StringUtils.isEmpty(article.getTitle())){
			article.setTitle(URLDecoder.decode(article.getTitle(), "utf-8"));
		}
		paramMap.put("article", article);
		paramMap.put("manager", manager);
		List<Article> articleList = articleService.getArticleList(paramMap, pager);
		map.put("articleList", articleList);
		map.put("pager", pager);
		return "/admin/article/article_pager_list";
	}
	
	@RequestMapping(value = "/admin/article/addJump")
	public String addJump(HttpSession session,ModelMap map){
		Manager manager = (Manager)session.getAttribute(Constant.USERINFO);
		map.put("manager", manager);
		
		// 栏目列表  链接栏目
		List<Category> categoryList = categoryService.getCategoryList();
		// 标签列表
		List<Tag> tagList = tagService.getTagList();
				
		map.put("categoryList", categoryList);
		map.put("tagList", tagList);
		return "/admin/article/add_article";
	}
	
	


	@RequestMapping(value = "/article/loadPage/{categoryId}/{articleId}")
	public String loadPage(HttpSession session,ModelMap map,@PathVariable String articleId,@PathVariable String categoryId) throws UnsupportedEncodingException {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("categoryId", categoryId);
		paramMap.put("articleId", articleId);
		// 最新的文章列表
		List<Article> articleList = articleService.getLastArticleList(paramMap);

		if(!SuprUtil.isEmptyCollection(articleList)){
			int i = 1;
			for(Article article : articleList){
				// 获取标签
				List<Tag> tList = tagService.getArticleTagList(article.getId());
				article.setTagList(tList);
				if(i == articleList.size()){
					map.put("articleId", article.getId());
				}
				i++;
			}
		}
		map.put("articleList", articleList);
		return "/blog/article/article_pager";
	}
	
	@RequestMapping(value = "/article/html/{id}")
	public String articleDetail(HttpSession session, ModelMap map, @PathVariable String id)
			throws UnsupportedEncodingException {
		// 更新文章的浏览数
		articleService.incrArticleShowCount(id);
		
		// 栏目列表
		List<Category> categoryList = categoryService.getCategoryList();
		// 标签列表
		List<Tag> tagList = tagService.getTagList();
		// 合作伙伴列表
		List<Friend> friendList = friendService.getAllFriendList();
		// 文章详情
		Article article = articleService.getArticleById(Integer.valueOf(id));
		// 获取标签
		List<Tag> tList = tagService.getArticleTagList(Integer.valueOf(id));
		article.setTagList(tList);
		
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("articleId", id);
		param.put("categoryId", article.getCategoryId());
		// 获取上一个文章 下一个文章id
		Article beforeArticle = articleService.getBeforeArticle(param);
		Article nextArticle = articleService.getNextArticle(param);
		
		// 获取相关文章  4个  同类型的随机四个
		List<Article> relationList = articleService.getLastArticleList(param);
		map.put("beforeArticle", beforeArticle);
		map.put("nextArticle", nextArticle);
		map.put("article", article);
		map.put("relationList", relationList);
		map.put("categoryList", categoryList);
		map.put("tagList", tagList);
		map.put("friendList", friendList);
		return "/blog/article/article_detail";
	}
	
	

	/**
	 * 修改文章状态
	 * 
	 * @return
	 */
	@RequestMapping("admin/article/updateStatue")
	public Message<String> updateArticleStatus(Integer id, Integer statue) {
		Message<String> message = new Message<String>();
		message.setResultCode(0);
		message.setResultMsg("修改失败");
		int count = articleService.updateArticleStauts(id, statue);
		if (count > 0) {
			message.setResultCode(1);
			message.setResultMsg("成功");
		}
		return message;
	}
	
}
