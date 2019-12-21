package com.kelly.effect.aop.aspect;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kelly.effect.aop.AopLoginActivity;
import com.kelly.effect.aop.SpUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author: zongkaili
 * data: 2019-11-24
 * desc:
 */
@Aspect
public class LoginCheckAspect {
    private static final String TAG = "Aop-Aspect-Login";

    //1.用到的注解，放到当前的切入点进行处理（找到需要处理的切入点）
    //execution : 以方法执行时作为切点，触发Aspect类
    //* *(..) : 通配符，表示这个类的所有方法
    @Pointcut("execution(@com.kelly.effect.aop.annotation.LoginCheck * *(..))")
    public void methodPointCut() {}

    //2.对切入点进行处理
    @Around("methodPointCut()")
    public Object jointPoint(ProceedingJoinPoint jointPoint) throws Throwable {
        Context context = (Context) jointPoint.getThis();
        if (SpUtils.Companion.getBoolean(context, "isLogin")) {
            Log.d(TAG, "检测到已登录");
            return jointPoint.proceed();
        } else {
            Log.d(TAG, "检测到未登录");
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, AopLoginActivity.class));
            return null;
        }
    }
}
