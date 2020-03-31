package com.kelly.effect

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicLong

/**
 * author: zongkaili
 * data: 2020/3/26
 * desc:
 */
class CoroutinesTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            println("start")
//            val c = AtomicLong()
//            for (i in 1..1000000L) {
//                GlobalScope.launch {
//                    c.addAndGet(i)
//                }
//            }
//            println(c.get())


            val deferred = (1..1000000).map { n ->
                 GlobalScope.async {
                     //模拟耗时操作
                    workload(n)
                }
            }

            runBlocking {
                //计算上面1..1000000的和，需要上面的协程执行完，才会执行，类似异步回调
                val sum = deferred.map {
                    it.await().toLong()
                }.sum()
                println("Sum: $sum")//500000500000
            }

//            val job = GlobalScope.launch(Dispatchers.Default) {
//                //在一个协程环境中，执行后台耗时操作
//            }
//            job.cancel()
//
//            GlobalScope.launch(newSingleThreadContext("MyThread")){
//                //在一个协程环境中，执行后台耗时操作
//            }

            println("stop")
        }

        suspend fun workload(n: Int): Int {
            delay(1000)
            return n
        }
    }
}