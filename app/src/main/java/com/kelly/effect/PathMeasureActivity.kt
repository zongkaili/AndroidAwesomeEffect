package com.kelly.effect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelly.effect.widget.PathMeasureView

class PathMeasureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(PathMeasureView(this))
    }

}
