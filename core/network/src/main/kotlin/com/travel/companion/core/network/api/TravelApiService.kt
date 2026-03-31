package com.travel.companion.core.network.api

import com.travel.companion.core.network.model.NetworkCity
import com.travel.companion.core.network.model.NetworkPlace
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Ktor tabanlı REST API servisi.
 * Sehir, yer ve rota verilerini uzak sunucudan ceker.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class TravelApiService @Inject constructor(
    private val client: HttpClient,
) {

    suspend fun getCities(): List<NetworkCity> =
        client.get("cities").body()

    suspend fun getCity(id: String): NetworkCity =
        client.get("cities/$id").body()

    suspend fun searchPlaces(query: String, cityId: String): List<NetworkPlace> =
        client.get("places") {
            parameter("q", query)
            parameter("city_id", cityId)
        }.body()

    suspend fun getPlacesByCity(cityId: String): List<NetworkPlace> =
        client.get("cities/$cityId/places").body()
}
