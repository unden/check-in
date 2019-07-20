package com.dld.checkin.util;

import org.springframework.util.StringUtils;

public class ResponseUtil {

	private int code;

	private String message;

	private Object data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public static ResponseUtil success() {
		ResponseUtil responseUtils = new ResponseUtil();
		responseUtils.setCode(ResponseEnum.SUCCESS.getValue());
		responseUtils.setMessage(ResponseEnum.SUCCESS.getName());
		return responseUtils;
	}

	public static ResponseUtil success(Object data) {
		ResponseUtil responseUtils = new ResponseUtil();
		responseUtils.setCode(ResponseEnum.SUCCESS.getValue());
		responseUtils.setMessage(ResponseEnum.SUCCESS.getName());
		responseUtils.setData(data);
		return responseUtils;
	}

	public static ResponseUtil fail(ResponseEnum responseEnum, String errorDes) {
		ResponseUtil responseUtils = new ResponseUtil();
		responseUtils.setCode(responseEnum.getValue());
		if (StringUtils.isEmpty(errorDes)) {
			responseUtils.setMessage(responseEnum.getName());
		} else {
			responseUtils.setMessage(errorDes);
		}
		
		return responseUtils;
	}
	
}
