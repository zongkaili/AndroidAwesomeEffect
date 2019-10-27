package com.kelly.test.widget.colorfilter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

import com.kelly.test.R

class ColorFilterView : View {

    private var mPaint: Paint? = null
    private var mBitmap: Bitmap? = null
    private var mColorMatrixColorFilter: ColorMatrixColorFilter? = null

    constructor(context: Context) : super(context) {
        mPaint = Paint()
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.pic_test)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    )

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val cm = ColorMatrix()
        //设置饱和度：0-无色彩，1-默认效果，>1-饱和度加强
        cm.setSaturation(0f)
        mColorMatrixColorFilter = ColorMatrixColorFilter(cm)
        mPaint?.colorFilter = mColorMatrixColorFilter
        mBitmap?.let {
            canvas.drawBitmap(it, 0f, 0f, mPaint)
        }
    }

    companion object {

        // 胶片
        val colormatrix_fanse = floatArrayOf(
            -1.0f,
            0.0f,
            0.0f,
            0.0f,
            255.0f,
            0.0f,
            -1.0f,
            0.0f,
            0.0f,
            255.0f,
            0.0f,
            0.0f,
            -1.0f,
            0.0f,
            255.0f,
            0.0f,
            0.0f,
            0.0f,
            1.0f,
            0.0f
        )
    }
}
