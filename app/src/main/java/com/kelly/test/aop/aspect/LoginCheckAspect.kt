//package com.kelly.test.aop.aspect
//
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.widget.Toast
//import com.kelly.test.aop.AopLoginActivity
//import com.kelly.test.aop.SpUtils
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
//class LoginCheckAspect {
//    companion object {
//        const val TAG = "Aop-Aspect-Login"
//    }
//
//    //1.用到的注解，放到当前的切入点进行处理（找到需要处理的切入点）
//    //execution : 以方法执行时作为切点，触发Aspect类
//    //* *(..) : 通配符，表示这个类的所有方法
//    @Pointcut("execution(@com.kelly.test.aop.annotation.LoginCheck * *(..))")
//    public fun methodPointCut() {}
//
//    //2.对切入点进行处理
//    @Around("methodPointCut")
//    @Throws(Throwable::class)
//    public fun jointPoint(jointPoint: ProceedingJoinPoint): Any? {
//        val context = jointPoint.`this` as Context
//        return if (SpUtils.getBoolean(context, "isLogin")) {
//            Log.d(TAG, "检测到已登录")
//            jointPoint.proceed()
//        } else {
//            Log.d(TAG, "检测到未登录")
//            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
//            context.startActivity(Intent(context, AopLoginActivity::class.java))
//            null
//        }
//    }
//
//}