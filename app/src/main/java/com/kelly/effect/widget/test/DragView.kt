package com.kelly.effect.widget.test

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.Toast

import kotlin.math.abs
import android.graphics.*


/**
 * author: kelly
 * data: 2019-10-26
 * desc:
 */
class DragView : View {
    private var mContext: Context? = null
    private var mCurrentX = 100
    private var mCurrentY = 100
    private var mPaint: Paint? = null
    private var mScreenWidth: Int = 0
    private var mScreenHeight: Int = 0
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mRegionPath: Path? = null
    private var mRegion: Region? = null

    private var lastX: Int = 0
    private var lastY: Int = 0

    private var circleRegion: Region? = null

    companion object {
        private val TAG = DragView::class.java.simpleName
        private const val sRadius = 100
    }

    constructor(context: Context) : super(context) {
        mContext = context
//        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.drawCircle(mCurrentX.toFloat(), mCurrentY.toFloat(), sRadius.toFloat(), mPaint!!)
        //Path.Direction.CW---顺时针  Path.Direction.CCW逆时针
        mRegionPath?.addCircle(
            mCurrentX.toFloat(),
            mCurrentY.toFloat(),
            sRadius.toFloat(),
            Path.Direction.CW
        )
        circleRegion?.setPath(mRegionPath, mRegion)
    }

    private fun init() {
        mPaint = Paint()
        mPaint!!.color = mContext?.resources?.getColor(com.kelly.effect.R.color.colorAccent, null)!!
        mPaint!!.isAntiAlias = true
        val manager = mContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        manager.defaultDisplay.getSize(point)
        mScreenWidth = point.x
        mScreenHeight = point.y

        circleRegion = Region()
        mRegionPath = Path()
        mRegion = Region(0, 0, mWidth, mWidth)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = w
        mHeight = h
        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (circleRegion?.contains(event.x.toInt(), event.y.toInt())!!) {
                    Toast.makeText(context, "onClick", Toast.LENGTH_LONG).show()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                mCurrentX = event.x.toInt()
                mCurrentY = event.y.toInt()
                if (mCurrentX >= 100 && mCurrentY >= 100 && mCurrentX <= mScreenWidth - 100 && mCurrentY <= mScreenHeight - 300) {
                    if (abs(mCurrentX - lastX) <= 200 && abs(mCurrentY - lastY) <= 200) {
                        postInvalidate()
                        lastX = mCurrentX
                        lastY = mCurrentY
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        return true
    }

}