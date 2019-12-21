package com.kelly.effect.widget.treeAnimation;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

import java.util.LinkedList;

/**
 * 树枝
 */
public class Branch {
    public static int branchColor = 0xFFFF9988;
    //控制点
    private PointF[] cp = new PointF[3];
    private int currentLength;
    private int maxLength;
    private float radius;//树枝的半径，这里不能是int
    private float part;//一共分成多少份去绘制
    private float growX;
    private float growY;
    //存储分枝
    LinkedList<Branch> childList;

    public Branch(int data[]) {
        // id, parentId, bezier control points(3 points, in 6 columns), max radius，length
        //{0, -1, 217, 490, 252, 60, 182, 10, 30, 100},
        cp[0] = new PointF(data[2], data[3]);
        cp[1] = new PointF(data[4], data[5]);
        cp[2] = new PointF(data[6], data[7]);
        radius = data[8];
        maxLength = data[9];
        part = 1.0f / maxLength;
//        Log.e("length", maxLength + "  " + part);
    }

    public void addChild(Branch branch) {
        if (childList == null) {
            childList = new LinkedList<>();
        }
        childList.add(branch);
    }

    public boolean grow(Canvas treeCanvas, Paint paint, int scaleFraction) {
        if (currentLength < maxLength) {//不要取等于
            //需要绘制当前的树枝
            //计算当前绘制的点
            bezier(part * currentLength);
            draw(treeCanvas, paint, scaleFraction);
            currentLength++;
            Log.e("currentLength", currentLength + "");
            radius *= 0.97f;
            return true;
        } else {
            return false;
        }
    }

    private void draw(Canvas treeCanvas, Paint paint, int scaleFraction) {
        paint.setColor(branchColor);
        treeCanvas.save();
        treeCanvas.scale(scaleFraction, scaleFraction);
//        treeCanvas.translate(growX, growY);
        treeCanvas.drawCircle(growX, growY, radius, paint);
        treeCanvas.restore();
    }

    private void bezier(float t) {
        //通过贝塞尔曲线公式计算当前的点
//        Path path = new Path();
//        path.quadTo();
//        path.cubicTo();
        //四阶怎么办
//
//        //获取当前绘制的点
//        PathMeasure measure = new PathMeasure(path, false);
//        measure.getPosTan(currentLength, pos, tan);
        //三阶贝塞尔曲线公式：(1-t)^2 * Px + 2t(1-t) * Px + t^2 * Px
        float c0 = (1 - t) * (1 - t);
        float c1 = 2 * t * (1 - t);
        float c2 = t * t;
        growX = c0 * cp[0].x + c1 * cp[1].x + c2 * cp[2].x;
        growY = c0 * cp[0].y + c1 * cp[1].y + c2 * cp[2].y;
    }
}
