package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.urils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

public class addUserTest {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试")
    public void addUser(){
        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase",1);
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);
    }
}
