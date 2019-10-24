package com.kelly.test.neteasy.musiclist;

import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.kelly.test.R;
import com.kelly.test.neteasy.musiclist.ui.UIUtils;
import com.kelly.test.neteasy.musiclist.ui.ViewCalculateUtil;
import com.kelly.test.neteasy.musiclist.util.FastBlurUtil;
import com.kelly.test.neteasy.musiclist.util.StatusBarUtil;
import com.kelly.test.neteasy.musiclist.util.ToolbarUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicListActivity extends AppCompatActivity {
    private Toolbar toolbar;

    private ListView listView;
    private View headView;
    private ImageView iv_header_bg;//顶部背景图片

    private ImageView iv_header_poster;//head中的封面

    private LinearLayout ll_layout6;//head中的封面右边布局
    private LinearLayout ll_layout5;//head中的播放全部布局
    private LinearLayout ll_layout1;//head中的封面图和标题等的包裹布局
    private LinearLayout ll_layout2;//head中的评论下载等按钮的包裹布局
    private LinearLayout ll_layout3;//head中的会员享高品质及下方的悬浮区域的包裹布局
    private LinearLayout ll_layout4;//head中的会员享高品质布局
    private TextView tv_music_list_name;//head中歌单名字
    private FrameLayout fl_layout_float;//真正悬浮的view包裹布局
    private LinearLayout ll_layout_float;//真正悬浮的view布局

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        UIUtils.getInstance(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.more));
        toolbar.setTitle("歌单");
        toolbar.setSubtitle("热门推荐");
        //设置跑马灯效果
        ToolbarUtils.setMarqueeForToolbarTitleView(toolbar);
        setSupportActionBar(toolbar);
        //设置沉浸式
        StatusBarUtil.setStateBar(this, toolbar);

        headView = getLayoutInflater().inflate(R.layout.item_music_list_header, null);
        iv_header_poster = headView.findViewById(R.id.iv_header_poster);
        ll_layout6 = headView.findViewById(R.id.ll_layout6);
        ll_layout5 = headView.findViewById(R.id.ll_layout5);
        ll_layout1 = headView.findViewById(R.id.ll_layout1);
        ll_layout2 = headView.findViewById(R.id.ll_layout2);
        ll_layout3 = headView.findViewById(R.id.ll_layout3);
        ll_layout4 = headView.findViewById(R.id.ll_layout4);
        iv_header_bg = headView.findViewById(R.id.iv_header_bg);

        tv_music_list_name = headView.findViewById(R.id.tv_music_list_name);

        fl_layout_float = findViewById(R.id.fl_layout_float);
        ll_layout_float = findViewById(R.id.ll_layout_float);

        listView = findViewById(R.id.listView);
        //适配
        ViewCalculateUtil.setViewLinearLayoutParam(iv_header_poster, 378, 378, 0, 0, 48, 0, true);
        ViewCalculateUtil.setViewLinearLayoutParam(ll_layout6, 552, 378, 0, 0, 42, 0, true);
        //添加HeadView
        listView.addHeaderView(headView);

        //初始化歌单数据
        List<Map<String, Object>> listItems = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            Map<String, Object> listItem = new HashMap<>();
            listItem.put("music_no", i);
            listItem.put("music_name", "歌曲" + i);
            listItem.put("auth_name", "歌手" + i);
            //添加到集合
            listItems.add(listItem);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems, R.layout.item_music_list,
                new String[]{"music_no", "music_name", "auth_name"},
                new int[]{R.id.item_position, R.id.music_name, R.id.item_auth});
        listView.setAdapter(simpleAdapter);
        try2UpdatePicBackground(R.drawable.pic_music_list_header_poster);
        //根据listView的滑动改变界面内容的透明度
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //状态
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //滑动的距离
                float originalHeight = headView.getHeight() - toolbar.getHeight() - ll_layout5.getHeight();
                //计算当前滑动距离
                float currentHeight = headView.getBottom() - toolbar.getHeight() - ll_layout5.getHeight();
                if (originalHeight == 0) {//防止出错
                    return;
                }
                //获取歌单名称相对于屏幕顶部的距离
                Rect rect = new Rect();
                tv_music_list_name.getGlobalVisibleRect(rect);
                Log.e("test", rect.top + "");
                if (rect.top < 10) {
                    //改成对应的标题名
                    if (!toolbar.getTitle().equals(getResources().getText(R.string.list_name))) {
                        toolbar.setTitle(getResources().getText(R.string.list_name));
                    }
                } else if (rect.top > 10 && currentHeight > 0) {
                    //改为默认的名称
                    if (!toolbar.getTitle().equals(getResources().getText(R.string.list_name_default))) {
                        toolbar.setTitle(getResources().getText(R.string.list_name_default));
                    }
                }
                if (currentHeight > 0) {
                    //计算拖动比
                    float part = currentHeight / originalHeight;
                    ll_layout1.setAlpha(1.4f * part);
                    ll_layout2.setAlpha(1.4f * part);
                    ll_layout4.setAlpha(1.4f * part);
                    ll_layout3.getBackground().setAlpha((int) (255 * part));
                    //注意(mutate)
                    Drawable background = toolbar.getBackground().mutate();
                    background.setAlpha((int) (255 * (1 - part)));
                    toolbar.setBackground(background);
                    fl_layout_float.setVisibility(View.GONE);
                } else {
                    //计算拖动比
                    float part = currentHeight / originalHeight;
                    ll_layout1.setAlpha(0);
                    ll_layout2.setAlpha(0);
                    ll_layout4.setAlpha(0);
                    ll_layout3.getBackground().setAlpha(0);
                    //注意(mutate)
                    Drawable background = toolbar.getBackground().mutate();
                    background.setAlpha(255);
                    toolbar.setBackground(background);
                    fl_layout_float.setVisibility(View.VISIBLE);
                    fl_layout_float.setBackground(fl_layout_float.getBackground());
                }

            }
        });
    }

    private void try2UpdatePicBackground(final int picRes) {
        //后台帮我们处理
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Drawable blurDrawable = getBackgroundDrawable(picRes);
                final Drawable blurDrawable1 = getBackgroundDrawable(picRes);
                final Drawable blurDrawable2 = getBackgroundDrawable(picRes);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fl_layout_float.setBackground(blurDrawable);
                        iv_header_bg.setImageDrawable(blurDrawable1);
                        toolbar.setBackground(blurDrawable2);
                    }
                });
            }
        }).start();
    }

    private Drawable getBackgroundDrawable(int picRes) {
        /*得到屏幕的宽高比，以便按比例切割图片一部分*/
        final float widthHeightSize = (float) (UIUtils.getScreenWidth(this)
                * 1.0 / UIUtils.getScreenHeight(this) * 1.0);

        Bitmap bitmap = getBackgroundBitmap(picRes);

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

        final Drawable foregroundDrawable = new BitmapDrawable(getResources(), blurBitmap);
        /*加入灰色遮罩层，避免图片过亮影响其他控件*/
        foregroundDrawable.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        return foregroundDrawable;

    }

    private Bitmap getBackgroundBitmap(int picRes) {
        int screenWidth = UIUtils.getScreenWidth(this);
        int screenHeight = UIUtils.getScreenHeight(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), picRes, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;

        if (imageWidth < screenWidth && imageHeight < screenHeight) {
            return BitmapFactory.decodeResource(getResources(), picRes);
        }

        int sample = 2;
        int sampleX = imageWidth / UIUtils.getScreenWidth(this);
        int sampleY = imageHeight / UIUtils.getScreenHeight(this);

        if (sampleX > sampleY && sampleY > 1) {
            sample = sampleX;
        } else if (sampleY > sampleX && sampleX > 1) {
            sample = sampleY;
        }

        options.inJustDecodeBounds = false;
        options.inSampleSize = sample;
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        return BitmapFactory.decodeResource(getResources(), picRes, options);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_music, menu);
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }

        return true;
    }
}
