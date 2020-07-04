package com.course.testng.parameter;

import org.testng.annotations.DataProvider;

public class TestData {
    @DataProvider(name = "testData")
    public static Object[][] getNameAge(){
        return  new Object[][]{
                {"liuyifei",25},
                {"lijiaxing",35},
                {"liangxiaobing",28}
        };
    }
}
