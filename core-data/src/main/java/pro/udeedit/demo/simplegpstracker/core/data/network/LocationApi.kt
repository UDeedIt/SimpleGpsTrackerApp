package pro.udeedit.demo.simplegpstracker.core.data.network

import pro.udeedit.demo.simplegpstracker.core.data.network.dto.LocationPayload

interface LocationApi {

    suspend fun sendLocation(
        serverUrl: String,
        apiToken: String?,
        payload: LocationPayload
    )
}