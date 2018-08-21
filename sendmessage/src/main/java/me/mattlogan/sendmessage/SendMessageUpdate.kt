package me.mattlogan.sendmessage

// Represents all the possible UI update states
sealed class SendMessageUpdate {
  object ShowError : SendMessageUpdate()
  object ShowRecording : SendMessageUpdate()
  object ShowSending : SendMessageUpdate()
  data class ShowSent(val fileUrl: String) : SendMessageUpdate()
}
