package com.course.urils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.*;

import java.io.IOException;
import java.io.Reader;

public class DatabaseUtil {
    private static SqlSession sqlSession;

    public static SqlSession getSqlSession() {
        try {
            //获取配置资源
            Reader reader = Resources.getResourceAsReader("database-config.xml");
            //创建SqlSessionFactoryBuilder对象，调用内部的build方法解析.xml文件信息
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            //sqlSession就是能否执行配置文件中的sql语句了
            //openSession底层就是做各种成员变量的初始化
            sqlSession = factory.openSession();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }
}

