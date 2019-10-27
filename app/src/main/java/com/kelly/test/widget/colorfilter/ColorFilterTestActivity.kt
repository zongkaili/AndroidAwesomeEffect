package com.kelly.test.widget.colorfilter

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

class ColorFilterTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ColorFilterView(this))
    }
}
