//package com.kelly.test.aop.aspect
//
//import android.util.Log
//import com.kelly.test.aop.annotation.ClickBehavior
//import org.aspectj.lang.ProceedingJoinPoint
//import org.aspectj.lang.annotation.Around
//import org.aspectj.lang.annotation.Aspect
//import org.aspectj.lang.annotation.Pointcut
//import org.aspectj.lang.reflect.MethodSignature
//
///**
// * author: zongkaili
// * data: 2019-11-23
// * desc:
// */
//@Aspect //定义切面类
//class ClickBehaviorAspect {
//    companion object {
//        const val TAG = "Aop-Aspect-Click"
//    }
//
//    //1.用到的注解，放到当前的切入点进行处理（找到需要处理的切入点）
//    //execution : 以方法执行时作为切点，触发Aspect类
//    //* *(..) : 通配符，表示这个类的所有方法
//    @Pointcut("execution(@com.kelly.test.aop.annotation.ClickBehavior * *(..))")
//    public fun methodPointCut() {}
//
//    //2.对切入点进行处理
//    @Around("methodPointCut()")
//    @Throws(Throwable::class)
//    public fun jointPoint(jointPoint: ProceedingJoinPoint): Any {
//        //获取签名方法
//        val methodSignature = jointPoint.signature as MethodSignature
//
//        //获取方法所属的类名
//        val className = methodSignature.declaringType.simpleName
//
//        //获取方法名
//        val methodName = methodSignature.name
//
//        //获取方法的注解值（需要统计的用户行为）
//        val annotationName = methodSignature.method.getAnnotation(ClickBehavior::class.java).value
//
//        //统计方法的执行时间、统计用户点击某功能的行为
//        val begin = System.currentTimeMillis()
//        Log.d(TAG, "ClickBehavior Method Start >>> ")
//        val result = jointPoint.proceed()//切面的方法
//        val duration = System.currentTimeMillis() - begin
//        Log.d(TAG, "ClickBehavior Method End >>> ")
//        Log.d(TAG, String.format(" 统计了： %s功能，在%s类中的%s方法，用时%d ms",
//            annotationName, className, methodName, duration))
//
//        return result
//    }
//
//}