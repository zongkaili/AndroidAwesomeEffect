package com.kelly.test.widget.pwdinput

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kelly.test.R

class PwdInputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pwd_input)
        val etPwd = findViewById<PwdEditText>(R.id.etPwd)
        etPwd.setOnTextChangeListener { pwd ->
            if (pwd.length == etPwd.textLength) {
                Toast.makeText(this@PwdInputActivity, "密码：$pwd", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
