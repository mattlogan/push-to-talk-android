package me.mattlogan.sendmessage

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.text.util.LinkifyCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import me.mattlogan.uicommon.ViewModelFactory
import java.io.File
import javax.inject.Inject

class SendMessageFragment : Fragment() {

  private lateinit var toolbar: Toolbar
  private lateinit var pushToTalkButton: Button
  private lateinit var statusText: TextView

  @Inject lateinit var viewModelFactory: ViewModelFactory

  private val recordAudioRequestCode = 1278

  // Lint complains about adding a touch listener without manually calling performClick() for
  // accessibility reasons! We should make sure this works with accessibility devices.
  @SuppressLint("ClickableViewAccessibility")
  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View {

    AndroidSupportInjection.inject(this)

    val viewModel = ViewModelProviders.of(this, viewModelFactory)[SendMessageViewModel::class.java]

    val view = inflater.inflate(R.layout.send_message_fragment, container, false)

    toolbar = view.findViewById(R.id.send_message_toolbar)
    pushToTalkButton = view.findViewById(R.id.send_message_push_to_talk_button)
    statusText = view.findViewById(R.id.send_message_status_text)

    toolbar.title = getString(R.string.send_a_message)
    requireActivity().setActionBar(toolbar)

    pushToTalkButton.setOnTouchListener { _, motionEvent ->
      when (motionEvent.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
          viewModel.uiEvents.startRecording.accept(StartRecordingEvent(filePath = getFile().absolutePath))
        }
        MotionEvent.ACTION_UP -> {
          viewModel.uiEvents.stopRecording.accept(StopRecordingEvent)
        }
      }

      // Even though we're "handling" touches, we still want the button to show touches!
      return@setOnTouchListener false
    }

    // Let's just assume the user grants permission. If they deny, we'll show an error
    // when they try to record.
    requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), recordAudioRequestCode)

    viewModel.state().observe(this, Observer<SendMessageUpdate> { event ->
      when (event) {
        is SendMessageUpdate.ShowError -> {
          statusText.text = getString(R.string.generic_error)
        }
        is SendMessageUpdate.ShowRecording -> {
          statusText.text = getString(R.string.recording)
        }
        is SendMessageUpdate.ShowSending -> {
          statusText.text = getString(R.string.sending)
        }
        is SendMessageUpdate.ShowSent -> {
          statusText.text = getString(R.string.message_sent, event.fileUrl)
          LinkifyCompat.addLinks(statusText, Linkify.ALL)
        }
      }
    })

    return view
  }

  // Ideally we'd get this stuff out of the UI layer but it requires a Context so it's a bit more
  // convenient to do it here. We could refactor this method into a separate class if we wanted.
  private fun getFile(): File {
    val filename = "audio-${System.currentTimeMillis()}"
    return File.createTempFile(filename, ".amr", requireContext().cacheDir)
  }
}
