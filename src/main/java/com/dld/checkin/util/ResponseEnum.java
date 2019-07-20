package com.dld.checkin.util;

public enum ResponseEnum {

	//操作成功
	SUCCESS(0000, "操作成功"),
	HAS_CHECKED_IN(3001, "当日已签到"),
	SERVER_ERROR(9999, "服务器异常");

	private int value;
	private String name;

	private ResponseEnum(int value, String name) {
		this.value = value;
		this.name = name;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
