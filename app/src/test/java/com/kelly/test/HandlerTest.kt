package com.kelly.test

import com.kelly.test.handler.Handler
import com.kelly.test.handler.Looper
import com.kelly.test.handler.Message
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