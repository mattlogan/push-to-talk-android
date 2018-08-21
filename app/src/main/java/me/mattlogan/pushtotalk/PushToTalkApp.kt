package me.mattlogan.pushtotalk

import android.app.Application
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class PushToTalkApp : Application(), HasSupportFragmentInjector {

  @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

  override fun onCreate() {
    super.onCreate()
    DaggerAppComponent.create().inject(this)
  }

  override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}
