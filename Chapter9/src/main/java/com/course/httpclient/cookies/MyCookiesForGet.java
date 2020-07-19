package com.course.httpclient.cookies;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {

    private String url;
    private ResourceBundle bundle;
    //CookieStore用例存储cookies信息的变量,CookieStore 是 Java API 中用来bai处理 HTTP 客户端的 Cookie 存储du策略的类。
    private static CookieStore store;

    @BeforeTest
    public void beforeTest() {
        //ResourceBundle类的作用就是读取资源属性文件（properties）
        //ResourceBundle类通常是用于针对不同的语言来使用的属性文件。
        //而如果你的应用程序中的属性文件只是一些配置，并不是针对多国语言的目的。那么使用Properties类就可以了。
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {

        String uri = bundle.getString("getCookies.uri");
        //从配置文件中拼接测试的trl
        String testUrl = this.url + uri;

//        getCookieOne(testUrl);
        System.out.println("----------------------------------");
        getCookieTwo(testUrl);

    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public void testGetCookiesWithCoookies() throws IOException {
        String uri = bundle.getString("test.get.with.cookies");
        String testUrl = this.url + uri;

//        getWithCookiesOne(testUrl);
        System.out.println("----------------------------------");
        getWithCookieTwo(testUrl);

    }

    /**
     * 第一种新方法携带cookieget访问get请求：CloseableHttpClient
     * @param testUrl
     */
    public static void getWithCookieTwo(String testUrl) throws IOException{
        HttpGet get = new HttpGet(testUrl);
        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();
        CloseableHttpResponse response = client.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("statusCode："+statusCode);
        if(statusCode==200){
            String result = EntityUtils.toString(response.getEntity(),Charsets.UTF_8);
            System.out.println(result);
        }

        List<Cookie> cookieList4 = store.getCookies();
        for (Cookie cookie4 : cookieList4) {
            String name4 = cookie4.getName();
            String value4 = cookie4.getValue();
            System.out.println("cookie name4：" + name4 + "，cookie value4：" + value4);
        }


    }

    /**
     * 第一种老方法携带cookieget访问get请求：DefaultHttpClient client = new DefaultHttpClient();
     * @param testUrl
     */
    public static void getWithCookiesOne(String testUrl)throws IOException{
        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        //设置cookies信息
        client.setCookieStore(MyCookiesForGet.store);

        HttpResponse response = client.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println("响应状态码：" + statusCode);

        if (statusCode == 200) {
            String result = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
            System.out.println(result);
        }

    }
    /**
     * 第二种方新式获取cookies：CookieStore cookieStore = new BasicCookieStore();CloseableHttpClient
     */
    public static void getCookieTwo(String testUrl) throws IOException {
        String result = null;
        MyCookiesForGet.store = new BasicCookieStore();

        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();
        HttpGet get = new HttpGet(testUrl);
        CloseableHttpResponse response = client.execute(get);
        HttpEntity entity = response.getEntity();
        result = EntityUtils.toString(entity, Charsets.UTF_8);
        System.out.println(result);

        List<Cookie> cookieList = store.getCookies();
        for (Cookie cookie : cookieList) {
            String name1 = cookie.getName();
            String value1 = cookie.getValue();
            System.out.println("cookie name1：" + name1 + "，cookie value1：" + value1);
        }
    }

    /**
     * 第一种老方式获取cookies：DefaultHttpClient client = new DefaultHttpClient();
     */
    public static void getCookieOne(String testUrl) throws IOException {
        String result = null;
        //创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象
        HttpGet get = new HttpGet(testUrl);

        DefaultHttpClient client = new DefaultHttpClient();

        //使用DefaultHttpClient类的execute方法发送HTTP GET或HTTP POST请求，并返回HttpResponse对象。
        HttpResponse response = client.execute(get);

        //HttpResponse接口的getEntity方法返回响应信息
        result = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
        System.out.println(result);

        //获取cookies信息
        //DefaultHttpClient对象的getCookieStore()，得到一个CookieStore对象，我们用到的Cookie就存在这里
        MyCookiesForGet.store = client.getCookieStore();
        List<Cookie> cookieList = store.getCookies();
        for (Cookie cookie : cookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name：" + name + "，cookie value：" + value);
        }
    }
}
