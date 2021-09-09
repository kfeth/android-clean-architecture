Android App Template
=========================

A GitHub template for creating quick-start Android app projects

Features
------------
* A quick way to spin up a new Android app with many of the common components in place
* Networking, database, dependency injection, app navigation etc
* [MVVM](https://developer.android.com/jetpack/guide#overview) architecture - Follows the principles outlined in Google's [guide to app architecture](https://developer.android.com/jetpack/guide)
* Single Activity structure - using Navigation component to swap fragments
* Kotlin Coroutines and Flow for asynchronous operations

API
------------
* [NewsAPI.org](https://newsapi.org/) - The sample app simply fetches and displays a list of the latest news articles

An API key is required, free developer keys can be obtained from [here](https://newsapi.org/register). Add this line to the `gradle.properties` file in the projects root folder

```
news_api_key="YOUR-API-KEY"
```


Libraries
------------
* Jetpack
    * [Android KTX](https://developer.android.com/kotlin/ktx)
    * [View Binding](https://developer.android.com/topic/libraries/view-binding)
    * [Room](https://developer.android.com/training/data-storage/room/)
    * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
    * [Navigation](https://developer.android.com/guide/navigation/)
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* Third Party
    * [Retrofit](https://square.github.io/retrofit/)
    * [Glide](https://bumptech.github.io/glide/)
    * [Gson](https://github.com/google/gson)
    * [Timber](https://github.com/JakeWharton/timber)
