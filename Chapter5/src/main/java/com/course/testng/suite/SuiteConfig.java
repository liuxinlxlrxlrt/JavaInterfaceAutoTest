package com.course.testng.suite;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class SuiteConfig {
    @BeforeSuite
    public void beforeSutie(){
        System.out.println("\t");
        System.out.println("before sutie运行拉");
    }
    @AfterSuite
    public void afterSuite(){
        System.out.println("after suite运行拉");
    }
    @BeforeTest
    public void beforeTest(){
        System.out.println("before test");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("after test");
    }
}
