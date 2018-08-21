package me.mattlogan.pushtotalk.sendmessage

// Represents all the possible UI update states
sealed class SendMessageUpdate {
  object ShowError : SendMessageUpdate()
  object ShowRecording : SendMessageUpdate()
  object ShowSending : SendMessageUpdate()
  object ShowSent : SendMessageUpdate()
}
