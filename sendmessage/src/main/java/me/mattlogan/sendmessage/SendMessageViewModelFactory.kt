package me.mattlogan.sendmessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class SendMessageViewModelFactory @Inject constructor(
    private val startRecordingTransformer: StartRecordingTransformer,
    private val stopRecordingTransformer: StopRecordingTransformer) : ViewModelProvider.Factory {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return SendMessageViewModel(startRecordingTransformer, stopRecordingTransformer) as T
  }
}
