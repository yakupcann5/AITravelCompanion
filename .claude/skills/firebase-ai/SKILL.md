---
name: firebase-ai
description: Firebase AI Logic (Gemini Flash/Pro), ML Kit GenAI (Gemini Nano on-device), and Nano Banana (image generation). Use for AI integration in Android apps including text generation, summarization, translation, and image generation.
---

# Firebase AI & On-Device AI for Android

## Architecture: Hybrid AI Strategy

```
              ┌─────────────────────────┐
              │     AiTextService        │  <- Interface
              └────────┬────────────────┘
                       │
          ┌────────────┼────────────────┐
          ▼            ▼                ▼
┌─────────────┐ ┌──────────────┐ ┌──────────────┐
│ OnDevice     │ │ Cloud         │ │ Hybrid       │
│ (Nano)       │ │ (Firebase AI) │ │ (Fallback)   │
└─────────────┘ └──────────────┘ └──────────────┘
```

## 1. ML Kit GenAI (Gemini Nano) - On-Device

### Setup
```kotlin
// libs.versions.toml
mlkit-genai = { module = "com.google.mlkit:genai", version = "1.0.0-beta1" }
```

### Feature Availability Check
```kotlin
class GeminiNanoAvailability @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun isAvailable(): Boolean {
        val client = GenerativeModel.getClient(context)
        return client.isModelDownloaded()
    }

    suspend fun downloadModel() {
        val client = GenerativeModel.getClient(context)
        client.downloadModel()
    }
}
```

### Summarization API
```kotlin
class OnDeviceSummarizer @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun summarize(text: String): String {
        val client = GenerativeModel.getClient(context)
        val summarizer = client.newSummarizer(
            SummarizerOptions.builder()
                .setLength(SummaryLength.SHORT)
                .build()
        )
        return summarizer.summarize(text).summary
    }
}
```

### Proofreading API
```kotlin
suspend fun proofread(text: String): String {
    val proofreader = client.newProofreader()
    return proofreader.proofread(text).correctedText
}
```

### Translation (Offline)
```kotlin
suspend fun translate(text: String, targetLang: String): String {
    val rewriter = client.newRewriter(
        RewriterOptions.builder()
            .setTone(Tone.FORMAL)
            .setLanguage(targetLang)
            .build()
    )
    return rewriter.rewrite(text).rewrittenText
}
```

## 2. Firebase AI Logic (Cloud)

### Setup
```kotlin
// libs.versions.toml
firebase-ai = { module = "com.google.firebase:firebase-ai", version = "16.0.0" }
```

### Text Generation (Gemini Flash)
```kotlin
class CloudAiService @Inject constructor() {
    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.0-flash")

    suspend fun generateTravelPlan(
        city: String,
        budget: String,
        interests: List<String>,
        duration: Int
    ): TravelPlan {
        val prompt = buildTravelPrompt(city, budget, interests, duration)
        val response = model.generateContent(prompt)
        return parseTravelPlan(response.text ?: "")
    }
}
```

### Function Calling (Structured Output)
```kotlin
val getTravelPlanFunction = defineFunction(
    name = "get_travel_plan",
    description = "Generate a structured travel itinerary",
    parameters = listOf(
        Schema.string("city", "Target city"),
        Schema.integer("days", "Number of days"),
        Schema.array("interests", Schema.string("interest", "Interest category"))
    )
)

val model = Firebase.ai(backend = GenerativeBackend.googleAI())
    .generativeModel(
        modelName = "gemini-2.0-flash",
        tools = listOf(Tool(listOf(getTravelPlanFunction)))
    )
```

## 3. Nano Banana (Image Generation)

```kotlin
class AiImageGenerator @Inject constructor() {
    private val model = Firebase.ai(backend = GenerativeBackend.googleAI())
        .generativeModel("gemini-2.5-flash-preview-image-generation")

    suspend fun generatePostcard(prompt: String): Bitmap {
        val response = model.generateContent(prompt)
        val imageBytes = response.candidates.first()
            .content.parts.filterIsInstance<ImagePart>().first()
        return BitmapFactory.decodeByteArray(imageBytes.data, 0, imageBytes.data.size)
    }
}
```

## Hybrid Service Pattern

```kotlin
class HybridAiTextService @Inject constructor(
    private val onDevice: OnDeviceAiTextService,
    private val cloud: CloudAiTextService
) : AiTextService {

    override suspend fun summarize(text: String): Result<String> =
        if (onDevice.isAvailable().first()) {
            runCatching { onDevice.summarize(text) }
                .recoverCatching { cloud.summarize(text) }
        } else {
            runCatching { cloud.summarize(text) }
        }
}
```

## Rules
- Always check Gemini Nano availability before on-device calls
- Provide cloud fallback for every on-device feature
- Use Firebase App Check for cloud API security
- Handle model download state in UI (download progress)
- Use Server-side prompt templates for sensitive prompts
- Monitor AI usage via Firebase AI Monitoring dashboard
