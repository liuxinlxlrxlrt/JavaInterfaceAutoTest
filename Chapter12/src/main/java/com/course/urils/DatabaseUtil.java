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
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
            //sqlSession就是能否执行配置文件中的sql语句了
            sqlSession = factory.openSession();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sqlSession;
    }
}

