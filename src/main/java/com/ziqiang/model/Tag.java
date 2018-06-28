package com.ziqiang.model;

import java.io.Serializable;

public class Tag implements Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 3889094528375534849L;
	private int id;
	private String tagName;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
}	
