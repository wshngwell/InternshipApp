package com.example.internshipapp

import android.util.Log
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

/* есть массив от 0 до 100. проходимся поочередно по нему, и вызываем метод test,
    которые вернет нам некоторый результат, на основе отданных в него данных.
    все это происходит в корутине. через 3,5 секунды Джоба закроется, но работа продолжит выполняться.

    почему так происходит?
    как это можно пофиксить?

    !!! запрещено заменять Thread.sleep на delay

    Задача №2. убрать модификатор suspend и опять починить
*/
class CoroutineTask1 {

    fun main() {
        val mScope = MainScope() + Dispatchers.IO
        val job = mScope.launch {
            (0..100).forEach {
                val result = test(it)
                ensureActive()
                Log.e("!!!", "${result}")
            }
        }
        mScope.launch {
            delay(3_500)
            job.cancel()
            Log.e("!!!", "отмена")
        }
    }

    private fun test(id: Int): String {
        Thread.sleep(1000)
        return id.toString()
    }
}
