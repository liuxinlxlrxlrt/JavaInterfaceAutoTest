package com.course.testng.testng;

import org.testng.annotations.*;

//Annotation ：注记层;注解类;备注;注解;注释
public class BaseAnnotation {
    //最基本的注解，用来把方法标记为方法的一部分
    @Test
    public void testCase1(){
        System.out.printf("Thread Id:%s%n",Thread.currentThread().getId());
        System.out.println("test这是测试用例1");
    }

    @BeforeMethod
    public void beforeMethod(){
        System.out.println("beforeMethod这个是在测试方法之前运行的");
    }
    @Test
    public void testCase2(){
        System.out.printf("Thread Id:%s%n",Thread.currentThread().getId());
        System.out.println("test这是测试用例2");
    }
    @AfterMethod
    public void afterMethod(){
        System.out.println("afterMethod这个实在测试方法之后运行的");
    }

    @BeforeClass
    public void beforeClass(){
        System.out.println("beforeClass是在运行类之前运行的方法");
    }

    @AfterClass
    public void afterClass(){
        System.out.println("afterClass是在运行类之后运行的方法");
    }

    @BeforeSuite
    public void beforeSutie(){
        System.out.println("beforeSutie测试套件");
    }

    @AfterSuite
    public void afterSuite(){
        System.out.println("afterSuite测试套件");
    }

}
