package com.example.internshipapp

import android.app.Application
import android.os.Looper
import com.example.internshipapp.di.appModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


open class BaseTestClass : KoinTest {

    private val testDispatcher = TestCoroutineDispatcher()

    private val testModulesList = appModule + listOf(
        ApiServiceMock().module,
        PostDBMock().module
    )

    @Before
    fun before() {
        startKoin {
            androidContext(Mockito.mock(Application::class.java))
            modules(testModulesList)
        }
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun after() {
        stopKoin()
        Dispatchers.resetMain()
    }

    fun test(
        invoke: suspend () -> Unit
    ) {
        runBlocking {
            invoke()
        }
    }

}