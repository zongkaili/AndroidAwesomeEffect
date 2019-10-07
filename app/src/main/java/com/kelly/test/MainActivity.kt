package com.kelly.test

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kelly.test.aidl.AIDLTestActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun splash(view: View) {
        startActivity(Intent(this, SplashAnimActivity::class.java))
    }

    fun irregular(view: View) {
        startActivity(Intent(this, IrregularActivity::class.java))
    }

    fun explosion(view: View) {
        startActivity(Intent(this, ExplosionActivity::class.java))
    }

    fun aidl(view: View) {
        startActivity(Intent(this, AIDLTestActivity::class.java))
    }

    fun bezier(view: View) {
        startActivity(Intent(this, BezierActivity::class.java))
    }
}
