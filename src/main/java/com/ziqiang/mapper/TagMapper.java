package com.ziqiang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ziqiang.model.Tag;

public interface TagMapper {

	List<Tag> getTagList();

	List<Tag> getArticleTagList(@Param("id") Integer id);

	List<Tag> getAllTagList();
	
	int addTag(Tag tag);

	int deleteTagByName(@Param("tagName") String tagName);

}
