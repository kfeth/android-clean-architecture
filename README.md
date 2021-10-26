Android App Template
=========================

A GitHub template for creating quick-start Android app projects

Features
------------
* A quick way to spin up a new Android project with many of the common app components in place
* Networking, database, dependency injection, app navigation -> See [Libraries](#libs)
* [MVVM](https://developer.android.com/jetpack/guide#overview) architecture - Follows the principles outlined in Google's [guide to app architecture](https://developer.android.com/jetpack/guide)
* Jetpack Compose - simple UI built entirely in Compose (article list screen -> article details screen)
* Single Activity structure - using Navigation component to swap in Composable screens
* Kotlin Coroutines and Flow for asynchronous operations

API
------------
* [NewsAPI.org](https://newsapi.org/) - The sample app simply fetches and displays a list of the latest news articles

An API key is required, free developer keys can be obtained from [here](https://newsapi.org/register)

```
// Add this line to the gradle.properties file in the projects root folder
news_api_key="YOUR-API-KEY"
```
<a name="libs"></a>
Libraries
------------
* Jetpack
    * [Compose](https://developer.android.com/jetpack/compose)
    * [Accompanist](https://google.github.io/accompanist/)
    * [Room](https://developer.android.com/training/data-storage/room/)
    * [Navigation](https://developer.android.com/guide/navigation/)
    * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
    * [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
* Third Party
    * [Coil](https://coil-kt.github.io/coil/)
    * [Retrofit](https://square.github.io/retrofit/)
    * [Gson](https://github.com/google/gson#readme)
    * [Timber](https://github.com/JakeWharton/timber#readme)
