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

class SendMessageFragment : Fragment() {

  private lateinit var toolbar: Toolbar
  private lateinit var pushToTalkButton: Button

  @SuppressLint("ClickableViewAccessibility")
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.send_message_fragment, container, false)
    toolbar = view.findViewById(R.id.send_message_toolbar)
    pushToTalkButton = view.findViewById(R.id.send_message_push_to_talk_button)

    toolbar.title = getString(R.string.send_a_message)
    requireActivity().setActionBar(toolbar)

    pushToTalkButton.setOnTouchListener { _, motionEvent ->
      when (motionEvent.actionMasked) {
        MotionEvent.ACTION_DOWN -> {

        }
        MotionEvent.ACTION_UP -> {

        }
        else -> {
          // Ignore
        }
      }

      // Even though we're "handling" touches, we still want the button to show touches!
      return@setOnTouchListener false
    }

    return view
  }
}
