package com.kelly.test.handler

import java.util.concurrent.ArrayBlockingQueue

/**
 * author: zongkaili
 * data: 2019-11-26
 * desc:
 */
class MessageQueue {

    val blockingQueue by lazy { ArrayBlockingQueue<Message>(50) }

    fun enqueueMessage(msg: Message) {
        try {
            blockingQueue.put(msg)
        } catch (e: Exception) {
        }
    }

    fun next(): Message? {
        var msg: Message? = null
        try {
            msg = blockingQueue.take()
        } catch (e: Exception) {
        }
        return msg
    }

}