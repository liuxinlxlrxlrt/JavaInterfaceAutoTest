package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.urils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GetUserInfoTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取userid为1的用户信息")
    public void getUserInfo(){
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = session.selectOne("getUserInfoCase",1);
        System.out.println(getUserInfoCase.toString());
        System.out.println(TestConfig.getUserInfoUrl);

        JSONArray resultJson = getJsonResult(getUserInfoCase);
        System.out.println("resultJson："+resultJson.toString());

        User user =session.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
        List userList = new ArrayList();
        userList.add(user);
        JSONArray jsonArray = new JSONArray(userList);
        System.out.println("jsonArray："+jsonArray.toString());
        //错误的原因是实际数组和期望数组的结果不一样
        //解决版本：拿出数组的值分别对比
        //Assert.assertEquals(int expected, int actual)为例:
        Assert.assertEquals(jsonArray.toString(),resultJson.toString());
    }

    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) {

        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param= new JSONObject();
        param.put("id",getUserInfoCase.getUserId());

        post.setHeader("content-type","application/json");

        StringEntity entity= new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);

        String result= "";

        HttpResponse response = null;
        try {
            response = TestConfig.defaultHttpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            result = EntityUtils.toString(response.getEntity(),"utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List resultList = Arrays.asList(result);
        JSONArray array = new JSONArray(result);
        return array;
    }
}
