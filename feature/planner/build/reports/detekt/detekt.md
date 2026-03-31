# detekt

## Metrics

* 53 number of properties

* 51 number of functions

* 16 number of classes

* 3 number of packages

* 12 number of kt files

## Complexity Report

* 1,139 lines of code (loc)

* 947 source lines of code (sloc)

* 629 logical lines of code (lloc)

* 56 comment lines of code (cloc)

* 94 cyclomatic complexity (mcc)

* 62 cognitive complexity

* 16 number of total code smells

* 5% comment source ratio

* 149 mcc per 1,000 lloc

* 25 code smells per 1,000 lloc

## Findings (16)

### complexity, LongMethod (2)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:75:14
```
The function PlannerScreen is too long (59). The maximum length is 50.
```
```kotlin
72  */
73 @OptIn(ExperimentalMaterial3Api::class)
74 @Composable
75 internal fun PlannerScreen(
!!              ^ error
76     state: PlannerState,
77     onIntent: (PlannerIntent) -> Unit,
78     modifier: Modifier = Modifier,

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:357:13
```
The function PlaceCard is too long (60). The maximum length is 50.
```
```kotlin
354 }
355 
356 @Composable
357 private fun PlaceCard(
!!!             ^ error
358     place: Place,
359     modifier: Modifier = Modifier,
360 ) {

```

### complexity, LongParameterList (1)

The more parameters a function has the more complex it is. Long parameter lists are often used to control complex algorithms and violate the Single Responsibility Principle. Prefer functions with short parameter lists.

[Documentation](https://detekt.dev/docs/rules/complexity#longparameterlist)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/voice/VoiceAssistantSheet.kt:37:24
```
The function VoiceAssistantSheet(isActive: Boolean, transcript: String, onStart: () -> Unit, onStop: () -> Unit, onDismiss: () -> Unit, modifier: Modifier) has too many parameters. The current threshold is set to 6.
```
```kotlin
34  */
35 @OptIn(ExperimentalMaterial3Api::class)
36 @Composable
37 fun VoiceAssistantSheet(
!!                        ^ error
38     isActive: Boolean,
39     transcript: String,
40     onStart: () -> Unit,

```

### complexity, TooManyFunctions (1)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:1:1
```
File '/Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt' with '14' functions detected. Defined threshold inside files is set to '11'
```
```kotlin
1 package com.travel.companion.feature.planner
! ^ error
2 
3 import androidx.compose.animation.AnimatedVisibility
4 import androidx.compose.foundation.clickable

```

### style, MagicNumber (4)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:319:50
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
316             places.first().longitude,
317         )
318     } else {
319         com.google.android.gms.maps.model.LatLng(41.0082, 28.9784)
!!!                                                  ^ error
320     }
321 
322     val cameraPositionState = com.google.maps.android.compose.rememberCameraPositionState {

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:319:59
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
316             places.first().longitude,
317         )
318     } else {
319         com.google.android.gms.maps.model.LatLng(41.0082, 28.9784)
!!!                                                           ^ error
320     }
321 
322     val cameraPositionState = com.google.maps.android.compose.rememberCameraPositionState {

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:323:101
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
320     }
321 
322     val cameraPositionState = com.google.maps.android.compose.rememberCameraPositionState {
323         position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(defaultPosition, 13f)
!!!                                                                                                     ^ error
324     }
325 
326     com.google.maps.android.compose.GoogleMap(

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:349:60
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
346             }
347             com.google.maps.android.compose.Polyline(
348                 points = routePoints,
349                 color = androidx.compose.ui.graphics.Color(0xFF1976D2),
!!!                                                            ^ error
350                 width = 5f,
351             )
352         }

```

### style, MaxLineLength (5)

Line detected, which is longer than the defined maximum line length in the code style.

[Documentation](https://detekt.dev/docs/rules/style#maxlinelength)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:90:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
87                 actions = {
88                     if (state.hasPlan) {
89                         IconButton(onClick = { onIntent(PlannerIntent.ShowPreferences) }) {
90                             Icon(Icons.Default.Tune, contentDescription = stringResource(R.string.planner_preferences_button_desc))
!! ^ error
91                         }
92                     }
93                 },

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:111:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
108                         if (state.isSaving) {
109                             CircularProgressIndicator(modifier = Modifier.padding(8.dp))
110                         } else {
111                             Icon(Icons.Default.Save, contentDescription = stringResource(R.string.planner_save_button_desc))
!!! ^ error
112                         }
113                     }
114                 }

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:184:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
181                 onClick = { onSelected(budget) },
182                 label = { Text(budget.name.lowercase().replaceFirstChar { it.uppercase() }) },
183                 leadingIcon = if (selectedBudget == budget) {
184                     { Icon(Icons.Default.AttachMoney, contentDescription = stringResource(R.string.planner_budget_icon_desc)) }
!!! ^ error
185                 } else null,
186             )
187         }

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/voice/VoiceAssistantSheet.kt:87:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
84             ) {
85                 Icon(
86                     imageVector = if (isActive) Icons.Default.MicOff else Icons.Default.Mic,
87                     contentDescription = if (isActive) stringResource(R.string.planner_voice_stop_desc) else stringResource(R.string.planner_voice_start_desc),
!! ^ error
88                     modifier = Modifier.size(36.dp),
89                 )
90             }

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/test/kotlin/com/travel/companion/feature/planner/PlannerViewModelTest.kt:60:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
57 
58     private fun createViewModel(cityId: String? = null): PlannerViewModel {
59         val handle = SavedStateHandle(if (cityId != null) mapOf("cityId" to cityId) else emptyMap())
60         return PlannerViewModel(handle, GenerateTravelPlanUseCase(fakeAiPlannerService), SaveTravelPlanUseCase(travelPlanRepository), cityRepository)
!! ^ error
61     }
62 
63     @Test fun `SetCityName updates state`() = runTest {

```

### style, UnusedPrivateMember (1)

Private function is unused and should be removed.

[Documentation](https://detekt.dev/docs/rules/style#unusedprivatemember)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/main/kotlin/com/travel/companion/feature/planner/PlannerScreen.kt:440:13
```
Private function `PlannerScreenPreview` is unused.
```
```kotlin
437 
438 @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
439 @Composable
440 private fun PlannerScreenPreview() {
!!!             ^ error
441     com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
442         PlannerScreen(
443             state = PlannerState(

```

### style, WildcardImport (2)

Wildcard imports should be replaced with imports using fully qualified class names. Wildcard imports can lead to naming conflicts. A library update can introduce naming clashes with your classes which results in compilation errors.

[Documentation](https://detekt.dev/docs/rules/style#wildcardimport)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/test/kotlin/com/travel/companion/feature/planner/PlannerViewModelTest.kt:27:1
```
org.junit.Assert.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
24 import kotlinx.coroutines.flow.flowOf
25 import kotlinx.coroutines.test.advanceUntilIdle
26 import kotlinx.coroutines.test.runTest
27 import org.junit.Assert.*
!! ^ error
28 import org.junit.Before
29 import org.junit.Rule
30 import org.junit.Test

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/planner/src/test/kotlin/com/travel/companion/feature/planner/usecase/GenerateTravelPlanUseCaseTest.kt:14:1
```
org.junit.Assert.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
11 import com.travel.companion.core.model.TravelPlan
12 import com.travel.companion.core.testing.testTravelPlan
13 import kotlinx.coroutines.test.runTest
14 import org.junit.Assert.*
!! ^ error
15 import org.junit.Test
16 
17 class GenerateTravelPlanUseCaseTest {

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2026-03-28 22:37:37 UTC
