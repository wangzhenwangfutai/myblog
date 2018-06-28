package com.ziqiang.model;

import java.io.Serializable;

public class Friend implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = -3654751979209282588L;
	private int id;
	private String siteName;
	private String siteUrl;
	private String siteDesc;
	private int sort;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	public String getSiteDesc() {
		return siteDesc;
	}
	public void setSiteDesc(String siteDesc) {
		this.siteDesc = siteDesc;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	
}
