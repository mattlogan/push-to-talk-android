Most of the code here lives in the `sendmessage` feature module. You'll also see an `audio` module for capturing audio, an `upload` module for uploading files to Google Cloud Storage via Firebase Storage, a `uicommon` module for strings and colors and such, and of course the root `app` module.

I included a couple unit tests in `/sendmessage/src/test` to cover the mapping of user inputs to UI status updates by way of the audio recorder and upload client.

This project uses ViewModel and LiveData from Android Architecture Components, Dagger 2 for dependency injection, RxJava + RxRelay for setting up reactive streams, the Firebase Storage library for uploading audio files, and of course... Kotlin! It also uses the new [AndroidX](https://developer.android.com/topic/libraries/support-library/androidx-overview) support library packages.

The UI architecture is pretty close to MVI (model-view-intent) as described [here](https://medium.com/@fnberta/mvi-on-android-20677f80df55) and [here](http://hannesdorfmann.com/android/model-view-intent).