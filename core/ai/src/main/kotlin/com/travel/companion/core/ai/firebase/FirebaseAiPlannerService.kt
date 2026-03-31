package com.travel.companion.core.ai.firebase

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import com.travel.companion.core.ai.AiPlannerService
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.DayPlan
import com.travel.companion.core.model.Place
import com.travel.companion.core.model.PlaceCategory
import com.travel.companion.core.model.TravelPlan
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Gezi planlama Firebase AI servisi.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class FirebaseAiPlannerService @Inject constructor() : AiPlannerService {

    private val json = Json { ignoreUnknownKeys = true }

    private val model by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(
                modelName = "gemini-2.5-flash",
                systemInstruction = content {
                    text(SYSTEM_PROMPT)
                },
                generationConfig = generationConfig {
                    responseMimeType = "application/json"
                    temperature = 0.8f
                },
            )
    }

    private val restaurantModel by lazy {
        Firebase.ai(backend = GenerativeBackend.googleAI())
            .generativeModel(
                modelName = "gemini-2.5-flash",
                generationConfig = generationConfig {
                    responseMimeType = "application/json"
                    temperature = 0.7f
                },
            )
    }

    override suspend fun generateTravelPlan(
        cityName: String,
        budget: Budget,
        interests: List<String>,
        durationDays: Int,
    ): Result<TravelPlan> = runCatching {
        val prompt = buildString {
            append("Sehir: $cityName\n")
            append("Butce: ${budget.toTurkish()}\n")
            append("Ilgi alanlari: ${interests.joinToString(", ")}\n")
            append("Sure: $durationDays gun\n")
        }

        val response = model.generateContent(
            content { text(prompt) },
        )

        val responseText = response.text
            ?: error("AI bos yanit dondu")

        val aiPlan = json.decodeFromString<AiTravelPlanResponse>(responseText)
        aiPlan.toDomain(cityName, budget, interests)
    }

    override suspend fun suggestRestaurants(
        cityName: String,
        cuisine: String,
        budget: Budget,
    ): Result<List<String>> = runCatching {
        val prompt = buildString {
            append("$cityName sehrinde $cuisine mutfagindan ")
            append("${budget.toTurkish()} butceye uygun 5 restoran oner. ")
            append("JSON formatinda sadece restoran isimlerini dondur: ")
            append("""{"restaurants": ["isim1", "isim2", ...]}""")
        }

        val response = restaurantModel.generateContent(
            content { text(prompt) },
        )

        val responseText = response.text
            ?: error("AI bos yanit dondu")

        val result = json.decodeFromString<AiRestaurantResponse>(responseText)
        result.restaurants
    }
}

@Serializable
internal data class AiTravelPlanResponse(
    val days: List<AiDayPlanResponse>,
) {
    fun toDomain(
        cityName: String,
        budget: Budget,
        interests: List<String>,
    ): TravelPlan = TravelPlan(
        id = UUID.randomUUID().toString(),
        cityId = cityName.trim().lowercase().replace(" ", "-"),
        cityName = cityName,
        days = days.mapIndexed { index, day -> day.toDomain(index) },
        budget = budget,
        interests = interests,
        createdAt = System.currentTimeMillis(),
    )
}

@Serializable
internal data class AiDayPlanResponse(
    val title: String,
    val places: List<AiPlaceResponse>,
) {
    fun toDomain(index: Int): DayPlan = DayPlan(
        dayIndex = index,
        title = title,
        places = places.map { it.toDomain() },
    )
}

@Serializable
internal data class AiPlaceResponse(
    val name: String,
    val description: String,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val category: String = "attraction",
    val estimatedTime: String = "",
    val rating: Float = 0f,
) {
    fun toDomain(): Place = Place(
        id = name.trim().lowercase().replace(" ", "-"),
        name = name,
        description = description,
        imageUrl = "",
        latitude = latitude,
        longitude = longitude,
        category = parseCategory(category),
        estimatedTime = estimatedTime,
        rating = rating,
    )
}

@Serializable
internal data class AiRestaurantResponse(
    val restaurants: List<String>,
)

internal fun parseCategory(value: String): PlaceCategory = when (value.lowercase()) {
    "restaurant", "restoran" -> PlaceCategory.RESTAURANT
    "museum", "muze" -> PlaceCategory.MUSEUM
    "park" -> PlaceCategory.PARK
    "shopping", "alisveris" -> PlaceCategory.SHOPPING
    "nightlife", "gece-hayati" -> PlaceCategory.NIGHTLIFE
    "hotel", "otel" -> PlaceCategory.HOTEL
    else -> PlaceCategory.ATTRACTION
}

internal fun Budget.toTurkish(): String = when (this) {
    Budget.LOW -> "dusuk"
    Budget.MEDIUM -> "orta"
    Budget.HIGH -> "yuksek"
    Budget.LUXURY -> "luks"
}

private const val SYSTEM_PROMPT = """
Sen bir profesyonel seyahat planlayicisisin. Kullanici sana sehir, butce, ilgi alanlari ve sure verecek.
Detayli bir gezi plani olustur.

JSON formati:
{
  "days": [
    {
      "title": "1. Gun - Tarihi Yerler",
      "places": [
        {
          "name": "Yer adi",
          "description": "Yer hakkinda kisa aciklama (Turkce)",
          "latitude": 41.0082,
          "longitude": 28.9784,
          "category": "attraction",
          "estimatedTime": "2 saat",
          "rating": 4.5
        }
      ]
    }
  ]
}

Kurallar:
- Her gun icin 3-4 yer oner
- category degerleri: attraction, restaurant, museum, park, shopping, nightlife, hotel
- estimatedTime Turkce olmali (ornek: "2 saat", "1.5 saat", "45 dk")
- description Turkce olmali
- latitude ve longitude gercek koordinatlar olmali
- Butceye uygun yerler sec (dusuk butce = ucretsiz/ucuz yerler, luks = premium yerler)
- Ilgi alanlarina gore yerleri onceliklendir
- Her gune tematik bir baslik ver
- rating 0-5 arasi olmali
- Sadece JSON don, baska bir sey yazma
"""
