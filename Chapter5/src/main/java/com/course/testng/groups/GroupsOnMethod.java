package com.course.testng.groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupsOnMethod {
    @Test(groups = "server")
    public void test1() {
        System.out.println("这个是“服务端组”的测试方法11111");
    }

    @Test(groups = "server")
    public void test2() {
        System.out.println("这个是“服务端组“的测试方法22222");
    }

    @Test(groups = "client")
    public void test3() {
        System.out.println("这个是“客户端组”的测试方法33333");
    }

    @Test(groups = "client")
    public void test4() {
        System.out.println("这个是“客户端组”的测试方法44444");
    }

    @BeforeGroups("server")
    public void beforeGroupsOnServer() {
        System.out.println("这个是“服务端”组“运行之前”运行的方法55555");
    }

    @AfterGroups("server")
    public void afterGroupsOnServer() {
        System.out.println("这个是“服务端”组“运行之后”运行的方法66666");
    }

    @BeforeGroups("client")
    public void beforeGroupsOnClient() {
        System.out.println("这个是“客户端”组“运行之前”运行的方法77777");
    }

    @AfterGroups("client")
    public void afterGroupsOnClient() {
        System.out.println("这个是“客户端“组“运行之后”运行的方法88888");
    }
}
