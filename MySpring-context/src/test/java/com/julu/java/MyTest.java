package com.julu.java;

import com.example.myspringbeans.context.support.ClassPathXmlApplicationContext;

/**
 * @author julu
 * @date 2022/9/11 15:16
 */
public class MyTest {


    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/Users/liuyuyang/Downloads/MySpring/MySpring-context/src/main/resources/MET-INF/ClassPathXmlTest.xml");
    }
}
