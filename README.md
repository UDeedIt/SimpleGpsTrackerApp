# SimpleGpsTracker

SimpleGpsTracker is a minimal Android app written in Kotlin and Jetpack Compose that periodically records the device’s GPS location and sends it to a configurable server endpoint.

The project is intended as a portfolio‑quality example of modern Android development with a clean architecture and background location tracking.

## Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- Coroutines / Flow
- Hilt for dependency injection
- AndroidX WorkManager / foreground service for background work (planned)
- Retrofit + OkHttp for networking (planned)
- DataStore (Preferences) for configuration (planned)
- Google Play Services Location for GPS data
- GitHub Actions for CI

## Features (Planned)

- Start / stop GPS tracking
- Periodic GPS location collection
- Configurable tracking interval
- Configurable server URL and API token
- Foreground notification while tracking
- Basic status information (last sent location, last error)
