package com.course.testng.timeout;

import org.testng.annotations.Test;

public class TimeOutTest {

    @Test(timeOut =  3000)//单位为毫秒值
    public void testSuccess() throws InterruptedException{
        Thread.sleep(2000);
    }

    @Test(timeOut =  2000)//单位为毫秒值
    public void testFail() throws InterruptedException{
        Thread.sleep(3000);
    }
}
