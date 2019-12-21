package com.kelly.effect.widget.irregularview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * author: zongkaili
 * data: 2019-10-05
 * desc:
 */
class ChromeIconView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {
    private lateinit var mPaint: Paint
    private var mWidth: Int = -1
    private var mHeight: Int = -1
    private var cx: Float = -1F
    private var cy: Float = -1F
    private var bitmap: Bitmap? = null
    private var canvasTemp: Canvas? = null
    private val colors = arrayOf(Color.RED, Color.YELLOW, Color.GREEN, Color.WHITE, Color.BLUE)

    init {
        initView()
    }

    fun initView() {
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL
        mPaint.strokeWidth = 2F
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        cx = (mWidth / 2).toFloat()
        cy = (mHeight / 2).toFloat()
        bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888)
        //创建新的画布解决图片的问题
        canvasTemp = Canvas(bitmap)
    }

    override fun onDraw(canvas: Canvas?) {
        val outerCr = 100
        val innerCr = 50
        val innerRectF = RectF(cx - innerCr, cy - innerCr, cx + innerCr, cy + innerCr)
        val outerRectF = RectF(cx - outerCr, cy - outerCr, cx + outerCr, cy + outerCr)

        val path = Path()
        //获取红色区域
        path.addArc(innerRectF, 150F, 120F)
        path.lineTo(((cx + outerCr * Math.sqrt(3.0) / 2).toFloat()), cy - innerCr)
        path.addArc(outerRectF, -30F, -120F)
        path.lineTo(((cx - innerCr * Math.sqrt(3.0) / 2).toFloat()), cy + innerCr / 2)
        mPaint.color = colors[0]
        canvasTemp?.drawPath(path, mPaint)

        //绘制黄色区域之前先旋转120°
        val matrix1 = Matrix()
        matrix1.setRotate(120F, cx, cy)
        path.transform(matrix1)

        //绘制黄色区域
        mPaint.color = colors[1]
        canvasTemp?.drawPath(path, mPaint)

        //绘制绿色区域之前先旋转120°
        val matrix2 = Matrix()
        matrix2.setRotate(120F, cx, cy)
        path.transform(matrix2)

        //绘制绿色区域
        mPaint.color = colors[2]
        canvasTemp?.drawPath(path, mPaint)

        //绘制中间的蓝色区域
        mPaint.color = colors[3]
        canvasTemp?.drawCircle(cx, cy, innerCr.toFloat(), mPaint)
        mPaint.color = colors[4]
        canvasTemp?.drawCircle(cx, cy, innerCr.toFloat() - 5, mPaint)

        canvas?.drawBitmap(bitmap!!, 0F, 0F, mPaint)

        super.onDraw(canvas)
    }

    //处理点击事件
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action != MotionEvent.ACTION_DOWN && event?.action != MotionEvent.ACTION_UP) {
            return super.onTouchEvent(event)
        }
        if (bitmap == null || event.x < 0 || event.y < 0 || x > width || y > height) {
            return false
        }
        val pixel = bitmap?.getPixel(event.x.toInt(), event.y.toInt())
        if (pixel == Color.TRANSPARENT) {
            return false
        } else {
            //判断颜色值
            this.setTag(this.id, 3)
            colors.forEachIndexed { index, i ->
                if (colors[index] == pixel) {
                    this.setTag(this.id, index)
                }
            }
        }


        return super.onTouchEvent(event)
    }

}