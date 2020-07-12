package com.kelly.effect.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;

import java.util.concurrent.atomic.AtomicInteger;

public class ScreenUtil {
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public ScreenUtil() {
    }

    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5F);
    }

    public static int px2dp(Context context, float px) {
        float scale = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5F);
    }

    public static Rect getViewRect(View view, Rect rect) {
        if (rect == null) {
            rect = new Rect();
        }

        ((Activity) view.getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        int decorViewTop = rect.top;
        view.getGlobalVisibleRect(rect);
        rect.top -= decorViewTop;
        rect.bottom -= decorViewTop;
        return rect;
    }

    public static Rect getViewRect(View view) {
        return getViewRect(view, (Rect) null);
    }

    public static int getGenerateViewId() {
        int i;
        if (Build.VERSION.SDK_INT < 17) {
            i = generateViewId();
            return i;
        } else {
            i = View.generateViewId();
            return i;
        }
    }

    public static int generateViewId() {
        int result;
        int newValue;
        do {
            result = sNextGeneratedId.get();
            newValue = result + 1;
            if (newValue > 16777215) {
                newValue = 1;
            }
        } while (!sNextGeneratedId.compareAndSet(result, newValue));

        return result;
    }

}
