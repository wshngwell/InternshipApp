package com.example.internshipapp

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import kotlin.random.Random

class CoroutineTask3 {
    /*данный код симулирует 11 паралельных задач, которые выполняются поочередно
     (из-за delay, который регулируется порядковым номером)
    сейчас случайным образом может произойти ошибка, из-за которой упадет все приложение.

    * необходимо обработать ошибку (просто вывести ошибку в лог)
    * несмотря на ошибку в одном из дочерних лаунчей, остальные задачи должны продолжить выполняться

    спустя 3,5 секунды (или любое другое время) работа будет отменена
    * задачи должны прекратить выполняться.

    !!! метод test закрыт для редактирования!!! кодить можно только в методе main

    ! запрещено пользоваться runCathing / try-catch !
    решить задачу используя CoroutineExceptionHandler*/

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d("!!!", throwable.stackTraceToString())
    }

    private suspend fun test(a: Int): Unit = withContext(Dispatchers.IO) {
        delay(a * 1000L)
        if (Random.nextBoolean()) throw Exception("error - ${a}")
        Log.e("!!!", "${a}")
    }


    fun main() {

        val job = MainScope().launch {
            val superVisorJob = SupervisorJob(this.coroutineContext[Job])
            (0..10).forEach {

                launch(superVisorJob + exceptionHandler) {
                    test(it)
                }

            }
        }
        MainScope().launch(Dispatchers.IO) {
            delay(3_500)
            job.cancel()
            Log.e("!!!", "отмена")
        }
    }
}

