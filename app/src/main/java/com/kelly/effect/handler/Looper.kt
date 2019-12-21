package com.kelly.effect.handler

/**
 * author: zongkaili
 * data: 2019-11-26
 * desc:
 */
class Looper {
    val mQueue: MessageQueue? = null

    companion object {
        val sThreadLocal = ThreadLocal<Looper>()
        fun prepare() {
            if (sThreadLocal.get() != null) {
                throw RuntimeException("Only one Looper may be created per thread")
            }
            sThreadLocal.set(Looper())
        }

        fun myLooper(): Looper? {
            return sThreadLocal.get()
        }

        fun loop() {
            val me = myLooper() ?: throw RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.")
            val queue = me.mQueue

            while (true) {
                val msg = queue?.next()
                    ?: // No message indicates that the message queue is quitting.
                    return // might block
                msg.target?.dispatchMessage(msg)
            }
        }

    }
}