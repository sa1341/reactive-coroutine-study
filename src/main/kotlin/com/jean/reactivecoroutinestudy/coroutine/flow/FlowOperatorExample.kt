package com.jean.reactivecoroutinestudy.coroutine.flow

import kotlinx.coroutines.flow.*
import mu.KotlinLogging
import java.lang.Exception

private val log = KotlinLogging.logger {}

suspend fun main() {
    mapOperatorTest()
    filterOperatorTest()
    transformOperatorTest()
    reduceAndFoldTest()
    firstAndSingleTest()
}

private suspend fun firstAndSingleTest() {
    val numbers = flowOf(1, 2, 3, 4, 5)

    val firstNumber = numbers.first()
    log.info { "first = $firstNumber" }

    try {
        val singleNumber = numbers.single()
        log.info { "single = $singleNumber" }
    } catch (e: Exception) {
        log.error { "single error1 = ${e.message}" }
    }

    try {
        val singleNumber = emptyFlow<Int>().single()
        log.info { "single = $singleNumber" }
    } catch (e: Exception) {
        log.error { "single error2 = ${e.message}" }
    }

}

private suspend fun reduceAndFoldTest() {
    val numbers = flowOf(1, 2, 3, 4, 5)

    // reduce는 첫번째 인자 값을 초기 값으로 사용합니다.
    val reduced = numbers.reduce { accumulator, value ->
        accumulator + value
    }
    log.info { "reduced = $reduced" }

    // fold는 초기 값을 받을 수 있습니다.
    val folded = numbers.fold(10) { accumulator, value ->
        accumulator + value
    }
    log.info { "folded = $folded" }
}

private suspend fun mapOperatorTest() {
    val numbers = flowOf(1, 2, 3, 4, 5)

    log.info { "mapOperatorTest Start" }
    log.info { "Operator1 Start" }
    numbers.map { it * 2 }.collect {
        log.info { "value1: $it" }
    }
    log.info { "Operator1 End" }

    log.info { "Operator2 Start" }
    numbers.mapNotNull {
        if (it % 2 == 0) null else it
    }.collect {
        log.info { "value2: $it" }
    }
    log.info { "Operator2 End" }
}

private suspend fun filterOperatorTest() {
    val numbers = flowOf(1, 2, 3, 4, 5)

    log.info { "filterOperatorTest Start" }
    log.info { "Operator1 Start" }
    numbers.filter { it % 2 == 0 }.collect {
        log.info { "value1: $it" }
    }
    log.info { "Operator1 End" }

    log.info { "Operator2 Start" }
    numbers.filterNot { it % 2 == 0 }.collect {
        log.info { "value2: $it" }
    }
    log.info { "Operator2 End" }

    log.info { "Operator3 Start" }
    val objects = flowOf(10, "20", emptyList<String>(), null)
    objects.filterIsInstance<Int>().collect {
        log.info { "value3: $it" }
    }
    log.info { "Operator3 End" }

    log.info { "Operator4 Start" }
    objects.filterNotNull().collect {
        log.info { "value4: $it" }
    }
    log.info { "Operator4 End" }
}

private suspend fun transformOperatorTest() {
    val numbers = flowOf(1, 2, 3, 4, 5)

    log.info { "transformOperatorTest Start" }
    log.info { "Operator1 Start" }
    numbers.transform { item ->
        emit(item * item)
    }.collect {
        log.info { "value1 = $it" }
    }
    log.info { "Operator1 End" }

    numbers.transform { item ->
        if (item % 2 == 0) {
            emit(item * item)
            emit(item * item * item)
        }
    }.collect {
        log.info { "value2 = $it" }
    }
}
