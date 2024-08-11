Programming Language: Kotlin
 
Architecture : MVVM + Modularization

MinSdk: 23

TargetSdk: 34

Android Studio Version : Jellyfish 2023.3.1

This project shows a list of books which is sorted by the release date, from the latest to the oldest.
The data is saved into the local database so when there is no internet connection or network error, it will load from the local database.
It has 2 screens, the first screen is where the list of books shown. The second screen is where the detail information of the book is shown.

the project has two modules :

data module -> it provides the access to data source, either from asset file or local database

app -> it contains the app itself where there are all UIs and resources.

techstack :
1. Jetpack Compose
2. Jetpack Navigation
3. Kotlin Coroutine
4. Kotlin Flow
5. Moshi
6. Coil
7. Dagger Hilt
8. Room database
9. Mockito
10. Timber

The app can handle the following edge cases:
1. Unknown date format beside "M/dd/yyyy" and "YYYY"
2. Minus value of book ID
3. when URL image returns error, display "no image" image
4. Network error, will retry 3 times with delay 2 seconds for each call

Screenshots : 

![alt text](https://github.com/raka-bakar/bookslist/blob/main/Screenshot_20240618_174012.png)
![alt text](https://github.com/raka-bakar/bookslist/blob/main/Screenshot_20240618_174105.png)
