package com.kelly.effect.neteasy.playpage.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import com.kelly.effect.R;

public class MyViewFlipper extends ViewFlipper implements View.OnTouchListener {
    private int musicSize = 0;
    private int mCurrentItem = 0;    //初始化在第一个位置
    private float originalX;//ACTION_DOWN事件发生时的手指坐标
    private int flipper_width = 0;//控件宽
    public static final int SCROLL_STATE_IDLE = 0;//空闲状态
    public static final int SCROLL_STATE_DRAGGING = 1;//滑动状态
    public static final int SCROLL_STATE_SETTLING = 2;//滑动后自然沉降的状态
    private OnPageChangeListener mOnPageChangeListener;//滑动切换监听

    public MyViewFlipper(Context context) {
        this(context, null);
    }

    public MyViewFlipper(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
        setLongClickable(true);
    }

    //设置音乐数
    public void setMusicSize(int musicSize) {
        this.musicSize = musicSize;
    }


    //获取当前下标
    public int getCurrentItem() {
        return mCurrentItem;
    }

    //设置当前下标
    public void setCurrentItem(int mCurrentItem) {
        this.mCurrentItem = mCurrentItem;
    }

    //获取下一个下标（并切换图片）
    private int nextItem(int index) {
        mCurrentItem = index;
        if (mCurrentItem >= musicSize) {
            mCurrentItem = 0;
        } else if (mCurrentItem < 0) {
            mCurrentItem = musicSize - 1;
        }
        return mCurrentItem;
    }

    //获取上一个下标（并切换图片）
    public int previousItem(int index) {
        mCurrentItem = index;
        if (mCurrentItem >= musicSize) {
            mCurrentItem = 0;
        } else if (mCurrentItem < 0) {
            mCurrentItem = musicSize - 1;
        }
        return mCurrentItem;
    }

    //仅获取下标
    public int getNextItem() {
        int next = mCurrentItem + 1;
        if (next >= musicSize) {
            return 0;
        } else if (next < 0) {
            return musicSize - 1;
        }
        return next;
    }

    //仅获取下标
    public int getPreviousItem() {
        int previous = mCurrentItem - 1;
        if (previous >= musicSize) {
            return 0;
        } else if (previous < 0) {
            return musicSize - 1;
        }
        return previous;
    }

    //获取没在屏幕中显示的View的下标
    public int getOtherItem() {
        return getChildCount() - 1 - getDisplayedChild();
    }

    //获取没在屏幕中显示的View
    public View getOtherView() {
        return getChildAt(getChildCount() - 1 - getDisplayedChild());
    }

    //获取当前屏幕中显示的Poster
    public ImageView getCurrentPosterView() {
        return getCurrentView().findViewById(R.id.ivPoster);
    }

    //获取没在屏幕中显示的Poster
    public ImageView getOtherPosterView() {
        return getChildAt(getChildCount() - 1 - getDisplayedChild()).findViewById(R.id.ivPoster);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        flipper_width = w;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float dx = event.getX() - originalX;
        //处理滑动事件 实现切换动作
        //偏移百分比 0，1
        float pageOffset = Math.abs(dx) / flipper_width;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                originalX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                //move 处理碟片移动
                //可见碟片
                getCurrentView().setTranslationX(dx);
                //other 可见
                getOtherView().setVisibility(VISIBLE);
                if (dx > 0) {
                    //右滑 上一曲
                    getOtherView().setTranslationX(dx - flipper_width);
                } else {
                    //左滑 下一曲
                    getOtherView().setTranslationX(dx + flipper_width);
                }
                //提供接口回调
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrolled(mCurrentItem, pageOffset, dx);
                    mOnPageChangeListener.onPageScrollStateChanged(SCROLL_STATE_DRAGGING);
                }
                break;
            case MotionEvent.ACTION_UP:
                //回弹处理
                final boolean isNext = dx < 0;
                if (mOnPageChangeListener != null) {
                    mOnPageChangeListener.onPageScrollStateChanged(SCROLL_STATE_SETTLING);
                }
                if (pageOffset > 0.5) {
                    //切换 动画自动偏移剩余的距离
                    ValueAnimator animator = ValueAnimator.ofFloat(dx,
                            isNext ? -flipper_width : flipper_width);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            getCurrentView().setTranslationX((Float) animation.getAnimatedValue());
                            getOtherView().setTranslationX((Float) animation.getAnimatedValue() +
                                    (isNext ? flipper_width : -flipper_width));
                            if (Math.abs((Float) animation.getAnimatedValue()) == flipper_width) {
                                //动画执行完成
                                if (isNext) {
                                    nextItem(mCurrentItem + 1);
                                    showNext();
                                } else {
                                    //上一曲
                                    previousItem(mCurrentItem - 1);
                                    showPrevious();
                                }
                                if (mOnPageChangeListener != null) {
                                    mOnPageChangeListener.onPageSelected(mCurrentItem, isNext);
                                    mOnPageChangeListener.onPageScrollStateChanged(SCROLL_STATE_IDLE);
                                }
                            }
                        }
                    });
                    animator.start();

                } else {
                    //回弹
                    ValueAnimator animator = ValueAnimator.ofFloat(dx, 0);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            getCurrentView().setTranslationX((Float) animation.getAnimatedValue());
                            getOtherView().setTranslationX((Float) animation.getAnimatedValue()
                                    + (isNext ? flipper_width : -flipper_width));
                            if ((float) animation.getAnimatedValue() == 0) {
                                if (mOnPageChangeListener != null) {
                                    mOnPageChangeListener.onPageScrollStateChanged(SCROLL_STATE_IDLE);
                                }
                            }
                        }
                    });
                    animator.start();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public void showPreviousWithAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, getWidth());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                //另一个子View的下标
                getCurrentView().setTranslationX((Float) animation.getAnimatedValue());
                getOtherView().setVisibility(VISIBLE);
                getOtherView().setTranslationX((Float) animation.getAnimatedValue() - getWidth());
                if (Math.abs((float) animation.getAnimatedValue()) == getWidth()) {
                    previousItem(mCurrentItem - 1);
                    showPrevious();
                    if (mOnPageChangeListener != null) {
                        mOnPageChangeListener.onPageSelected(mCurrentItem, false);
                        mOnPageChangeListener.onPageScrollStateChanged(SCROLL_STATE_IDLE);
                    }
                }
            }
        });
        animator.start();
    }

    public void showNextWithAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, -getWidth());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //另一个子View的下标
                getCurrentView().setTranslationX((Float) animation.getAnimatedValue());
                getOtherView().setVisibility(VISIBLE);
                getOtherView().setTranslationX((Float) animation.getAnimatedValue() + getWidth());
                if (Math.abs((float) animation.getAnimatedValue()) == getWidth()) {
                    nextItem(mCurrentItem + 1);
                    showNext();
                    if (mOnPageChangeListener != null) {
                        mOnPageChangeListener.onPageSelected(mCurrentItem, true);
                        mOnPageChangeListener.onPageScrollStateChanged(SCROLL_STATE_IDLE);
                    }
                }
            }
        });
        animator.start();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    public interface OnPageChangeListener {
        void onPageScrolled(int position, float positionOffset, float positionOffsetPixels);

        void onPageSelected(int position, boolean isNext);

        void onPageScrollStateChanged(int state);
    }
}
