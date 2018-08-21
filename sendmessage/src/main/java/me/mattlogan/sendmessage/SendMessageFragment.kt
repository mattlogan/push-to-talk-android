package me.mattlogan.sendmessage

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.util.Linkify
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.text.util.LinkifyCompat
import androidx.fragment.app.Fragment
import com.jakewharton.rxrelay2.PublishRelay
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.io.File
import javax.inject.Inject

class SendMessageFragment : Fragment(), SendMessagePresenter.Target {

  private lateinit var toolbar: Toolbar
  private lateinit var pushToTalkButton: Button
  private lateinit var statusText: TextView

  private val startRecordingRelay = PublishRelay.create<StartRecordingEvent>()
  private val stopRecordingRelay = PublishRelay.create<StopRecordingEvent>()

  @Inject lateinit var presenter: SendMessagePresenter

  private lateinit var disposable: Disposable

  private val recordAudioRequestCode = 1278

  // Lint complains about adding a touch listener without manually calling performClick() for
  // accessibility reasons! We should make sure this works with accessibility devices.
  @SuppressLint("ClickableViewAccessibility")
  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View {

    AndroidSupportInjection.inject(this)

    val view = inflater.inflate(R.layout.send_message_fragment, container, false)

    toolbar = view.findViewById(R.id.send_message_toolbar)
    pushToTalkButton = view.findViewById(R.id.send_message_push_to_talk_button)
    statusText = view.findViewById(R.id.send_message_status_text)

    toolbar.title = getString(R.string.send_a_message)
    requireActivity().setActionBar(toolbar)

    pushToTalkButton.setOnTouchListener { _, motionEvent ->
      when (motionEvent.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
          startRecordingRelay.accept(StartRecordingEvent(filePath = getFile().absolutePath))
        }
        MotionEvent.ACTION_UP -> {
          stopRecordingRelay.accept(StopRecordingEvent)
        }
      }

      // Even though we're "handling" touches, we still want the button to show touches!
      return@setOnTouchListener false
    }

    // Let's just assume the user grants permission. If they deny, we'll show an error
    // when they try to record.
    requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), recordAudioRequestCode)

    presenter.attach(this)

    disposable = presenter.updates()
        .subscribe { event ->
          when (event) {
            is SendMessageUpdate.ShowError -> {
              Log.d("debug_log", "show error")
              statusText.text = getString(R.string.generic_error)
            }
            is SendMessageUpdate.ShowRecording -> {
              Log.d("debug_log", "show recording")
              statusText.text = getString(R.string.recording)
            }
            is SendMessageUpdate.ShowSending -> {
              Log.d("debug_log", "show sending")
              statusText.text = getString(R.string.sending)
            }
            is SendMessageUpdate.ShowSent -> {
              Log.d("debug_log", "show sent, url: ${event.fileUrl}")
              statusText.text = getString(R.string.message_sent, event.fileUrl)
              LinkifyCompat.addLinks(statusText, Linkify.ALL)
            }
          }
        }

    return view
  }

  override fun onDestroyView() {
    super.onDestroyView()
    presenter.detach()
    disposable.dispose()
  }

  override fun startRecordingEvents(): Observable<StartRecordingEvent> = startRecordingRelay

  override fun stopRecordingEvents(): Observable<StopRecordingEvent> = stopRecordingRelay

  // Ideally we'd get this stuff out of the UI layer but it requires a Context so it's a bit more
  // convenient to do it here. We could refactor this method into a separate class if we wanted.
  private fun getFile(): File {
    val filename = "audio-${System.currentTimeMillis()}"
    return File.createTempFile(filename, ".amr", requireContext().cacheDir)
  }
}
