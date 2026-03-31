---
name: android-ktor
description: Ktor Client for Android networking. KMP-ready HTTP client with Content Negotiation, kotlinx-serialization, and Hilt integration. Use instead of Retrofit for KMP-compatible projects.
---

# Ktor Client for Android

## Setup

```kotlin
// libs.versions.toml
[versions]
ktor = "3.0.3"

[libraries]
ktor-client-core = { module = "io.ktor:ktor-client-core", version.ref = "ktor" }
ktor-client-okhttp = { module = "io.ktor:ktor-client-okhttp", version.ref = "ktor" }
ktor-client-content-negotiation = { module = "io.ktor:ktor-client-content-negotiation", version.ref = "ktor" }
ktor-serialization-kotlinx-json = { module = "io.ktor:ktor-serialization-kotlinx-json", version.ref = "ktor" }
ktor-client-logging = { module = "io.ktor:ktor-client-logging", version.ref = "ktor" }
ktor-client-auth = { module = "io.ktor:ktor-client-auth", version.ref = "ktor" }
```

## Hilt Module

```kotlin
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                prettyPrint = false
            })
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.BODY
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 30_000
            connectTimeoutMillis = 15_000
        }
        defaultRequest {
            url("https://api.example.com/")
            contentType(ContentType.Application.Json)
        }
    }
}
```

## API Service Pattern

```kotlin
class TravelApiService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getCities(): List<NetworkCity> =
        client.get("cities").body()

    suspend fun getCity(id: String): NetworkCity =
        client.get("cities/$id").body()

    suspend fun createTravelPlan(request: CreatePlanRequest): NetworkTravelPlan =
        client.post("plans") {
            setBody(request)
        }.body()

    suspend fun searchPlaces(query: String, cityId: String): List<NetworkPlace> =
        client.get("places") {
            parameter("q", query)
            parameter("city_id", cityId)
        }.body()
}
```

## Error Handling

```kotlin
suspend fun <T> safeApiCall(block: suspend () -> T): Result<T> =
    runCatching { block() }
        .onFailure { e ->
            when (e) {
                is ClientRequestException -> { /* 4xx */ }
                is ServerResponseException -> { /* 5xx */ }
                is HttpRequestTimeoutException -> { /* timeout */ }
                is CancellationException -> throw e
            }
        }
```

## KMP Advantage
- Same API service code works on Android, iOS, Desktop
- Engine is platform-specific (OkHttp on Android, Darwin on iOS)
- Shared serialization models via kotlinx-serialization

## Rules
- Inject HttpClient via Hilt (singleton)
- Use suspend functions for all calls
- Return Result<T> from repository layer
- Map NetworkModel -> Entity/Domain at repository boundary
- Always set timeouts
- Use Content Negotiation plugin (not manual serialization)
