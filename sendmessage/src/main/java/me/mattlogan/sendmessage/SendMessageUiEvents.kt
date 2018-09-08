package me.mattlogan.sendmessage

import com.jakewharton.rxrelay2.PublishRelay

// UI event classes
data class StartRecordingEvent(val filePath: String)
object StopRecordingEvent

// Holder for event relays
class SendMessageUiEvents(
    val startRecording: PublishRelay<StartRecordingEvent> = PublishRelay.create(),
    val stopRecording: PublishRelay<StopRecordingEvent> = PublishRelay.create()
)
