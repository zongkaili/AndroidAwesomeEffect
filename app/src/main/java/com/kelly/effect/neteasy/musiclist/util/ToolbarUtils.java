package com.kelly.effect.neteasy.musiclist.util;

import android.text.TextUtils;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import java.lang.reflect.Field;

public class ToolbarUtils {
    public static TextView getToolbarTitleView(Toolbar toolbar) {

        try {
            Field field = toolbar.getClass().getDeclaredField("mTitleTextView");
            field.setAccessible(true);

            Object object = field.get(toolbar);
            if (object != null) {
                TextView mTitleTextView = (TextView) object;
                return mTitleTextView;
            }
        } catch (IllegalAccessException e) {
        } catch (NoSuchFieldException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public static void setMarqueeForToolbarTitleView(final Toolbar toolbar) {
        toolbar.post(new Runnable() {
            @Override
            public void run() {
                TextView mTitleTextView = getToolbarTitleView(toolbar);
                if (mTitleTextView == null) {
                    return;
                }
                //设置可以水平滚动
                mTitleTextView.setHorizontallyScrolling(true);
                //设置无限循环
                mTitleTextView.setMarqueeRepeatLimit(-1);
                //设置跑马灯模式
                mTitleTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                //设置选中
                mTitleTextView.setSelected(true);
            }
        });
    }
}
