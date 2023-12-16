package com.jean.reactivecoroutinestudy.coroutine

import mu.KotlinLogging

private val log = KotlinLogging.logger {}

/**
 * Continuation Passing Style 예제 코드
 */
object CpsCalculator {
    fun calculate(initialValue: Int, continuation: (Int) -> Unit) {
        initialize(initialValue) { initial ->
            addOne(initial) { added ->
                multiplyTwo(added) { multiplied ->
                    continuation(multiplied)
                }
            }
        }
    }

    private fun initialize(value: Int, continuation: (Int) -> Unit) {
        log.info { "Initial" }
        continuation(value)
    }

    private fun addOne(value: Int, continuation: (Int) -> Unit) {
        log.info { "Add one" }
        continuation(value + 1)
    }

    private fun multiplyTwo(value: Int, continuation: (Int) -> Unit) {
        log.info { "Multiply two" }
        continuation(value + 2)
    }
}

fun main() {
    CpsCalculator.calculate(5) {
        log.info { "Result = $it" }
    }
}
