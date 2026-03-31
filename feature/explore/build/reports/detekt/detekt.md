# detekt

## Metrics

* 49 number of properties

* 40 number of functions

* 16 number of classes

* 2 number of packages

* 12 number of kt files

## Complexity Report

* 943 lines of code (loc)

* 756 source lines of code (sloc)

* 490 logical lines of code (lloc)

* 60 comment lines of code (cloc)

* 69 cyclomatic complexity (mcc)

* 21 cognitive complexity

* 7 number of total code smells

* 7% comment source ratio

* 140 mcc per 1,000 lloc

* 14 code smells per 1,000 lloc

## Findings (7)

### complexity, TooManyFunctions (1)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/explore/src/main/kotlin/com/travel/companion/feature/explore/ExploreScreen.kt:1:1
```
File '/Users/yakupcan/AndroidStudioProjects/AI/feature/explore/src/main/kotlin/com/travel/companion/feature/explore/ExploreScreen.kt' with '11' functions detected. Defined threshold inside files is set to '11'
```
```kotlin
1 package com.travel.companion.feature.explore
! ^ error
2 
3 import androidx.compose.foundation.layout.Arrangement
4 import androidx.compose.foundation.layout.PaddingValues

```

### style, MagicNumber (4)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/explore/src/main/kotlin/com/travel/companion/feature/explore/ExploreScreen.kt:263:74
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
260         ExploreScreen(
261             state = ExploreState(
262                 popularCities = listOf(
263                     City("1", "Istanbul", "Turkiye", "Tarihi sehir", "", 41.0, 28.9, listOf("Tarih")),
!!!                                                                          ^ error
264                     City("2", "Paris", "Fransa", "Isiklar sehri", "", 48.8, 2.3, listOf("Kultur")),
265                 ),
266             ),

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/explore/src/main/kotlin/com/travel/companion/feature/explore/ExploreScreen.kt:263:80
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
260         ExploreScreen(
261             state = ExploreState(
262                 popularCities = listOf(
263                     City("1", "Istanbul", "Turkiye", "Tarihi sehir", "", 41.0, 28.9, listOf("Tarih")),
!!!                                                                                ^ error
264                     City("2", "Paris", "Fransa", "Isiklar sehri", "", 48.8, 2.3, listOf("Kultur")),
265                 ),
266             ),

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/explore/src/main/kotlin/com/travel/companion/feature/explore/ExploreScreen.kt:264:71
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
261             state = ExploreState(
262                 popularCities = listOf(
263                     City("1", "Istanbul", "Turkiye", "Tarihi sehir", "", 41.0, 28.9, listOf("Tarih")),
264                     City("2", "Paris", "Fransa", "Isiklar sehri", "", 48.8, 2.3, listOf("Kultur")),
!!!                                                                       ^ error
265                 ),
266             ),
267             onIntent = {},

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/explore/src/main/kotlin/com/travel/companion/feature/explore/ExploreScreen.kt:264:77
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
261             state = ExploreState(
262                 popularCities = listOf(
263                     City("1", "Istanbul", "Turkiye", "Tarihi sehir", "", 41.0, 28.9, listOf("Tarih")),
264                     City("2", "Paris", "Fransa", "Isiklar sehri", "", 48.8, 2.3, listOf("Kultur")),
!!!                                                                             ^ error
265                 ),
266             ),
267             onIntent = {},

```

### style, MaxLineLength (1)

Line detected, which is longer than the defined maximum line length in the code style.

[Documentation](https://detekt.dev/docs/rules/style#maxlinelength)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/explore/src/main/kotlin/com/travel/companion/feature/explore/ExploreScreen.kt:180:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
177                     CircularProgressIndicator(Modifier.size(24.dp))
178                 } else {
179                     IconButton(onClick = { onIntent(ExploreIntent.SubmitSearch) }) {
180                         Icon(Icons.Default.Search, contentDescription = stringResource(R.string.explore_search_button_desc))
!!! ^ error
181                     }
182                 }
183             },

```

### style, UnusedPrivateMember (1)

Private function is unused and should be removed.

[Documentation](https://detekt.dev/docs/rules/style#unusedprivatemember)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/explore/src/main/kotlin/com/travel/companion/feature/explore/ExploreScreen.kt:258:13
```
Private function `ExploreScreenPreview` is unused.
```
```kotlin
255 
256 @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
257 @Composable
258 private fun ExploreScreenPreview() {
!!!             ^ error
259     com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
260         ExploreScreen(
261             state = ExploreState(

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2026-03-28 22:37:37 UTC
