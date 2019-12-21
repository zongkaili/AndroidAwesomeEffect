package com.kelly.effect

import com.kelly.effect.handler.Handler
import com.kelly.effect.handler.Looper
import com.kelly.effect.handler.Message
import org.junit.Test

/**
 * author: zongkaili
 * data: 2019-11-26
 * desc:
 */
class HandlerTest {
    @Test
    fun main() {
        //Looper准备
        Looper.prepare()

        //处理消息
        val handler = object: Handler() {
            override fun handleMessage(msg: Message) {
                println(msg.obj)
            }
        }

        //发送消息
        Thread {
            val message = Message()
            message.obj = "手动实现的handler"
            handler.sendMessage(message)
        }.start()

        //循环取消息
        Looper.loop()

    }
}