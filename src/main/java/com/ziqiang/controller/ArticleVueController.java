package com.ziqiang.controller;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ziqiang.model.Article;
import com.ziqiang.model.Result;
import com.ziqiang.model.Tag;
import com.ziqiang.service.ArticleService;
import com.ziqiang.service.CategoryService;
import com.ziqiang.service.FriendService;
import com.ziqiang.service.TagService;
import com.ziqiang.util.Pager;



@Controller
public class ArticleVueController {


	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private FriendService friendService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	@RequestMapping(value = "/vue/article/load", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> loadArticlePageList(String state,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "count", defaultValue = "6") Integer count, String keywords)
			throws UnsupportedEncodingException {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		HashMap<String, Object> paramMap = new HashMap<String,Object>();
		Pager<Article> pager = new Pager<Article>();
		Article article = new Article();
		article.setTitle(keywords);
		article.setStatue(Integer.valueOf(state));
		paramMap.put("article", article);
		Integer start = (page - 1) * count;
		Integer limit = page * count <= count ? page * count : count;
		paramMap.put("start", start);
		paramMap.put("limit", limit);
		Integer count1 = articleService.getAllArticle();
		List<Article> articleList = articleService.getArticleList(paramMap, pager);
		returnMap.put("articles", articleList);
		returnMap.put("totalCount", count1);
		return returnMap;
	}

	@RequestMapping(value = "/vue/add/article", method = RequestMethod.POST)
	@ResponseBody
	public Result addArticle(Article article, String[] dynamicTags) {
		List<Tag> tagList = new ArrayList<Tag>();
		if (dynamicTags != null && dynamicTags.length > 0) {
			for (String tag : dynamicTags) {
				Tag tagg = new Tag();
				tagg.setTagName(tag);
				tagList.add(tagg);
			}
		}
		article.setTagList(tagList);
		int count = articleService.vueAddArticle(article);
		Result result = new Result();
		if (count > 0) {
			result.setResultCode("1");
			result.setObject(article);
		} else {
			result.setResultCode("0");
			result.setErrorInfo("保存失败");
		}

		return result;
	}

	/**
	 * 上传图片
	 *
	 * @return 返回值为图片的地址
	 */
	@RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
	@ResponseBody
	public Result uploadImg(HttpServletRequest request, MultipartFile image) {
		String path = request.getSession().getServletContext().getRealPath("upload");
		String targetFileName = articleService.upload(image, path);
		String url = "http://img.wangfutai.vip/" + targetFileName;
		Result result = new Result();
		result.setResultCode("1");
		result.setMsg(url);
		return result;
	}

	/**
	 * 查看文章详情
	 * 
	 * @param aid
	 * @return Article
	 */
	@RequestMapping(value = "/article/{aid}", method = RequestMethod.GET)
	@ResponseBody
	public Article getArticleByid(@PathVariable Integer aid) {
		return articleService.getVueArticleById(aid);
	}

	@RequestMapping(value = "/admin/article/dustbin/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteArticle(@PathVariable Integer id) {
		Result result = new Result();
		result.setMsg("删除失败");
		result.setResultCode("0");
		int m = articleService.deleteArticleById(id);
		if (m > 0) {
			result.setMsg("删除成功");
			result.setResultCode("1");
		}

		return result;

	}

}
