package com.blog.utils;

import cn.hutool.http.HttpUtil;

public class HttpUtils {

    public static String  getHttpResource(String url){
       //发送get请求并接收响应数据
        return HttpUtil.get(url);
    }
}
