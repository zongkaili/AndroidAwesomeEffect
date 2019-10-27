package com.kelly.test.widget.colorfilter

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.kelly.test.widget.test.DragView

class ColorFilterTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(DragView(this))
    }
}
