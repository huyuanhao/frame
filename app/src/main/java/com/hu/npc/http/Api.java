package com.hu.npc.http;

/**
 * @author PC
 * @date 2019/05/09 11:01
 */
public class Api {
    public static String BaseUrl = "http://pay.888cfb.com/embroid/";
//    public static String BaseUrl = "http://127.0.0.1:8080/embroid/";

    public static String USERNAME = "username";
    public static String UID = "uid";
    public static String TOKEN = "token";
    public static String TYPE = "type";//账号类型 0--总后台 1--代理 2--商户 3--通道 4--普通会员
    public static String LoginStatu = "loginstatu";//登陆状态 0.已登录 1 已退出 2.过期3.锁定
}