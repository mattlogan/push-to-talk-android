package me.mattlogan.upload

import dagger.Binds
import dagger.Module

@Module
abstract class UploadClientModule {
  @Binds abstract fun uploadClient(recorder: RealUploadClient): UploadClient
}
