package com.kelly.test.widget.svg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.graphics.PathParser;

import com.kelly.test.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class ChinaMapView extends View {
    private int[] colorArray = new int[]{0xFF239BD7, 0xFF30A9E5, 0xFF80CBF1, 0xFFFFFFFF};
    private Context context;
    private List<ProvinceItem> itemList;
    private Paint paint;
    private ProvinceItem select;
    private RectF totalRect;
    private float scale=1.0f;
    public ChinaMapView(Context context) {
        super(context);
    }

    public ChinaMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)   {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        itemList = new ArrayList<>();
        loadThread.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取到当前控件宽高值
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (totalRect != null) {
            double mapWidth = totalRect.width();
            scale= (float) (width / mapWidth);
        }

        setMeasuredDimension(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(height , MeasureSpec.EXACTLY));

    }

    private Thread loadThread = new Thread() {
        @Override
        public void run() {
            final InputStream inputStream = context.getResources().openRawResource(R.raw.china);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  //取得DocumentBuilderFactory实例
            DocumentBuilder builder; //从factory获取DocumentBuilder实例
            try {
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(inputStream);   //解析输入流 得到Document实例
                Element rootElement = doc.getDocumentElement();
                NodeList items = rootElement.getElementsByTagName("path");
                float left = -1;
                float right = -1;
                float top = -1;
                float bottom = -1;
                List<ProvinceItem> list = new ArrayList<>();
                for (int i = 0; i < items.getLength(); i++) {
                    Element element = (Element) items.item(i);
                    String pathData = element.getAttribute("android:pathData");
                    @SuppressLint("RestrictedApi") Path path = PathParser.createPathFromPathData(pathData);
                    ProvinceItem provinceItem = new ProvinceItem(path);
                    provinceItem.setDrawColor(colorArray[i % 4]);
                    RectF rect = new RectF();
                    path.computeBounds(rect, true);
                    left = left == -1 ? rect.left : Math.min(left, rect.left);
                    right = right == -1 ? rect.right : Math.max(right, rect.right);
                    top = top == -1 ? rect.top : Math.min(top, rect.top);
                    bottom = bottom == -1 ? rect.bottom : Math.max(bottom, rect.bottom);
                    list.add(provinceItem);
                }
                itemList = list;
                totalRect = new RectF(left, top, right, bottom);
                // 刷新界面
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    requestLayout();
                    invalidate();
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        handleTouch(event.getX()/scale, event.getY()/scale);
        return super.onTouchEvent(event);
    }

    private void handleTouch(float x, float y) {
        if (itemList == null) {
            return;
        }
        ProvinceItem selectItem = null;
        for (ProvinceItem provinceItem : itemList) {
            if (provinceItem.isTouch(x, y)) {
                selectItem = provinceItem;
            }
        }
        if (selectItem != null) {
            select = selectItem;
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (itemList != null) {
            canvas.save();
            canvas.scale(scale,scale);
            for (ProvinceItem provinceItem : itemList) {
                if (provinceItem != select) {
                    provinceItem.drawItem(canvas, paint, false);
                }else {
                    select.drawItem(canvas, paint, true);
                }
            }
        }

    }
}
