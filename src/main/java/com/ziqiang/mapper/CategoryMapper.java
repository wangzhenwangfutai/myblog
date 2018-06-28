package com.ziqiang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziqiang.model.Category;

public interface CategoryMapper {

	public List<Category> getCategoryList();

	public Category getCategoryByAlias(String aliasName);

	int deleteByPrimaryKey(Integer id);

	int deleteByIds(@Param("ids") String[] ids);

	int insertSelective(Category record);

	Category selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Category record);
}
