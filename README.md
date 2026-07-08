# SimpleGpsTracker (Android)

SimpleGpsTracker is a demo Android app written in Kotlin and Jetpack Compose that periodically records the device’s GPS location in a foreground service and sends it to a configurable backend (Ktor server, e.g. [SimpleGpsTrackerServer](https://github.com/UDeedIt/SimpleGpsTrackerServer)).

The project is structured as a small, realistic portfolio example of modern Android + Ktor Backend development.

---

## Features

- Foreground GPS tracking with a persistent notification
- User-configurable:
  - Tracking on/off
  - Tracking interval (minutes)
  - Base server URL (no hardcoded endpoint)
  - User name
- Sends JSON payloads to `{BASE_URL}/api/v1/locations`
- Displays:
  - Last sent latitude/longitude
  - Last location timestamp (date + time)
  - Simple tracking status text
- Clean architecture with modularization:
  - `app` – UI, DI wiring, foreground service
  - `core-domain` – models, use cases, repository interfaces
  - `core-data` – Ktor client, DataStore, mappers

---

## 📸 Screenshots

<p align="center">
  <b>Modern MVI & Compose UI</b><br>
  <img src="./assets/im_1_main_screen.png" width="30%" alt="Main Screen" />
  <img src="./assets/im_2_permission_1.png" width="30%" alt="Allow Notifications Permission" />
  <img src="./assets/im_3_permission_2.png" width="30%" alt="Allow Location Permission" />
  <img src="./assets/im_4_main_screen.png" width="30%" alt="Main Screen at Work" />
  <img src="./assets/im_1_main_screen_dark.png" width="30%" alt="Main Screen, Dark" />
  <img src="./assets/im_4_main_screen_dark.png" width="30%" alt="Main Screen at Work, Dark" />
</p>

---

## Tech Stack

**Android**

- Kotlin
- Jetpack Compose (Material 3)
- Hilt (dependency injection)
- Coroutines + StateFlow
- Fused Location Provider (Google Play Services Location)
- Foreground Service with notification (Android 13+ permissions)
- DataStore (preferences) for configuration

**Networking**

- Ktor HTTP client
- Kotlinx Serialization (JSON)

**Architecture**

- Modular: `app`, `core-domain`, `core-data`
- Domain:
  - `LocationPoint`, `TrackingConfig`
  - Use cases: observe/update tracking config, observe location
- Data:
  - `LocationApi` + `KtorLocationApi`
  - `TrackingConfigRepository` + DataStore

**Backend (companion project)**

- Ktor server ([SimpleGpsTrackerServer](https://github.com/UDeedIt/SimpleGpsTrackerServer))
- Kotlinx Serialization
- Docker
- Google Cloud Run

---

## JSON Contracts

### Request (Android → Server)

**{
"deviceId": "330be5e7-bef9-47f8-aeb3-5823aac7e6b2",
"userName": "Alex",
"latitude": 40.2016878,
"longitude": 44.5144951,
"accuracyMeters": 8.3,
"timestampMillis": 1783347145469
}**

### Response (Server → Android)

**{
"status": "ok",
"message": "Location received"
}**

```text
POST {BASE_URL}/api/v1/locations
