package com.course.httpclient.demo;

import org.apache.commons.codec.Charsets;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

public class NewHttpClient {
    @Test
    public void test2()throws Exception{
        String result;
        HttpGet get = new HttpGet("http://www.baidu.com");
        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(), Charsets.UTF_8);
        System.out.println(result);
    }
}
