package com.ziqiang.model;

import java.io.Serializable;


public class Result implements Serializable {
	/** serialVersionUID */
	private static final long serialVersionUID = 3696781837059634983L;
	
	// 操作结果
	private String resultCode;
	
	// 错误信息
	private String errorInfo;
	
	private String msg;

	// 附属对象
	private Object object;
	
	public boolean isSuccess() {
		return resultCode == "1";
	}

	public Result() {
		super();
	}

	public Result(String resultCode, String errorInfo) {
		this.resultCode = resultCode;
		this.errorInfo = errorInfo;
	}
	
	public Result(String resultCode, String errorInfo, Object object) {
		this.resultCode = resultCode;
		this.errorInfo = errorInfo;
		this.object = object;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
