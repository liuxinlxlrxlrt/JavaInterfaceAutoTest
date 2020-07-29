package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

@Log4j
@RestController
@Api(value = "v1",description = "用户管理系统")
@RequestMapping("v1")
public class UserManager {
    @Autowired
    private SqlSessionTemplate template;

    @ApiOperation(value = "登录接口",httpMethod = "POST")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Boolean login(HttpServletResponse response,
                      @RequestBody User user){
        //查询数据库中时是否有这个用户数据
        int i = template.selectOne("login",user);
        //登录接口返回cookies
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        log.info("查询到结果是"+i);
        //判断结果是否为1
        if(i==1){
            log.info("登录用户是"+user.getUserName());
            return true;
        }
        return false;
    }

    @ApiOperation(value = "添加用户接口",httpMethod = "POST")
    @RequestMapping(value = "addUser",method = RequestMethod.POST)
    public boolean addUser(HttpServletRequest request,@RequestBody User user){
        //验证添加接口信息携带的cookies信息是否正确
        Boolean x = verifyCookies(request);
        int result=0;
        if(x!=null){
            result = template.insert("addUser",user);
        }
        if(result>0){
            log.info("添加用户的数量是："+result);
            return true;
        }
        return false;
    }

    @ApiOperation(value = "获取用户（列表） 信息接口",httpMethod = "POST")
    @RequestMapping(value = "getUserInfo",method = RequestMethod.POST)
    public List<User> getUserInfo(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
//        Boolean x= true;
        if(x==true){
            List<User> users = template.selectList("getUserInfo",user);
            log.info("getUserInfo获取到用户数量是："+users.size());
            return users;
        }else {
            return null;
        }
    }

    @ApiOperation(value = "更新/删除用户接口",httpMethod = "POST" )
    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    public int updateUser(HttpServletRequest request,@RequestBody User user){
        Boolean x = verifyCookies(request);
        int i = 0;
        if(x==true){
            i = template.update("updateUserInfo",user);
        }
        log.info("更新数据的条数为："+i);
        return i;
    }

    private Boolean verifyCookies(HttpServletRequest request) {
        //从request获取cookeis
        Cookie[] cookies = request.getCookies();
        //判断cookies是否为空
        if(Objects.isNull(cookies)){
            log.info("cookies为空");
            return false;
        }

        for(Cookie cookie:cookies){
            if(cookie.getName().equals("login")&& cookie.getValue().equals("true")){
                log.info("cookies验证通过");
                return true;
            }
        }
        return false;
    }
}
