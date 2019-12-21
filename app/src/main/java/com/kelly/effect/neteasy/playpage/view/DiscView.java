package com.kelly.effect.neteasy.playpage.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.viewpager.widget.ViewPager;
import com.kelly.effect.R;
import com.kelly.effect.neteasy.musiclist.ui.UIUtils;
import com.kelly.effect.neteasy.musiclist.ui.ViewCalculateUtil;
import com.kelly.effect.neteasy.playpage.model.MusicData;

import java.util.ArrayList;
import java.util.List;

public class DiscView extends RelativeLayout {
    /*手柄起始角度*/
    public static final float ROTATION_INIT_NEEDLE = -20;

    /*唱针宽高、距离等比例*/
    public static final int NEEDLE_WIDTH = 276;
    public static final int NEEDLE_HEIGHT = 382;
    public static final int NEEDLE_MARGIN_LEFT = 495;
    public static final int NEEDLE_PIVOT_X = 56;
    public static final int NEEDLE_PIVOT_Y = 93;
    public static final int NEEDLE_MARGIN_TOP = 32;

    /*唱盘比例*/
    public static final int DISC_BG_WIDTH = 810;
    public static final int DISC_BLACK_WIDTH = 804;
    public static final int DISC_POSTER_WIDTH = 534;
    public static final int DISC_MARGIN_TOP = 210;

    private ImageView mIvNeedle;
    private MyViewFlipper mViewFlipper;
    private ObjectAnimator mNeedleAnimator;

    private List<MusicData> mMusicData = new ArrayList<>();
    private List<ObjectAnimator> mDiscAnimators = new ArrayList<>();
    /*标记ViewFlipper是否处于偏移的状态*/
    private boolean mViewFlipperIsOffset = false;

    /*标记唱针复位后，是否需要重新偏移到唱片处*/
    private boolean mIsNeed2StartPlayAnimator = false;

    public static final int DURATION_NEEDLE_ANIMATOR = 500;
    private NeedleAnimatorStatus needleAnimatorStatus = NeedleAnimatorStatus.IN_FAR_END;

    private MusicStatus musicStatus = MusicStatus.STOP;
    private IPlayInfo mIPlayInfo;
    private int otherPosterRes = -1;

    /*唱针当前所处的状态*/
    private enum NeedleAnimatorStatus {
        /*移动时：从唱盘往远处移动*/
        TO_FAR_END,
        /*移动时：从远处往唱盘移动*/
        TO_NEAR_END,
        /*静止时：离开唱盘*/
        IN_FAR_END,
        /*静止时：贴近唱盘*/
        IN_NEAR_END
    }

    /*音乐当前的状态：只有播放、暂停、停止三种*/
    public enum MusicStatus {
        PLAY, PAUSE, STOP
    }

    /*DiscView需要触发的音乐切换状态：播放、暂停、上/下一首、停止*/
    public enum MusicChangedStatus {
        PLAY, PAUSE, NEXT, PREVIOUS, STOP
    }

    public interface IPlayInfo {
        /*用于更新标题栏变化*/
        void onMusicInfoChanged(String musicName, String musicAuthor);

        /*用于更新背景图片*/
        void onMusicPicChanged(int musicPicRes);

        /*用于更新音乐播放状态*/
        void onMusicChanged(MusicChangedStatus musicChangedStatus);
    }

    public DiscView(Context context) {
        super(context);
    }

