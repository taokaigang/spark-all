package com.spark.util;

/**
 * 设计模式
 * 使用饿汉式单例 适用于有多线程的环境
 */
public class Singleton {

    private Singleton(){
//        System.out.println("一个饿汉式单例");
    }

    private static Singleton instance;

    static {
        instance = new Singleton();
    }

    public static Singleton getInstance(){
        return instance;
    }

}
