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


## The requirements
A lot of people commute from city center to the office using LUAS every day. To avoid waiting too much at the stops, people would like to have an app where it is
possible to see trams forecast. To help people I wrote a simple app considering only the following requests:


Given I am a LUAS passenger:
When I open the app from 00:00 – 12:00
Then I should see trams forecast from Marlborough LUAS stop towards Outbound


Given I am a LUAS passenger
When I open the app from 12:01 – 23:59
Then I should see trams forecast from Stillorgan LUAS stop towards Inbound


Given I am on the stop forecast info screen
When I tap on the refresh button
Then the forecast data should be updated


To get real time information, it was used LUAS Forecasting API
https://data.gov.ie/dataset/luas-forecasting-api
