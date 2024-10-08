package com.kelly.effect.neteasy.playpage;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.kelly.effect.R;
import com.kelly.effect.neteasy.musiclist.ui.UIUtils;
import com.kelly.effect.neteasy.musiclist.util.FastBlurUtil;
import com.kelly.effect.neteasy.musiclist.util.StatusBarUtil;
import com.kelly.effect.neteasy.musiclist.util.ToolbarUtils;
import com.kelly.effect.neteasy.playpage.model.MusicData;
import com.kelly.effect.neteasy.playpage.service.MusicService;
import com.kelly.effect.neteasy.playpage.view.DiscView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MusicPlayActivity extends AppCompatActivity implements DiscView.IPlayInfo, View.OnClickListener {
    private ImageSwitcher bg_switcher;//背景切换
    private int bg_pic_res = -1;//背景资源ID
    private DiscView mDisc;
    private Toolbar mToolbar;
    private SeekBar mSeekBar;
    private ImageView mIvPlayOrPause, mIvNext, mIvPrevious;//播放/暂停按钮 下一曲 上一曲
    private TextView mTvMusicDuration, mTvTotalMusicDuration;//当前播放时长 总时长

    public static final int MUSIC_MESSAGE = 0;

    @SuppressLint("HandlerLeak")
    private Handler mMusicHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mSeekBar.setProgress(mSeekBar.getProgress() + 1000);
            mTvMusicDuration.setText(duration2Time(mSeekBar.getProgress()));
            startUpdateSeekBarProgress();
        }
    };

    private MusicReceiver mMusicReceiver = new MusicReceiver();//广播接收者
    private List<MusicData> musicData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        initMusicData();//初始化音乐数据
        initView();
        initMusicReceiver();
    }


    private void initMusicData() {
        MusicData musicData1 = new MusicData(R.raw.music1, R.raw.poster1, "我喜欢", "梁静茹");
        MusicData musicData2 = new MusicData(R.raw.music2, R.raw.poster2, "想把我唱给你听", "老狼");
        MusicData musicData3 = new MusicData(R.raw.music3, R.raw.poster3, "风再起时", "张国荣");
        //添加歌曲
        musicData.add(musicData1);
        musicData.add(musicData2);
        musicData.add(musicData3);
        //开启播放服务
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(MusicService.PARAM_MUSIC_LIST, (Serializable) musicData);
        startService(intent);
    }

    private void initView() {
        mDisc = findViewById(R.id.discview);
        mIvNext = findViewById(R.id.ivNext);
        mIvPrevious = findViewById(R.id.ivPrevious);
        mIvPlayOrPause = findViewById(R.id.ivPlayOrPause);
        mTvMusicDuration = findViewById(R.id.tvCurrentTime);
        mTvTotalMusicDuration = findViewById(R.id.tvTotalTime);
        mSeekBar = findViewById(R.id.musicSeekBar);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle("歌单");
        ToolbarUtils.setMarqueeForToolbarTitleView(mToolbar);
        //设置沉浸式，并且toolbar设置padding
        StatusBarUtil.setStateBar(this, mToolbar);
        //只能够有两个ImageView
        bg_switcher = findViewById(R.id.bg_switcher);
        bg_switcher.setFactory(new ViewSwitcher.ViewFactory() {

            @Override
            public View makeView() {
                //调用两次
                // makeView返回的是当前需要显示的ImageView控件，用于填充进ImageSwitcher中。
                ImageView imageView = null;
                imageView = new ImageView(MusicPlayActivity.this);//实例化一个ImageView类的对象
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);//设置保持纵横比居中缩放图像
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return imageView;
            }
        });
        bg_switcher.setImageResource(R.drawable.bg_defalut);
        Animation animationIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation animationOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        animationIn.setDuration(500);
        animationOut.setDuration(500);
        bg_switcher.setInAnimation(animationIn);//设置淡入动画
        bg_switcher.setOutAnimation(animationOut);//设置淡出动画
        //唱盘设置播放监听
        mDisc.setPlayInfoListener(this);
        mIvPrevious.setOnClickListener(this);
        mIvNext.setOnClickListener(this);
        mIvPlayOrPause.setOnClickListener(this);
        //监听进度条进度变化
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //改变当前播放进度
                mTvMusicDuration.setText(duration2Time(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                startUpdateSeekBarProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //拖动进度结束（音乐进度更新）
                seekTo(seekBar.getProgress());
                stopUpdateSeekBarProgress();
            }
        });

        mTvMusicDuration.setText(duration2Time(0));
        mTvTotalMusicDuration.setText(duration2Time(0));
        mDisc.setMusicDataList(musicData);
    }

    private void initMusicReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_PLAY);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_PAUSE);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_DURATION);
        intentFilter.addAction(MusicService.ACTION_STATUS_MUSIC_COMPLETE);
        /*注册本地广播*/
        LocalBroadcastManager.getInstance(this).registerReceiver(mMusicReceiver, intentFilter);
    }

    private void try2UpdateMusicPicBackground(final int musicPicRes) {
        if (isNeed2UpdateBackground(musicPicRes)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Drawable foregroundDrawable = getForegroundDrawable(musicPicRes);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bg_switcher.setImageDrawable(foregroundDrawable);
                        }
                    });
                }
            }).start();
        }
    }

    public boolean isNeed2UpdateBackground(int musicPicRes) {
        if (this.bg_pic_res == -1) return true;
        if (musicPicRes != this.bg_pic_res) {
            return true;
        }
        return false;
    }

    private Drawable getForegroundDrawable(int musicPicRes) {
        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        final float widthHeightSize = (float) (UIUtils.getScreenWidth(this)
                * 1.0 / UIUtils.getScreenHeight(this) * 1.0);

        Bitmap bitmap = getForegroundBitmap(musicPicRes);
        int cropBitmapWidth = (int) (widthHeightSize * bitmap.getHeight());
        int cropBitmapWidthX = (int) ((bitmap.getWidth() - cropBitmapWidth) / 2.0);

        /*切割部分图片*/
        Bitmap cropBitmap = Bitmap.createBitmap(bitmap, cropBitmapWidthX, 0, cropBitmapWidth,
                bitmap.getHeight());
        /*缩小图片*/
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(cropBitmap, bitmap.getWidth() / 50, bitmap
                .getHeight() / 50, false);
        /*模糊化*/
        final Bitmap blurBitmap = FastBlurUtil.doBlur(scaleBitmap, 8, true);

        final Drawable foregroundDrawable = new BitmapDrawable(blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;
    }

    private void stopUpdateSeekBarProgress() {
        mMusicHandler.removeMessages(MUSIC_MESSAGE);
    }

    private Bitmap getForegroundBitmap(int musicPicRes) {
        int screenWidth = UIUtils.getScreenWidth(this);
        int screenHeight = UIUtils.getScreenHeight(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), musicPicRes, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        if (imageWidth < screenWidth && imageHeight < screenHeight) {
            return BitmapFactory.decodeResource(getResources(), musicPicRes);
        }

        int sample = 2;
        int sampleX = imageWidth / screenWidth;
        int sampleY = imageHeight / screenHeight;

        if (sampleX > sampleY && sampleY > 1) {
            sample = sampleX;
        } else if (sampleY > sampleX && sampleX > 1) {
            sample = sampleY;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(getResources(), musicPicRes, options);
    }

    @Override
    public void onMusicInfoChanged(String musicName, String musicAuthor) {
        //唱盘接口回调 切歌
        getSupportActionBar().setTitle(musicName);
        getSupportActionBar().setSubtitle(musicAuthor);
    }

    @Override
    public void onMusicPicChanged(int musicPicRes) {
        //唱盘接口回调 换图
        try2UpdateMusicPicBackground(musicPicRes);
    }

    @Override
    public void onMusicChanged(DiscView.MusicChangedStatus musicChangedStatus) {
        //唱盘接口回调 音乐播放状态改变
        switch (musicChangedStatus) {
            case PLAY: {
                play();
                break;
            }
            case PAUSE: {
                pause();
                break;
            }
            case NEXT: {
                next();
                break;
            }
            case PREVIOUS: {
                previous();
                break;
            }
            case STOP: {
                stop();
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mIvPlayOrPause) {
            mDisc.playOrPause();
        } else if (v == mIvNext) {
            mDisc.next();
        } else if (v == mIvPrevious) {
            mDisc.previous();
        }
    }

    private void play() {
        optMusic(MusicService.ACTION_OPT_MUSIC_PLAY);
        startUpdateSeekBarProgress();
    }

    private void pause() {
        optMusic(MusicService.ACTION_OPT_MUSIC_PAUSE);
        stopUpdateSeekBarProgress();
    }

    private void stop() {
        stopUpdateSeekBarProgress();
        mIvPlayOrPause.setImageResource(R.drawable.icon_back);
        mTvMusicDuration.setText(duration2Time(0));
        mTvTotalMusicDuration.setText(duration2Time(0));
        mSeekBar.setProgress(0);
    }

    private void next() {
        //下一曲 歌曲延迟切换 唱针指到碟片上开始播放
        bg_switcher.postDelayed(new Runnable() {
            @Override
            public void run() {
                optMusic(MusicService.ACTION_OPT_MUSIC_NEXT);
            }
        }, DiscView.DURATION_NEEDLE_ANIMATOR);
        stopUpdateSeekBarProgress();
        mTvMusicDuration.setText(duration2Time(0));
        mTvTotalMusicDuration.setText(duration2Time(0));
    }

    private void previous() {
        //上一曲 歌曲延迟切换
        bg_switcher.postDelayed(new Runnable() {
            @Override
            public void run() {
                optMusic(MusicService.ACTION_OPT_MUSIC_PREVIOUS);
            }
        }, DiscView.DURATION_NEEDLE_ANIMATOR);
        stopUpdateSeekBarProgress();
        mTvMusicDuration.setText(duration2Time(0));
        mTvTotalMusicDuration.setText(duration2Time(0));
    }

    private void complete(boolean isOver) {
        //逻辑需要改变
//        if (isOver) {
//            mDisc.stop();
//        } else {
        mDisc.next();
//        }
    }

    private void optMusic(final String action) {
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent(action));
    }

    private void seekTo(int position) {
        Intent intent = new Intent(MusicService.ACTION_OPT_MUSIC_SEEK_TO);
        intent.putExtra(MusicService.PARAM_MUSIC_SEEK_TO, position);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void startUpdateSeekBarProgress() {
        /*避免重复发送Message*/
        stopUpdateSeekBarProgress();
        mMusicHandler.sendEmptyMessageDelayed(0, 1000);
    }

    /*根据时长格式化称时间文本*/
    private String duration2Time(int duration) {
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;
        return (min < 10 ? "0" + min : min + "") + ":" + (sec < 10 ? "0" + sec : sec + "");
    }

    /* 初始更新歌曲当前进度及歌曲总时长*/
    private void updateMusicDurationInfo(int totalDuration) {
        mSeekBar.setProgress(0);
        mSeekBar.setMax(totalDuration);
        mTvTotalMusicDuration.setText(duration2Time(totalDuration));
        mTvMusicDuration.setText(duration2Time(0));
        startUpdateSeekBarProgress();
    }

    class MusicReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            assert action != null;//断言 true继续 false 抛出异常并退出
            switch (action) {
                case MusicService.ACTION_STATUS_MUSIC_PLAY:
                    mIvPlayOrPause.setImageResource(R.drawable.ic_pause);
                    int currentPosition = intent.getIntExtra(MusicService.PARAM_MUSIC_CURRENT_POSITION, 0);
                    mSeekBar.setProgress(currentPosition);
                    if (!mDisc.isPlaying()) {
                        mDisc.playOrPause();
                    }
                    break;
                case MusicService.ACTION_STATUS_MUSIC_PAUSE:
                    mIvPlayOrPause.setImageResource(R.drawable.ic_play);
                    if (mDisc.isPlaying()) {
                        mDisc.playOrPause();
                    }
                    break;
                case MusicService.ACTION_STATUS_MUSIC_DURATION:
                    int duration = intent.getIntExtra(MusicService.PARAM_MUSIC_DURATION, 0);
                    updateMusicDurationInfo(duration);
                    break;
                case MusicService.ACTION_STATUS_MUSIC_COMPLETE:
                    boolean isOver = intent.getBooleanExtra(MusicService.PARAM_MUSIC_IS_OVER, true);
                    complete(isOver);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMusicReceiver);
        //关闭服务
        Intent intent = new Intent(this, MusicService.class);
        stopService(intent);
        if (mMusicHandler != null) {
            mMusicHandler = null;
        }
    }
}
