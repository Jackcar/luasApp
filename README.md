# luasApp
This repository contains a simple Luas app that implements MVVM architecture using Kotlin, ViewModel, LiveData, RxJava, Hilt and etc.


## Tech stack & Open-source libraries
- Minimum SDK level 24
- [Kotlin](https://kotlinlang.org/) based
- Hilt for Dependency Injection.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
- [SimpleXML](https://github.com/square/retrofit/tree/master/retrofit-converters/simplexml) - A XML converter library for Kotlin and Java.
- [RxJava & RxAndroid](https://github.com/ReactiveX/RxAndroid) - Library to make writing reactive components in Android applications easy and hassle-free.
