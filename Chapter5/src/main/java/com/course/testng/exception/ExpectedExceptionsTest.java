package com.course.testng.exception;

import org.testng.annotations.Test;

public class ExpectedExceptionsTest {
    /**
     * 什么时候用到异常测试？
     * 在我们期望结果为某一个异常的时候
     * 比如：传入某些不合法的参数，程序抛出异常
     * 也就是说我的预期结果就是这个异常
     */

    //这是一个测试结果会失败的异常测试
    @Test(expectedExceptions = RuntimeException.class)
    public void runtTimeExceptionFailed(){
        System.out.println("这是一个结果会失败的异常测试");
    }
    //这是一个成功的异常测试
    @Test(expectedExceptions = RuntimeException.class)
    public void runTimeExceptionSuccess(){
        System.out.println("这是我的成功的异常测试");
        throw  new RuntimeException();
    }
}
