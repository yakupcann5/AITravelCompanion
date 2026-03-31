package com.travel.companion.core.testing

import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.City
import com.travel.companion.core.model.DayPlan
import com.travel.companion.core.model.Place
import com.travel.companion.core.model.PlaceCategory
import com.travel.companion.core.model.TravelPlan

/**
 * Birim testleri icin ornek domain nesneleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
val testCity = City(
    id = "city-1",
    name = "Istanbul",
    country = "Turkiye",
    description = "Tarihi yarimada ve Bogazici'nin kesistigi benzersiz sehir.",
    imageUrl = "https://example.com/istanbul.jpg",
    latitude = 41.0082,
    longitude = 28.9784,
    tags = listOf("Tarih", "Kultur", "Yemek"),
)

val testPlace = Place(
    id = "place-1",
    name = "Ayasofya",
    description = "Bizans doneminden kalma tarihi yapi.",
    imageUrl = "https://example.com/hagiasophia.jpg",
    latitude = 41.0086,
    longitude = 28.9802,
    category = PlaceCategory.ATTRACTION,
    estimatedTime = "120 dk",
    rating = 4.8f,
)

val testDayPlan = DayPlan(
    dayIndex = 0,
    title = "Tarihi Yarimada Turu",
    places = listOf(testPlace),
)

val testTravelPlan = TravelPlan(
    id = "plan-1",
    cityId = "city-1",
    cityName = "Istanbul",
    days = listOf(testDayPlan),
    budget = Budget.MEDIUM,
    interests = listOf("Tarih", "Kultur"),
    createdAt = 1711152000000L,
)
