package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.urils.ConfigFile;
import com.course.urils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {
    @BeforeTest(groups = "loginTrue", description = "测试准备工作，获取httpClient等")
    public void beforeTest() {
        //“获取”用户信息接口url：http://localhost:8888/v1/getUserInfo
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        //“获取”用户信息“列表”接口url：http://localhost:8888/v1/getUserList
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GETUSERLIST);
        //“更新”用户信息接口url：http://localhost:8888/v1/updateUserInfo
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);
        //“添加”用户信息接口url：http://localhost:8888/v1/addUser
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSER);
        //“登录”接口url：http://localhost:8888/v1/login
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);

        TestConfig.defaultHttpClient = new DefaultHttpClient();
    }
    @Test(groups = "loginTrue",description = "用户邓丽成功接口测试")
    public void loginTrue(){
        SqlSession session = DatabaseUtil.getSqlSession();
        //获取logincase表中的信id为3的信息存储到LoginCase对象中
        LoginCase loginCase = session.selectOne("loginCase",3);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        //第一步发送请求
        String result = null;
        try {
            result = getResult(loginCase);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("mysql中expected："+loginCase.getExpected());
        //验证结果
        Assert.assertEquals(loginCase.getExpected(),result);
    }



    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse(){
        SqlSession session = DatabaseUtil.getSqlSession();
        //查询logincase表中的信id为2的信息存储到LoginCase对象中
        LoginCase loginCase = session.selectOne("loginCase",1);
        System.out.println(loginCase.toString());
        System.out.println(TestConfig.loginUrl);

        ////发请求post调用login接口查询AddUserCase对象的用户信息在user是否存在、获取查询结果
        String result = null;
        try {
            result = getResult(loginCase);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("mysql中expected："+loginCase.getExpected());
        //验证结果
        Assert.assertEquals(loginCase.getExpected(),result);
    }

    private String getResult(LoginCase loginCase) throws IOException {
        //声明一个方式，这个方式就是post
        HttpPost post = new HttpPost(TestConfig.loginUrl);

        //添加参数
        JSONObject param = new JSONObject();
        param.put("userName",loginCase.getUserName());
        param.put("password",loginCase.getPassword());

        //设置请求头信息，设置header
        post.setHeader("content-type","application/json");

        //将参数设置到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //声明一个对象来进行响应结果的存储
        String result;

        //执行post方法
        HttpResponse response = TestConfig.defaultHttpClient.execute(post);

        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println("请求结果："+result);

        TestConfig.cookieStore=TestConfig.defaultHttpClient.getCookieStore();
        return result;
    }
}
