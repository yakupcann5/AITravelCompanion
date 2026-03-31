package com.travel.companion.core.data.mapper

import com.travel.companion.core.database.entity.TravelPlanEntity
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.DayPlan
import com.travel.companion.core.model.Place
import com.travel.companion.core.model.PlaceCategory
import com.travel.companion.core.model.TravelPlan
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json

/**
 * TravelPlan entity-domain donusturucu.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
private val json = Json { ignoreUnknownKeys = true }

fun TravelPlanEntity.toDomain(): TravelPlan = TravelPlan(
    id = id,
    cityId = cityId,
    cityName = cityName,
    days = parseDaysJson(daysJson),
    budget = Budget.entries.firstOrNull { it.name.equals(budget, ignoreCase = true) } ?: Budget.MEDIUM,
    interests = parseInterestsJson(interests),
    createdAt = createdAt,
)

fun TravelPlan.toEntity(): TravelPlanEntity = TravelPlanEntity(
    id = id,
    cityId = cityId,
    cityName = cityName,
    budget = budget.name.lowercase(),
    interests = json.encodeToString(ListSerializer(String.serializer()), interests),
    daysJson = json.encodeToString(ListSerializer(DayPlan.serializer()), days),
    createdAt = createdAt,
)

private fun parseDaysJson(daysJson: String): List<DayPlan> = runCatching {
    json.decodeFromString<List<DayPlan>>(daysJson)
}.getOrDefault(emptyList())

private fun parseInterestsJson(interestsJson: String): List<String> = runCatching {
    json.decodeFromString<List<String>>(interestsJson)
}.getOrDefault(emptyList())
