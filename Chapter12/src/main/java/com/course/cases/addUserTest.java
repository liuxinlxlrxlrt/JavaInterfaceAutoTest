package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.urils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class addUserTest {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser(){
        SqlSession session = DatabaseUtil.getSqlSession();
        //查询addusercase表中id为1的信息存储到LoginCase对象中
        AddUserCase addUserCase = session.selectOne("addUserCase",1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //发请求post调用addUser接口添加AddUserCase对象的用户信息到user表中、获取结果
        String result = null;
        try {
            result = getResult(addUserCase);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("result："+result);

        //验证返回结果
        //查询user表中添加成功的用户信息查询出来存储到User中
        //报错的原因是执行的速率不受控制，添加用户信息为执行完就查询，肯定是查不到的，
        //如何解决？添加等待一段时间查询
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User user = session.selectOne("addUser",addUserCase);

//        System.out.println("user："user.toString());
//        Assert.assertEquals(addUserCase.getExpected(),result);


    }

    private String getResult(AddUserCase addUserCase) throws Exception {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());

        //设置头信息
        post.setHeader("content-type","application/json");

        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //设置cookies
        TestConfig.defaultHttpClient.setCookieStore(TestConfig.cookieStore);

        //设置存放返回结果
        String result;
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        return result;
    }
}
