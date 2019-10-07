package com.kelly.test

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelly.test.widget.PathMeasureView

class PathMeasureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(PathMeasureView(this))
    }

}
