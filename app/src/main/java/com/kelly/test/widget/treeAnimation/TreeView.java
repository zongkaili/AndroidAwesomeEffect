package com.kelly.test.widget.treeAnimation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Iterator;
import java.util.LinkedList;

public class TreeView extends View {
    //存储当前需要绘制的树枝
    LinkedList<Branch> growingBranches;
    private Paint mPaint;
    private Canvas treeCanvas;
    private Bitmap mBitmap;

    public TreeView(Context context) {
        this(context, null);
    }

    public TreeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TreeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(0xff000000);
        mPaint.setTextSize(100);
        growingBranches = new LinkedList<>();
        growingBranches.add(getBranches());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //绘制的内容保存进入Bitmap
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //treeCanvas绘制的内容都会保存在该Bitmap中
        treeCanvas = new Canvas(mBitmap);
    }

    public static Branch getBranches() {//树枝数据初始化
        // id, parentId, bezier control points(3 points, in 6 columns), max radius，length
        int[][] data = new int[][]{
                {0, -1, 217, 490, 252, 60, 182, 10, 30, 100},
                {1, 0, 222, 310, 137, 227, 22, 210, 13, 100},
                {2, 1, 132, 245, 116, 240, 76, 205, 2, 40},
                {3, 0, 232, 255, 282, 166, 362, 155, 12, 100},
                {4, 3, 260, 210, 330, 219, 343, 236, 3, 80},
                {5, 0, 217, 91, 219, 58, 216, 27, 3, 40},
                {6, 0, 228, 207, 95, 57, 10, 54, 9, 80},
                {7, 6, 109, 96, 65, 63, 53, 15, 2, 40},
                {8, 6, 180, 155, 117, 125, 77, 140, 4, 60},
                {9, 0, 228, 167, 290, 62, 360, 31, 6, 100},
                {10, 9, 272, 103, 328, 87, 330, 81, 2, 80}
        };
        int n = data.length;//获取树枝总数
        Branch[] branches = new Branch[n];
        for (int i = 0; i < n; i++) {//循环给每一个树枝分配数据
            branches[i] = new Branch(data[i]);
            int parent = data[i][1];//获取每组数据的第二个数据值，判断当前树枝的父分枝
            if (parent != -1) {//有父分支，将当前分支加入到父分枝中
                branches[parent].addChild(branches[i]);
            }
        }
        return branches[0];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制单次新增内容 treeCanvas
        drawBranches();
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        //保存上一次绘制的内容
//        canvas.drawText(String.valueOf(n), n * 20, n * 20, mPaint);
//        n++;
//        postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                postInvalidate();
//            }
//        }, 1000);
        //1(保存上一次的绘制内容)
    }

    private void drawBranches() {
        //绘制之前先要获取到此次绘制需要的树枝
        if (!growingBranches.isEmpty()) {
            int scaleFraction = 2;
            //需要继续绘制
            treeCanvas.save();
            //217, 490
            treeCanvas.translate(getWidth() / 2 - 217*scaleFraction, getHeight() - 490*scaleFraction);
            LinkedList<Branch> tempBranches = null;
            Iterator<Branch> iterator = growingBranches.iterator();
            while (iterator.hasNext()) {
                Branch branch = iterator.next();
                if (!branch.grow(treeCanvas, mPaint, scaleFraction)) {
                    iterator.remove();
                    if (branch.childList != null) {
                        //有分枝需要绘制
                        if (tempBranches == null) {
                            tempBranches = branch.childList;
                        } else {
                            tempBranches.addAll(branch.childList);
                        }
                    }
                }
            }
            treeCanvas.restore();
            if (tempBranches != null) {
                growingBranches.addAll(tempBranches);
            }
        }
        if (!growingBranches.isEmpty()) {
            //继续绘制
            invalidate();
        }
        //停止
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //资源释放

    }
}
