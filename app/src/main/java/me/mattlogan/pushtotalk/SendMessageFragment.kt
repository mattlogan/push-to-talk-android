package me.mattlogan.pushtotalk

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SendMessageFragment : Fragment(), SendMessagePresenter.Target {

  private lateinit var toolbar: Toolbar
  private lateinit var pushToTalkButton: Button

  private val downTouchesRelay = PublishRelay.create<Unit>()
  private val upTouchesRelay = PublishRelay.create<Unit>()

  @Inject lateinit var presenter: SendMessagePresenter

  private lateinit var disposable: Disposable

  // Lint complains about adding a touch listener without manually calling performClick() for
  // accessibility reasons! We should make sure this works with accessibility devices.
  @SuppressLint("ClickableViewAccessibility")
  override fun onCreateView(inflater: LayoutInflater,
                            container: ViewGroup?,
                            savedInstanceState: Bundle?): View {

    val view = inflater.inflate(R.layout.send_message_fragment, container, false)

    toolbar = view.findViewById(R.id.send_message_toolbar)
    pushToTalkButton = view.findViewById(R.id.send_message_push_to_talk_button)

    toolbar.title = getString(R.string.send_a_message)
    requireActivity().setActionBar(toolbar)

    pushToTalkButton.setOnTouchListener { _, motionEvent ->
      when (motionEvent.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
          downTouchesRelay.accept(Unit)
        }
        MotionEvent.ACTION_UP -> {
          upTouchesRelay.accept(Unit)
        }
        else -> {
          // Ignore
        }
      }

      // Even though we're "handling" touches, we still want the button to show touches!
      return@setOnTouchListener false
    }

    presenter.attach(this)

    disposable = presenter.updates()
        .subscribe { event ->
          when (event) {
            is SendMessageUpdate.ShowError -> {

            }
            is SendMessageUpdate.ShowSending -> {

            }
            is SendMessageUpdate.ShowSent -> {

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

  override fun downTouches(): Observable<Unit> = downTouchesRelay

  override fun upTouches(): Observable<Unit> = upTouchesRelay
}
