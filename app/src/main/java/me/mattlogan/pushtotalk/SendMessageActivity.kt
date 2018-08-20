package me.mattlogan.pushtotalk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SendMessageActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.send_message_activity)

    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
          .add(R.id.send_message_container, SendMessageFragment())
          .commit()
    }
  }
}
