package com.jean.reactivecoroutinestudy.coroutine

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import kotlin.system.measureTimeMillis

class CoroutineExercise01 {

    @Test
    fun `코루틴 비동기 메서드 호출 결과를 측정한다`() = runBlocking {
        val result = measureTimeMillis {
            withDetails()
        }

        println(result)
    }

    private suspend fun withDetails() = coroutineScope {
        val result1 = async {
            doSomethingFirst()
        }

        val result2 = async {
            doSomethingSecond()
        }

        println("result1 = ${result1.await()}, result2 = ${result2.await()}")
    }

    private suspend fun doSomethingFirst(): String {
        delay(2000)
        return "done1"
    }

    private suspend fun doSomethingSecond(): String {
        delay(3000)
        return "done2"
    }
}
