package me.mattlogan.upload

import android.net.Uri
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

interface UploadClient {
  fun uploadFile(path: String): Single<Result>

  sealed class Result {
    data class Success(val fileUrl: String) : Result()
    object Error : Result()
  }
}

class RealUploadClient @Inject constructor() : UploadClient {
  override fun uploadFile(path: String): Single<UploadClient.Result> {
    val uri = Uri.fromFile(File(path))
    val storageRef = FirebaseStorage.getInstance().reference.child(uri.lastPathSegment!!)

    return Single.create { emitter ->
      storageRef.putFile(uri)
          .continueWithTask { task ->
            if (!task.isSuccessful) {
              Log.d("debug_log", "task failed: ${task.exception}")
              throw task.exception!!
            }

            return@continueWithTask storageRef.downloadUrl
          }
          .addOnCompleteListener { task ->
            if (task.isSuccessful) {
              emitter.onSuccess(UploadClient.Result.Success(task.result.toString()))
            } else {
              emitter.onSuccess(UploadClient.Result.Error)
            }
          }
    }
  }
}
