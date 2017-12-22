package com.core.constant;

import org.springframework.context.ApplicationContext;

public class Consts {

	public static final String LOGIN_USER = "loginUser";
	public static final String LOGIN = "/login/toLogin"; // 登录地址
    public static final String ALL_DICTIONARY = "allDictionary";

    public static ApplicationContext WEB_APP_CONTEXT = null; // 该值会在web容器启动时由WebAppContextListener初始化

	/**
	 * Login Constants
	 */
	public static final String ACCOUNT_PWD_ERROR = "用户名或密码错误";
	public static final String USER_INVAILD = "用户停用";
	public static final String CHECK_FAIL = "身份验证失败";
	public static final String PARAM_MISS = "缺少参数";
}
