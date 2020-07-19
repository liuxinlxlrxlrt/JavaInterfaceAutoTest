package com.course.httpclient.cookies;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
    private String url;
    private ResourceBundle bundle;
    private static CookieStore store;

    @BeforeTest
    public void getPropertiesFileInfo(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies()throws IOException {
        String result1 = null;
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url+uri;
        HttpGet get = new HttpGet(testUrl);
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response1 = client.execute(get);
        result1 = EntityUtils.toString(response1.getEntity(),Charsets.UTF_8);
        System.out.println(result1);

        MyCookiesForPost.store = client.getCookieStore();
        List<Cookie> cookieList = store.getCookies();
        for(Cookie cookie:cookieList){
            String name2 = cookie.getName();
            String value2 = cookie.getValue();
            System.out.println("cookie name2：" + name2 + "，cookie value2：" + value2);
        }
    }

    @Test(dependsOnMethods = "testGetCookies")
    public void testPostMethod() throws IOException{
        String uri = bundle.getString("test.post.with.cookies");
        String testUrl = this.url+uri;

//        postWithCookieOne(testUrl);
        postWithCookieTwo(testUrl);
    }

    public static void postWithCookieTwo(String testUrl)throws IOException{
        HttpPost post = new HttpPost(testUrl);

        JSONObject param2 = new JSONObject();
        param2.put("name","gaoyuanyuan");
        param2.put("age","29");

        post.setHeader("content-type","application/json");

        StringEntity entity = new StringEntity(param2.toString(),Charsets.UTF_8);
        post.setEntity(entity);

        CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();

        CloseableHttpResponse response = client.execute(post);

        String result = EntityUtils.toString(response.getEntity(),Charsets.UTF_8);
        System.out.println("resultJson2："+result);

        JSONObject resultJson2 = new JSONObject(result);

        String gulinazha = (String)resultJson2.get("liqing");
        String status = (String)resultJson2.getString("status");

        //具体的判断返回结果的值
        Assert.assertEquals("success",gulinazha);
        Assert.assertEquals("1",status);


    }

    /**
     * 第一种老方式携带cookies和header访问post方法:DefaultHttpClient
     * @param testUrl
     */
    public static void postWithCookieOne(String testUrl) throws IOException{
        //声明一个Client对象，用来进行方法的执行
        DefaultHttpClient client = new DefaultHttpClient();

        //声明一个方式，这个方式就是post
        HttpPost post = new HttpPost(testUrl);

        //添加参数
        JSONObject param = new JSONObject();
        param.put("name","gaoyuanyuan");
        param.put("age","29");

        //设置请求头信息，设置header
        post.setHeader("content-type","application/json");

        //将参数设置到方法中
        StringEntity entity = new StringEntity(param.toString(),Charsets.UTF_8);
        post.setEntity(entity);

        //声明一个对象来进行响应结果的存储
        String result = null;

        //设置cookies信息
        client.setCookieStore(MyCookiesForPost.store);

        //执行post方法
        HttpResponse response = client.execute(post);

        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),Charsets.UTF_8);
        System.out.println("resultJson："+result);

        //处理结果，就是判断
        //将返回的响应结果字符串转化为json对象
        JSONObject resultJson = new JSONObject(result);

        String gulinazha = (String)resultJson.get("liqing");
        String status = (String)resultJson.getString("status");

        //具体的判断返回结果的值
        Assert.assertEquals("success",gulinazha);
        Assert.assertEquals("1",status);
    }
}
