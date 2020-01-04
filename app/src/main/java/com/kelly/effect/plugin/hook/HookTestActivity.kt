package com.kelly.effect.plugin.hook

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.kelly.effect.R
import kotlinx.android.synthetic.main.activity_hook_test.*
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy

class HookTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hook_test)
        btn.setOnClickListener {
            Toast.makeText(this, btn.text, Toast.LENGTH_SHORT).show()
        }

        btn1.setOnClickListener {
//            startActivity(Intent(this, HookSampleActivity::class.java))
        }

        try {
            hookOnClick(btn)
        } catch (e: Exception) {
           Toast.makeText(this, "hookOnClick 失败 >>> " + e.message, Toast.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("PrivateApi")
    @Throws(Exception::class)
    private fun hookOnClick(view: View) {
        //反射拿到ListenerInfo的对象
        val viewClass = Class.forName("android.view.View")
        val getListenerInfoMethod = viewClass.getDeclaredMethod("getListenerInfo")
        getListenerInfoMethod.isAccessible = true
        val listenerInfo = getListenerInfoMethod.invoke(view)
        //通过ListenerInfo对象拿到在其中声明的mOnClickListener属性
        val listenerInfoClass = Class.forName("android.view.View${'$'}ListenerInfo")
        val onClickListenerField = listenerInfoClass.getField("mOnClickListener")
        val onClickListenerObj = onClickListenerField.get(listenerInfo) as Any

        val onClickListenerProxyInstance = Proxy.newProxyInstance(HookTestActivity::class.java.classLoader,
            arrayOf<Class<*>>(View.OnClickListener::class.java),
            InvocationHandler { proxy, method, args ->
                Log.d("hookOnClick", " invocationHandler >>> ")
                val btn = AppCompatButton(this@HookTestActivity)
                btn.text = "hook更改了文案"
                return@InvocationHandler method.invoke(onClickListenerObj, btn)
            })


        //将mOnClickListener属性换成动态代理的onClickListener示例
        onClickListenerField.set(listenerInfo, onClickListenerProxyInstance)

    }
}
