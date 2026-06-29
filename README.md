# SimpleGpsTracker

SimpleGpsTracker is a minimal Android app written in Kotlin and Jetpack Compose that periodically records the device’s GPS location and sends it to a configurable server endpoint.

The project is intended as a portfolio‑quality example of **modern Android development**, with a clean, layered architecture, modularization, and background location tracking.

## Tech Stack

- Kotlin
- Jetpack Compose (Material 3)
- Coroutines / Flow
- Modular architecture:
    - `app` – Android app, UI, DI wiring
    - `core-domain` – pure Kotlin domain models, repositories, use cases
    - `core-data` – DataStore, Ktor client, repository implementations
- Dependency Injection: Hilt (planned wiring)
- Networking: Ktor HTTP client + Kotlinx Serialization
- Persistence: DataStore (Preferences) for configuration (planned)
- Location: Google Play Services Location (Fused Location Provider)
- Background work: WorkManager / foreground service for tracking (planned)
- CI: GitHub Actions (`SimpleGpsTracker – Android CI` workflow)

## Features (Planned)

- Start / stop GPS tracking
- Periodic GPS location collection
- Configurable tracking interval
- Configurable server URL and API token
- Foreground notification while tracking
- Basic status information (last sent location, last error)
