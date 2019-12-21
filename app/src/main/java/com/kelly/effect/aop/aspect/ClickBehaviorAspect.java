package com.kelly.effect.aop.aspect;

import android.util.Log;

import com.kelly.effect.aop.annotation.ClickBehavior;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * author: zongkaili
 * data: 2019-11-24
 * desc:
 */
@Aspect
public class ClickBehaviorAspect {
    private static String TAG = "Aop-Aspect-Click";

    //1.用到的注解，放到当前的切入点进行处理（找到需要处理的切入点）
    //execution : 以方法执行时作为切点，触发Aspect类
    //* *(..) : 通配符，表示这个类的所有方法
    @Pointcut("execution(@com.kelly.effect.aop.annotation.ClickBehavior * *(..))")
    public void methodPointCut() {}

    //2.对切入点进行处理
    @Around("methodPointCut()")
    public Object jointPoint(ProceedingJoinPoint jointPoint) throws Throwable {
        //获取签名方法
        MethodSignature methodSignature = (MethodSignature) jointPoint.getSignature();

        //获取方法所属的类名
        String className = methodSignature.getDeclaringType().getSimpleName();

        //获取方法名
        String methodName = methodSignature.getName();

        //获取方法的注解值（需要统计的用户行为）
        String annotationName = methodSignature.getMethod().getAnnotation(ClickBehavior.class).value();

        //统计方法的执行时间、统计用户点击某功能的行为
        long begin = System.currentTimeMillis();
        Log.d(TAG, "ClickBehavior Method Start >>> ");
        Object result = jointPoint.proceed();//切面的方法
        long duration = System.currentTimeMillis() - begin;
        Log.d(TAG, "ClickBehavior Method End >>> ");
        Log.d(TAG, String.format(" 统计了： [%s]功能，在%s类中的%s方法，用时%d ms",
                annotationName, className, methodName, duration));

        return result;
    }
}
