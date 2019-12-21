package com.kelly.effect.handler

/**
 * author: zongkaili
 * data: 2019-11-26
 * desc:
 */
open class Handler {
    private var mLooper: Looper? = null
    private var mQueue: MessageQueue? = null

    init {
        mLooper = Looper.myLooper()
        if (mLooper == null) {
            throw RuntimeException(
                "Can't create handler inside thread " + Thread.currentThread()
                        + " that has not called Looper.prepare()"
            )
        }
        mQueue = mLooper?.mQueue
    }

    open fun handleMessage(msg: Message) {}

    fun sendMessage(message: Message) {
        enqueueMessage(message)
    }

    private fun enqueueMessage(msg: Message) {
        msg.target = this
        mQueue?.enqueueMessage(msg)
    }

    fun dispatchMessage(msg: Message) {
        handleMessage(msg)
    }

}