package com.course.testng.groups;

import org.testng.annotations.Test;

@Test(groups = "stu")
public class GroupsOnClass2 {
    public void stu1(){
        System.out.println("GroupsOnClass2 中的 stu1 运行");
    }
    public void stu2(){
        System.out.println("GroupsOnClass2中的 stu2 运行");
    }
}