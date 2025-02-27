package com.example.internshipapp

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.UUID

/*есть класс Новости.
есть метод "получить новости по ИД" который получает данные от сервера (время выполнения 1 секунда)
есть метод "получить список ИДшников всех новостей", который тоже берет данные с сервера (время выполнения 2 секунды)

нужно реализовать метод "получить все новости" за максимально короткое время.

существует множество вариантов решения. я хочу увидеть все, что сможешь придумать.
НО! лишь 1 будет идеальным. укажи какой вариант считаешь лучшим.*/
class CoroutineTask2 {

    data class News(
        val id: String,
        val text: String
    )

    suspend fun getNewsByID(id: String): News = withContext(Dispatchers.IO) {
        delay(1000)
        return@withContext News(id = id, text = UUID.randomUUID().toString())
    }

    suspend fun getAllNewsIDs(): List<String> = withContext(Dispatchers.IO) {
        delay(2000)
        return@withContext (0 until 1000).map { it.toString() }
    }


    suspend fun fetchAllNews1(): List<News> = withContext(Dispatchers.IO) {
        return@withContext getAllNewsIDs().map {
            getNewsByID(it)
        }
        //100 002 секунды
    }

    suspend fun fetchAllNews2(): List<News> = withContext(Dispatchers.IO) {
        val newsIDs = getAllNewsIDs()
        val deferredNews = newsIDs.map { id ->
            async {
                getNewsByID(id)
            }
        }
        return@withContext deferredNews.awaitAll()
        //3 секунды
    }

    fun callbackToFlow(): Flow<String> = channelFlow {
        val callback = object : Callback {
            override fun onResult(data: String) {
               // send(data) // Send data to the flow
            }
        }

        // Simulate an async callback
        GlobalScope.launch {
            delay(1000)
            callback.onResult("Data from callback")
        }

        awaitClose { /* Optional: Clean up when flow is completed */ }
    }

    interface Callback {
        fun onResult(data: String)
    }

    fun main() = runBlocking {
        callbackToFlow().collect { data ->
            println("Received: $data")
        }
    }
}

