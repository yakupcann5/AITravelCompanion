package com.travel.companion.core.ai.firebase

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.GenerativeBackend
import com.google.firebase.ai.type.content
import com.google.firebase.ai.type.generationConfig
import com.travel.companion.core.ai.AiCityService
import com.travel.companion.core.model.City
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Sehir arama Firebase AI servisi.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Singleton
class FirebaseAiCityService @Inject constructor() : AiCityService {

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
                    temperature = 0.7f
                },
            )
    }

    override suspend fun searchCity(cityName: String): Result<City> = runCatching {
        val response = model.generateContent(
            content { text("Sehir: $cityName") },
        )

        val responseText = response.text
            ?: error("AI bos yanit dondu")

        val aiCity = json.decodeFromString<AiCityResponse>(responseText)
        aiCity.toDomain()
    }
}

@Serializable
internal data class AiCityResponse(
    val name: String,
    val country: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val tags: List<String>,
) {
    fun toDomain(): City = City(
        id = name.trim().lowercase().replace(" ", "-"),
        name = name.trim(),
        country = country.trim(),
        description = description.trim(),
        imageUrl = "",
        latitude = latitude,
        longitude = longitude,
        tags = tags,
    )
}

private const val SYSTEM_PROMPT = """
Sen bir seyahat asistanisin. Kullanici sana bir sehir adi verecek.
O sehir hakkinda asagidaki JSON formatinda bilgi don.

JSON formati:
{
  "name": "Sehrin tam adi",
  "country": "Ulke adi",
  "description": "Sehir hakkinda 1-2 cumlelik kisa ve ilgi cekici aciklama (Turkce)",
  "latitude": 41.0082,
  "longitude": 28.9784,
  "tags": ["etiket1", "etiket2", "etiket3", "etiket4"]
}

Kurallar:
- description Turkce olmali
- tags Turkce olmali ve seyahatle ilgili olmali (ornek: tarih, kultur, yemek, deniz, doga, mimari, sanat)
- latitude ve longitude gercek koordinatlar olmali
- Eger sehir gercek degilse veya bulunamiyorsa, en yakin eslesen gercek sehri don
- Sadece JSON don, baska bir sey yazma
"""
