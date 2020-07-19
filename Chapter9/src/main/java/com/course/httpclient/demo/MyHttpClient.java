package com.course.httpclient.demo;


import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

public class MyHttpClient {

    @Test
    public void test1() throws IOException {
        //存放我们的结果
        String result;
        HttpGet get = new HttpGet("http://www.baidu.com");
        //执行get方法
        //HttpClient client = new DefaultHttpClient();这个不会获取cookies信息，需修改才能获取cookies信息
        HttpClient client = new DefaultHttpClient();

        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);

    }
}
