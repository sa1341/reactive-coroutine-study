package com.jean.reactivecoroutinestudy.coroutine.sinks

import mu.KotlinLogging
import reactor.core.publisher.Hooks
import reactor.core.publisher.Sinks
import reactor.core.publisher.Sinks.EmitFailureHandler.FAIL_FAST

private val log = KotlinLogging.logger {}

fun main() {
    Hooks.onOperatorDebug()
    sinkOnePublisher()
    sinkManyUnicastPublisher()
    sinkManyMulticastPublisher()
    sinkManyReplayPublisher()
}

/**
 * Sinks.One 예제코드
 * - emit 된 데이터 중에서 단 하나의 데이터만 Subscriber에게 전달합니다. 나머지 데이터는 Drop 됨.
 */
private fun sinkOnePublisher() {
    val sinkOne = Sinks.one<String>()
    val event = sinkOne.asMono()

    sinkOne.emitValue("Hello Reactor", FAIL_FAST)
    sinkOne.emitValue("Hi Reactor", FAIL_FAST)

    event.subscribe {
        log.info { "# Subscriber1 = $it" }
    }

    event.subscribe {
        log.info { "# Subscriber2 = $it" }
    }
}

/**
 * Sinks.Many 예제
 * - unicast()를 통해 단 하나의 Subscriber만 데이터를 전달 받을 수 있습니다.
 * Sinks가 Publisher의 역할을 할 경우 기본적으로 Hot Publisher로 동작합니다.
 */
private fun sinkManyUnicastPublisher() {
    val unicastSink = Sinks.many().unicast().onBackpressureBuffer<Int>()
    val fluxView = unicastSink.asFlux()

    unicastSink.emitNext(1, FAIL_FAST)
    unicastSink.emitNext(2, FAIL_FAST)

    fluxView.subscribe { log.info { "# Subscriber1 = $it" } }

    unicastSink.emitNext(3, FAIL_FAST)

    fluxView.subscribe { log.info { "# Subscriber2 = $it" } }

    // Caused by: java.lang.IllegalStateException
    //            : Sinks.many().unicast() sinks only allow a single Subscriber
}

/**
 * Sinks.Many 예제
 * - multicast()를 통해 하나 이상의 Subscriber에게 데이터를 emit하는 예제
 */
private fun sinkManyMulticastPublisher() {
    val unicastSink = Sinks.many().multicast().onBackpressureBuffer<Int>()
    val fluxView = unicastSink.asFlux()

    unicastSink.emitNext(1, FAIL_FAST)
    unicastSink.emitNext(2, FAIL_FAST)

    fluxView.subscribe { log.info { "# Subscriber1 = $it" } } // 1, 2, 3
    fluxView.subscribe { log.info { "# Subscriber2 = $it" } } // 3

    unicastSink.emitNext(3, FAIL_FAST)
}

/**
 * Sinks.Many 예제
 * - replay()를 통해 이미 emit된 데이터 중에서 특정 개수의 최신 데이터만 전달하는 예제
 */
private fun sinkManyReplayPublisher() {
    val replayLimitSink = Sinks.many().replay().limit<Int>(2)
    val fluxLimitView = replayLimitSink.asFlux()

    log.info { "####### Replay Limit #######" }

    replayLimitSink.emitNext(1, FAIL_FAST)
    replayLimitSink.emitNext(2, FAIL_FAST)
    replayLimitSink.emitNext(3, FAIL_FAST)

    fluxLimitView.subscribe { log.info { "# Subscriber1 = $it" } } // 1, 2, 3

    replayLimitSink.emitNext(4, FAIL_FAST)

    fluxLimitView.subscribe { log.info { "# Subscriber2 = $it" } } // 3

    val replayAllSink = Sinks.many().replay().all<Int>()
    val fluxAllView = replayAllSink.asFlux()

    log.info { "####### Replay All #######" }

    replayAllSink.emitNext(1, FAIL_FAST)
    replayAllSink.emitNext(2, FAIL_FAST)
    replayAllSink.emitNext(3, FAIL_FAST)

    fluxAllView.subscribe { log.info { "# Subscriber1 = $it" } } // 1, 2, 3, 4

    replayAllSink.emitNext(4, FAIL_FAST)

    fluxAllView.subscribe { log.info { "# Subscriber2 = $it" } } // 1, 2, 3, 4
}
