package com.ziqiang.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ziqiang.model.Category;
import com.ziqiang.model.Result;
import com.ziqiang.service.CategoryService;

/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryControllerVue {
    @Autowired
	private CategoryService categoryService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
    public List<Category> getAllCategories() {
		return categoryService.getCategoryList();
    }

    @RequestMapping(value = "/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Result deleteById(@PathVariable String ids) {
		Result result = new Result();
		result.setResultCode("0");
		result.setMsg("失败");
		boolean flag = categoryService.deleteCategoryByIds(ids);
		if (flag) {
			result.setResultCode("1");
			result.setMsg("成功");
        }
		return result;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public Result addNewCate(Category category) {
		Result msg = new Result();
		msg.setResultCode("0");
		msg.setMsg("失败");
        int result = categoryService.addCategory(category);
        if (result == 1) {
			msg.setResultCode("1");
			msg.setMsg("成功");
        }
		return msg;
    }

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Result updateCate(String categoryName, Integer id) {
		Result result = new Result();
		result.setResultCode("0");
		result.setMsg("失败");
		Category category = new Category();
		category.setCategoryName(categoryName);
		category.setId(id);
		int i = categoryService.updateCategory(category);
        if (i == 1) {
			result.setResultCode("1");
			result.setMsg("成功");
        }
		return result;
    }
}
