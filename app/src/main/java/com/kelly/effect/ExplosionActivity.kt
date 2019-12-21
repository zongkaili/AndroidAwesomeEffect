package com.kelly.effect

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kelly.effect.utils.StatusBarUtil

class ExplosionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explosion)
        StatusBarUtil.setTransparentForImageView(this, null)
    }

}
