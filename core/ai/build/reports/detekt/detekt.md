# detekt

## Metrics

* 91 number of properties

* 98 number of functions

* 32 number of classes

* 6 number of packages

* 22 number of kt files

## Complexity Report

* 1,592 lines of code (loc)

* 1,169 source lines of code (sloc)

* 838 logical lines of code (lloc)

* 162 comment lines of code (cloc)

* 127 cyclomatic complexity (mcc)

* 25 cognitive complexity

* 14 number of total code smells

* 13% comment source ratio

* 151 mcc per 1,000 lloc

* 16 code smells per 1,000 lloc

## Findings (14)

### style, ForbiddenComment (3)

Flags a forbidden comment.

[Documentation](https://detekt.dev/docs/rules/style#forbiddencomment)

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/ondevice/FeatureAvailabilityChecker.kt:20:9
```
Forbidden TODO todo marker in comment, please do the changes.
```
```kotlin
17 class FeatureAvailabilityChecker @Inject constructor() {
18 
19     suspend fun isOnDeviceAvailable(): Boolean {
20         // TODO: firebase-ai-ondevice stabilize olunca:
!!         ^ error
21         // val status = FirebaseAIOnDevice.checkStatus()
22         // return status == OnDeviceModelStatus.AVAILABLE
23         Log.d(TAG, "On-device AI henuz desteklenmiyor, cloud kullanilacak")

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/ondevice/HybridAiTextService.kt:34:5
```
Forbidden TODO todo marker in comment, please do the changes.
```
```kotlin
31             .generativeModel("gemini-2.5-flash")
32     }
33 
34     // TODO: firebase-ai-ondevice stabilize olunca:
!!     ^ error
35     // private val hybridModel by lazy {
36     //     Firebase.ai(backend = GenerativeBackend.googleAI())
37     //         .generativeModel(

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/ondevice/HybridAiTextService.kt:75:20
```
Forbidden TODO todo marker in comment, please do the changes.
```
```kotlin
72 
73     private suspend fun getPreferredModel() = if (availabilityChecker.isOnDeviceAvailable()) {
74         Log.d(TAG, "On-device model kullaniliyor")
75         cloudModel // TODO: hybridModel ile degistir (on-device API stabilize olunca)
!!                    ^ error
76     } else {
77         cloudModel
78     }

```

### style, MagicNumber (10)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiCityService.kt:19:15
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
16 class FakeAiCityService @Inject constructor() : AiCityService {
17 
18     override suspend fun searchCity(cityName: String): Result<City> {
19         delay(1500L)
!!               ^ error
20         val id = cityName.trim().lowercase().replace(" ", "-")
21         return Result.success(
22             City(

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiImageService.kt:17:15
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
14 class FakeAiImageService @Inject constructor() : AiImageService {
15 
16     override suspend fun generateImage(prompt: String): Result<ByteArray> {
17         delay(3000L)
!!               ^ error
18         // Gercek image byte'lari yerine bos array dondur
19         return Result.success(ByteArray(0))
20     }

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiPlannerService.kt:29:15
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
26         durationDays: Int,
27     ): Result<TravelPlan> {
28         // AI cagrisini simule et
29         delay(2000L)
!!               ^ error
30 
31         val days = (1..durationDays).map { dayIndex ->
32             DayPlan(

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiPlannerService.kt:57:15
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
54         cuisine: String,
55         budget: Budget,
56     ): Result<List<String>> {
57         delay(1000L)
!!               ^ error
58         return Result.success(
59             listOf(
60                 "$cityName Mutfagi - Yerel lezzetler",

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiTextService.kt:19:15
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
16 class FakeAiTextService @Inject constructor() : AiTextService {
17 
18     override suspend fun summarize(text: String): Result<String> {
19         delay(1000L)
!!               ^ error
20         val summary = if (text.length > 200) {
21             text.take(200) + "..."
22         } else {

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiTextService.kt:20:41
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
17 
18     override suspend fun summarize(text: String): Result<String> {
19         delay(1000L)
20         val summary = if (text.length > 200) {
!!                                         ^ error
21             text.take(200) + "..."
22         } else {
23             text

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiTextService.kt:21:23
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
18     override suspend fun summarize(text: String): Result<String> {
19         delay(1000L)
20         val summary = if (text.length > 200) {
21             text.take(200) + "..."
!!                       ^ error
22         } else {
23             text
24         }

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiTextService.kt:29:15
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
26     }
27 
28     override suspend fun translate(text: String, targetLang: String): Result<String> {
29         delay(1500L)
!!               ^ error
30         return Result.success("[Ceviri ($targetLang)]: $text")
31     }
32 

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiTextService.kt:34:15
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
31     }
32 
33     override suspend fun rewrite(text: String, tone: String): Result<String> {
34         delay(1000L)
!!               ^ error
35         return Result.success("[$tone ton]: $text")
36     }
37 

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/voice/FakeVoiceAssistantService.kt:19:15
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
16 
17     override fun startSession(): Flow<VoiceEvent> = flow {
18         emit(VoiceEvent.SessionStarted)
19         delay(1000)
!!               ^ error
20         emit(VoiceEvent.Response("Merhaba! Size nasil yardimci olabilirim?"))
21     }
22 

```

### style, MaxLineLength (1)

Line detected, which is longer than the defined maximum line length in the code style.

[Documentation](https://detekt.dev/docs/rules/style#maxlinelength)

* /Users/yakupcan/AndroidStudioProjects/AI/core/ai/src/main/kotlin/com/travel/companion/core/ai/fake/FakeAiPlannerService.kt:68:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
65     }
66 
67     private fun getDayTheme(dayIndex: Int, interests: List<String>): String {
68         val themes = listOf("Tarihi Yerler", "Yerel Lezzetler", "Kultur ve Sanat", "Dogal Guzellikler", "Alisveris ve Eglence")
!! ^ error
69         return themes.getOrElse(dayIndex - 1) { interests.firstOrNull() ?: "Kesfet" }
70     }
71 

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2026-03-28 22:37:36 UTC
