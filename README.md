Desktop Accounting App (Kotlin Multiplatform)

This project is a Desktop application built with Kotlin Multiplatform and Jetpack Compose for Desktop, focused on managing clients, current accounts (accounts receivable), transactions and balances.

It follows modern reactive patterns using StateFlow, clean architecture principles, and Voyager for navigation.

🧱 Tech Stack

Kotlin Multiplatform (JVM / Desktop)

Jetpack Compose for Desktop

Voyager – Navigation

Room – Local database

Koin – Dependency Injection

Coroutines & StateFlow – Reactive state management

Material 3


* Navigation *

Navigation is handled using Voyager.

A sidebar menu is used instead of a back button

Each menu item represents a top-level screen

To avoid stacking the same screen multiple times, navigation uses:

screen comparison (navigator.lastItem)

or replace() instead of push()

🧠 Architecture Overview

The app follows a layered approach:

UI (Compose)
│
├── ViewModel (StateFlow)
│
├── UseCases
│
└── Repository
    └── Room (DAO + Entities)


This is a Kotlin Multiplatform project targeting Desktop (JVM).

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

### Build and Run Desktop (JVM) Application

To build and run the development version of the desktop app, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:run
  ```

---

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
