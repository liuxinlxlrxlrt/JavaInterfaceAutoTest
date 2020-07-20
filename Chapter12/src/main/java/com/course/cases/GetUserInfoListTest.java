package com.course.cases;

import com.course.config.TestConfig;
import com.course.urils.DatabaseUtil;
import org.apache.ibatis.session.SqlSession;
import org.testng.annotations.Test;

public class GetUserInfoListTest {
    @Test(dependsOnGroups = "loginTrue",description = "获取性别为男性的用户信息")
    public void getUserListInfo(){
        SqlSession session = DatabaseUtil.getSqlSession();
        GetUserInfoListTest getUserInfoListCase = session.selectOne("getUserListCase");
        System.out.println(getUserInfoListCase.toString());
        System.out.println(TestConfig.getUserListUrl);
    }
}
