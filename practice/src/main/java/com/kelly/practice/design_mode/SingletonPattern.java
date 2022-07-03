package com.kelly.practice.design_mode;

/**
 * author: zongkaili
 * data: 2022/5/31
 * desc: 单例模式 双重校验锁
 */
public class SingletonPattern {
    private volatile static SingletonPattern instance;

    private SingletonPattern() {
    }

    public static SingletonPattern getInstance() {
        if (instance == null) {
            synchronized (SingletonPattern.class) {
                if (instance == null) {
                    instance = new SingletonPattern();
                }
            }
        }
        return instance;
    }
}