    public DiscView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DiscView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //初始化完成
        initDiscBlackGround();//白色背景初始化
        initViewFlipper();
        initNeedle();
        initObjectAnimator();
    }

    private void initDiscBlackGround() {
        ImageView mDiscBlackGround = findViewById(R.id.ivDiscBlackGround);
        mDiscBlackGround.setImageDrawable(getDiscBlackGroundDrawable());
        int marginTop = UIUtils.getInstance().getHeight(DISC_MARGIN_TOP);
        int width = UIUtils.getInstance().getWidth(DISC_BG_WIDTH);
        ViewCalculateUtil.setViewRelativeLayoutParam(mDiscBlackGround,
                width, width, marginTop, 0, 0, 0);
    }

    private void initViewFlipper() {
        mViewFlipper = findViewById(R.id.vfDiscContain);
        mViewFlipper.setOverScrollMode(View.OVER_SCROLL_NEVER);//关闭渐变
        int marginTop = UIUtils.getInstance().getHeight(DISC_MARGIN_TOP);
        int height = UIUtils.getInstance().getWidth(DISC_BG_WIDTH);
        ViewCalculateUtil.setViewRelativeLayoutParam(mViewFlipper,
                1080, height, marginTop, 0, 0, 0);

        mViewFlipper.setOnPageChangeListener(new MyViewFlipper.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, float positionOffsetPixels) {
                Log.e("滑动", "" + positionOffsetPixels);
                if (positionOffsetPixels > 0) {
                    //上一曲
                    int previous = mViewFlipper.getPreviousItem();
//                    Log.e("右滑", previous + "   mCurrentItem " + mViewFlipper.getCurrentItem());
                    if (otherPosterRes != mMusicData.get(previous).getMusicPicRes()) {
                        otherPosterRes = mMusicData.get(previous).getMusicPicRes();
                        mViewFlipper.getOtherPosterView().setImageDrawable(
                                getDiscPosterDrawable(otherPosterRes));
                    }
                    if (positionOffset > 0.5) {
                        notifyMusicInfoChanged(previous);
                    } else {
                        notifyMusicInfoChanged(position);
                    }
                } else {
                    int next = mViewFlipper.getNextItem();
                    Log.e("左滑", next + "   mCurrentItem " + mViewFlipper.getCurrentItem());
                    if (otherPosterRes != mMusicData.get(next).getMusicPicRes()) {
                        otherPosterRes = mMusicData.get(next).getMusicPicRes();
                        mViewFlipper.getOtherPosterView().setImageDrawable(
                                getDiscPosterDrawable(otherPosterRes));
                    }
                    if (positionOffset < 0.5) {
                        notifyMusicInfoChanged(position);
                    } else {
                        notifyMusicInfoChanged(next);
                    }
                }
            }

            @Override
            public void onPageSelected(int position, boolean isNext) {
                Log.e("onPageSelected", position + "");
                resetOtherDiscAnimation();
                notifyMusicPicChanged(position);
                notifyMusicInfoChanged(position);
                if (isNext) {
                    notifyMusicStatusChanged(MusicChangedStatus.NEXT);
                } else {
                    notifyMusicStatusChanged(MusicChangedStatus.PREVIOUS);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                doWithAnimatorOnPageScroll(state);
            }
        });
    }

    /**
     * 取消其他页面上的动画，并将图片旋转角度复原
     */
    private void resetOtherDiscAnimation() {
        mDiscAnimators.get(mViewFlipper.getOtherItem()).cancel();
        mViewFlipper.getOtherView().setRotation(0);
    }

    private void doWithAnimatorOnPageScroll(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE:
            case ViewPager.SCROLL_STATE_SETTLING: {
                mViewFlipperIsOffset = false;
                if (musicStatus == MusicStatus.PLAY) {
                    playAnimator();
                }
                break;
            }
            case ViewPager.SCROLL_STATE_DRAGGING: {
                mViewFlipperIsOffset = true;
                pauseAnimator();
                break;
            }
        }
    }

    private void initNeedle() {
        mIvNeedle = findViewById(R.id.ivNeedle);

        ViewCalculateUtil.setViewRelativeLayoutParam(mIvNeedle, NEEDLE_WIDTH, NEEDLE_HEIGHT, -NEEDLE_MARGIN_TOP, 0, NEEDLE_MARGIN_LEFT, 0, false);
        int pivotX = UIUtils.getInstance().getWidth(NEEDLE_PIVOT_X);
        int pivotY = UIUtils.getInstance().getHeight(NEEDLE_PIVOT_Y);

        mIvNeedle.setPivotX(pivotX);
        mIvNeedle.setPivotY(pivotY);
        mIvNeedle.setRotation(ROTATION_INIT_NEEDLE);
    }

    /*得到唱盘背后半透明的圆形背景*/
    private Drawable getDiscBlackGroundDrawable() {
        int discSize = UIUtils.getInstance().getWidth(DISC_BG_WIDTH);
        Bitmap bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R
                .drawable.ic_disc_blackground), discSize, discSize, false);
        RoundedBitmapDrawable roundDiscDrawable = RoundedBitmapDrawableFactory.create
                (getResources(), bitmapDisc);
        return roundDiscDrawable;
    }

    private void initObjectAnimator() {
        mNeedleAnimator = ObjectAnimator.ofFloat(mIvNeedle, View.ROTATION,ROTATION_INIT_NEEDLE, 0);
        mNeedleAnimator.setDuration(DURATION_NEEDLE_ANIMATOR);
        mNeedleAnimator.setInterpolator(new AccelerateInterpolator());
        mNeedleAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                /**
                 * 根据动画开始前NeedleAnimatorStatus的状态，
                 * 即可得出动画进行时NeedleAnimatorStatus的状态
                 * */
                if (needleAnimatorStatus == NeedleAnimatorStatus.IN_FAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.TO_NEAR_END;
                } else if (needleAnimatorStatus == NeedleAnimatorStatus.IN_NEAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.TO_FAR_END;
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {

                if (needleAnimatorStatus == NeedleAnimatorStatus.TO_NEAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.IN_NEAR_END;
                    playDiscAnimator();
                    musicStatus = MusicStatus.PLAY;
                } else if (needleAnimatorStatus == NeedleAnimatorStatus.TO_FAR_END) {
                    needleAnimatorStatus = NeedleAnimatorStatus.IN_FAR_END;
                    if (musicStatus == MusicStatus.STOP) {
                        mIsNeed2StartPlayAnimator = true;
                    }
                }

                if (mIsNeed2StartPlayAnimator) {
                    mIsNeed2StartPlayAnimator = false;
                    /**
                     * 只有在ViewFlipper不处于偏移状态时，才开始唱盘旋转动画
                     * */
                    if (!mViewFlipperIsOffset) {
                        /*延时500ms*/
                        DiscView.this.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                playAnimator();
                            }
                        }, 50);
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public void setPlayInfoListener(IPlayInfo listener) {
        this.mIPlayInfo = listener;
    }

    /**
     * 得到唱盘图片
     */
    private Bitmap getDiscDrawable() {
        int discSize = UIUtils.getInstance().getWidth(DISC_BLACK_WIDTH);
        Bitmap bitmapDisc = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_disc), discSize, discSize, false);
        return bitmapDisc;
    }

    //获取封面图，切圆角
    private Drawable getDiscPosterDrawable(int musicPicRes) {
        int musicPicSize = UIUtils.getInstance().getWidth(DISC_POSTER_WIDTH);
        Bitmap bitmapMusicPic = getMusicPicBitmap(musicPicSize, musicPicRes);
        RoundedBitmapDrawable roundMusicDrawable = RoundedBitmapDrawableFactory.create
                (getResources(), bitmapMusicPic);
        roundMusicDrawable.setCornerRadius(musicPicSize / 2);
        roundMusicDrawable.setAntiAlias(true);
        return roundMusicDrawable;
    }

    private Bitmap getMusicPicBitmap(int musicPicSize, int musicPicRes) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), musicPicRes, options);
        int imageWidth = options.outWidth;

        int sample = imageWidth / musicPicSize;
        int dstSample = 1;
        if (sample > dstSample) {
            dstSample = sample;
        }
        options.inJustDecodeBounds = false;
        //设置图片采样率
        options.inSampleSize = dstSample;
        //设置图片解码格式
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(),
                musicPicRes, options), musicPicSize, musicPicSize, true);
    }

    public void setMusicDataList(List<MusicData> musicDataList) {
        if (musicDataList.isEmpty()) return;

        mMusicData.clear();
        mDiscAnimators.clear();
        mMusicData.addAll(musicDataList);
        mViewFlipper.setMusicSize(musicDataList.size());
        //获取第一首歌曲信息
        MusicData musicData = mMusicData.get(0);
        otherPosterRes = musicData.getMusicPicRes();
        //添加碟片
        for (int i = 0; i < 2; i++) {
            View discLayout = LayoutInflater.from(getContext()).inflate(R.layout.netease_layout_music_play_disc,
                    mViewFlipper, false);

            ImageView disc = discLayout.findViewById(R.id.ivDisc);
            disc.setImageBitmap(getDiscDrawable());
            ImageView poster = discLayout.findViewById(R.id.ivPoster);
            poster.setImageDrawable(getDiscPosterDrawable(otherPosterRes));
            ViewCalculateUtil.setViewRelativeLayoutParam(disc, DISC_BLACK_WIDTH,
                    DISC_BLACK_WIDTH, 3, 0, 0, 0, true);
            ViewCalculateUtil.setViewRelativeLayoutParam(poster, DISC_POSTER_WIDTH,
                    DISC_POSTER_WIDTH, 0, 0, 0, 0, true);
            mDiscAnimators.add(getDiscObjectAnimator(discLayout));
            mViewFlipper.addView(discLayout);
        }

        //接口回调通知图片更新
        if (mIPlayInfo != null) {
            mIPlayInfo.onMusicInfoChanged(musicData.getMusicName(), musicData.getMusicAuthor());
            mIPlayInfo.onMusicPicChanged(musicData.getMusicPicRes());
        }
    }

    //获取唱片的动画
    private ObjectAnimator getDiscObjectAnimator(View disc) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(disc, View.ROTATION, 0, 360);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.setDuration(20 * 1000);
        objectAnimator.setInterpolator(new LinearInterpolator());

        return objectAnimator;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /*播放动画*/
    private void playAnimator() {
        /*唱针处于远端时，直接播放动画*/
        if (needleAnimatorStatus == NeedleAnimatorStatus.IN_FAR_END) {
            mNeedleAnimator.start();
        }
        /*唱针处于往远端移动时，设置标记，等动画结束后再播放动画*/
        else if (needleAnimatorStatus == NeedleAnimatorStatus.TO_FAR_END) {
            mIsNeed2StartPlayAnimator = true;
        }
    }

    /*暂停动画*/
    private void pauseAnimator() {
        /*播放时暂停动画*/
        if (needleAnimatorStatus == NeedleAnimatorStatus.IN_NEAR_END) {
//            int index = mViewFlipper.getCurrentItem();
            pauseDiscAnimator();
        }
        /*唱针往唱盘移动时暂停动画*/
        else if (needleAnimatorStatus == NeedleAnimatorStatus.TO_NEAR_END) {
            mNeedleAnimator.reverse();
            /**
             * 若动画在没结束时执行reverse方法，则不会执行监听器的onStart方法，此时需要手动设置
             * */
            needleAnimatorStatus = NeedleAnimatorStatus.TO_FAR_END;
        }
        /**
         * 动画可能执行多次，只有音乐处于停止 / 暂停状态时，才执行暂停命令
         * */
        if (musicStatus == MusicStatus.STOP) {
            notifyMusicStatusChanged(MusicChangedStatus.STOP);
        } else if (musicStatus == MusicStatus.PAUSE) {
            notifyMusicStatusChanged(MusicChangedStatus.PAUSE);
        }
    }

    /*播放唱盘动画*/
    private void playDiscAnimator() {
        ObjectAnimator objectAnimator = mDiscAnimators.get(mViewFlipper.getDisplayedChild());
        if (objectAnimator.isPaused()) {
            objectAnimator.resume();
        } else {
            objectAnimator.start();
        }
        /**
         * 唱盘动画可能执行多次，只有不是音乐不在播放状态，在回调执行播放
         * */
        if (musicStatus != MusicStatus.PLAY) {
            notifyMusicStatusChanged(MusicChangedStatus.PLAY);
        }
    }

    /*暂停唱盘动画*/
    private void pauseDiscAnimator() {
        ObjectAnimator objectAnimator = mDiscAnimators.get(mViewFlipper.getDisplayedChild());
        objectAnimator.pause();
        mNeedleAnimator.reverse();
    }

    public void notifyMusicInfoChanged(int position) {
        if (mIPlayInfo != null) {
            MusicData musicData = mMusicData.get(position);
            mIPlayInfo.onMusicInfoChanged(musicData.getMusicName(), musicData.getMusicAuthor());
        }
    }

    public void notifyMusicPicChanged(int position) {
        if (mIPlayInfo != null) {
            MusicData musicData = mMusicData.get(position);
            mIPlayInfo.onMusicPicChanged(musicData.getMusicPicRes());
        }
    }

    public void notifyMusicStatusChanged(MusicChangedStatus musicChangedStatus) {
        if (mIPlayInfo != null) {
            mIPlayInfo.onMusicChanged(musicChangedStatus);
        }
    }

    private void play() {
        playAnimator();
    }

    private void pause() {
        musicStatus = MusicStatus.PAUSE;
        pauseAnimator();
    }

    public void stop() {
        musicStatus = MusicStatus.STOP;
        pauseAnimator();
    }

    public void playOrPause() {
        if (musicStatus == MusicStatus.PLAY) {
            pause();
        } else {
            play();
        }
    }

    public void next() {
        //TODO 需要注意的修改项
        int next = mViewFlipper.getNextItem();
        Log.e("next", next + "");
        if (otherPosterRes != mMusicData.get(next).getMusicPicRes()) {
            otherPosterRes = mMusicData.get(next).getMusicPicRes();
            mViewFlipper.getOtherPosterView().setImageDrawable(getDiscPosterDrawable(otherPosterRes));
        }
        mViewFlipper.showNextWithAnimation();
        selectMusicWithButton();
    }

    public void previous() {
        int previous = mViewFlipper.getPreviousItem();
        Log.e("previous", previous + "");
        if (otherPosterRes != mMusicData.get(previous).getMusicPicRes()) {
            otherPosterRes = mMusicData.get(previous).getMusicPicRes();
            mViewFlipper.getOtherPosterView().setImageDrawable(getDiscPosterDrawable(otherPosterRes));
        }
        mViewFlipper.showPreviousWithAnimation();
        selectMusicWithButton();
    }

    public boolean isPlaying() {
        return musicStatus == MusicStatus.PLAY;
    }

    private void selectMusicWithButton() {
        if (musicStatus == MusicStatus.PLAY) {
            mIsNeed2StartPlayAnimator = true;
            pauseAnimator();
        } else if (musicStatus == MusicStatus.PAUSE) {
            play();
        }
    }
}