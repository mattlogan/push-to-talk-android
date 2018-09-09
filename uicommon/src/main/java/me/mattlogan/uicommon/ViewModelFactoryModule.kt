package me.mattlogan.uicommon

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelFactoryModule {

  @Binds
  abstract fun viewModelFactory(daggerViewModelFactory: ViewModelFactory): ViewModelProvider.Factory
}
