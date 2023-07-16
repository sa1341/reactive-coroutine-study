package com.jean.reactivecoroutinestudy.notification.event

data class Event(
    val type: String,
    val message: String
) {
    val toMessage: String
        get() = "$type: $message"
}
