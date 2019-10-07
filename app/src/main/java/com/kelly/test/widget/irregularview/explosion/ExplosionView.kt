package com.kelly.test.widget.irregularview.explosion

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.kelly.test.R

/**
 * author: zongkaili
 * data: 2019-10-06
 * desc: 粒子爆炸效果view
 */
class ExplosionView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defaultStyleInt: Int = 0)
    : View(context, attributeSet, defaultStyleInt) {

    private lateinit var mBitmap: Bitmap
    private lateinit var mPaint: Paint
    private val mParticleD = 16//粒子直径
    private lateinit var mParticles: ArrayList<Paticle>
    private var animator: ValueAnimator? = null

    init {
        initView()
    }

    private fun initView() {
        mPaint = Paint()
        mBitmap = BitmapFactory.decodeResource(resources, R.drawable.pic_test)
        mParticles = arrayListOf()
        initParticle()
    }

    private fun initParticle() {
        for (i in 0 until mBitmap.width step mParticleD) {
            for (j in 0 until mBitmap.height step mParticleD) {
               val particle = Paticle()
                particle.color = mBitmap.getPixel(i, j)
                particle.r = (mParticleD / 2).toFloat()
                particle.x = i + particle.r
                particle.y = j + particle.r
                //水平初速度（-20， 20）
                particle.vx = ((Math.random() - 0.5) * 40).toFloat()
                //垂直初速度（-30， 30）
                particle.vy = ((Math.random() - 0.5) * 60).toFloat()
                particle.ax = 0f
                particle.ay = 9.8f
                mParticles.add(particle)
            }
        }

        animator = ValueAnimator.ofFloat(0f, 1f)
        animator?.apply {
            duration = 2000
            interpolator = LinearInterpolator()
            addUpdateListener {
                updateParticle()
                invalidate()
            }
        }
    }

    private fun updateParticle() {
        for (particle in mParticles) {
            particle.x += particle.vx
            particle.y += particle.vy
            particle.vx += particle.ax
            particle.vy += particle.ay
        }
    }

    override fun onDraw(canvas: Canvas?) {
//        canvas?.translate(400F, 400F)
        if (!animator?.isStarted!!) {
            canvas?.drawBitmap(mBitmap, 0f, 0f, mPaint)
        } else {
            for (particle in mParticles) {
                mPaint.color = particle.color
                canvas?.drawCircle(particle.x, particle.y, particle.r, mPaint)
            }
        }
        super.onDraw(canvas)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            initParticle()
            animator?.end()
            animator?.start()
        }
        return super.onTouchEvent(event)
    }
}