package com.kelly.test

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_irregular.*

class IrregularActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_irregular)
        chromeIconView.setOnClickListener {
            Toast.makeText(this, it.getTag(it.id).toString(), Toast.LENGTH_SHORT).show()
        }
    }

}
