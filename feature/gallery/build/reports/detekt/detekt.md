# detekt

## Metrics

* 48 number of properties

* 39 number of functions

* 15 number of classes

* 2 number of packages

* 9 number of kt files

## Complexity Report

* 874 lines of code (loc)

* 735 source lines of code (sloc)

* 506 logical lines of code (lloc)

* 44 comment lines of code (cloc)

* 75 cyclomatic complexity (mcc)

* 54 cognitive complexity

* 16 number of total code smells

* 5% comment source ratio

* 148 mcc per 1,000 lloc

* 31 code smells per 1,000 lloc

## Findings (16)

### complexity, LongMethod (1)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryScreen.kt:346:13
```
The function ImageDetailSheet is too long (53). The maximum length is 50.
```
```kotlin
343 
344 @OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
345 @Composable
346 private fun ImageDetailSheet(
!!!             ^ error
347     image: GalleryImage,
348     onDismiss: () -> Unit,
349     onShare: () -> Unit,

```

### complexity, TooManyFunctions (1)

Too many functions inside a/an file/class/object/interface always indicate a violation of the single responsibility principle. Maybe the file/class/object/interface wants to manage too many things at once. Extract functionality which clearly belongs together.

[Documentation](https://detekt.dev/docs/rules/complexity#toomanyfunctions)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryScreen.kt:1:1
```
File '/Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryScreen.kt' with '11' functions detected. Defined threshold inside files is set to '11'
```
```kotlin
1 package com.travel.companion.feature.gallery
! ^ error
2 
3 import androidx.compose.foundation.clickable
4 import androidx.compose.foundation.layout.Arrangement

```

### style, MagicNumber (2)

Report magic numbers. Magic number is a numeric literal that is not defined as a constant and hence it's unclear what the purpose of this number is. It's better to declare such numbers as constants and give them a proper name. By default, -1, 0, 1, and 2 are not considered to be magic numbers.

[Documentation](https://detekt.dev/docs/rules/style#magicnumber)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryScreen.kt:279:44
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
276         val windowWidth = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
277         when (windowWidth) {
278             WindowWidthSizeClass.COMPACT -> 2
279             WindowWidthSizeClass.MEDIUM -> 3
!!!                                            ^ error
280             else -> 4
281         }
282     }

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryScreen.kt:280:21
```
This expression contains a magic number. Consider defining it to a well named constant.
```
```kotlin
277         when (windowWidth) {
278             WindowWidthSizeClass.COMPACT -> 2
279             WindowWidthSizeClass.MEDIUM -> 3
280             else -> 4
!!!                     ^ error
281         }
282     }
283     LazyVerticalStaggeredGrid(

```

### style, MaxLineLength (9)

Line detected, which is longer than the defined maximum line length in the code style.

[Documentation](https://detekt.dev/docs/rules/style#maxlinelength)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryScreen.kt:262:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
259             if (isGenerating) {
260                 CircularProgressIndicator(modifier = Modifier.height(20.dp))
261             } else {
262                 Icon(Icons.Default.AutoAwesome, contentDescription = stringResource(R.string.gallery_generate_button_desc))
!!! ^ error
263             }
264         }
265     }

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryScreen.kt:329:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
326                 Icon(
327                     imageVector = if (image.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
328                     contentDescription = stringResource(R.string.gallery_favorite_button_desc),
329                     tint = if (image.isFavorite) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
!!! ^ error
330                 )
331             }
332         }

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryScreen.kt:402:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
399                         contentDescription = stringResource(R.string.gallery_favorite_button_desc),
400                     )
401                     Spacer(modifier = Modifier.width(8.dp))
402                     Text(if (image.isFavorite) stringResource(R.string.gallery_remove_favorite_button) else stringResource(R.string.gallery_add_favorite_button))
!!! ^ error
403                 }
404             }
405 

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryState.kt:30:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
27 )
28 
29 val sampleGalleryImages = listOf(
30     GalleryImage(id = "1", prompt = "Istanbul Bogazici gun batimi", imageUrl = "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200"),
!! ^ error
31     GalleryImage(id = "2", prompt = "Paris Eyfel Kulesi gece", imageUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34"),
32     GalleryImage(id = "3", prompt = "Tokyo sakura mevsimi", imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf"),
33     GalleryImage(id = "4", prompt = "Roma Kolezyum", imageUrl = "https://images.unsplash.com/photo-1552832230-c0197dd311b5"),

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryState.kt:31:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
28 
29 val sampleGalleryImages = listOf(
30     GalleryImage(id = "1", prompt = "Istanbul Bogazici gun batimi", imageUrl = "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200"),
31     GalleryImage(id = "2", prompt = "Paris Eyfel Kulesi gece", imageUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34"),
!! ^ error
32     GalleryImage(id = "3", prompt = "Tokyo sakura mevsimi", imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf"),
33     GalleryImage(id = "4", prompt = "Roma Kolezyum", imageUrl = "https://images.unsplash.com/photo-1552832230-c0197dd311b5"),
34     GalleryImage(id = "5", prompt = "Barcelona Sagrada Familia", imageUrl = "https://images.unsplash.com/photo-1583422409516-2895a77efded"),

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryState.kt:32:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
29 val sampleGalleryImages = listOf(
30     GalleryImage(id = "1", prompt = "Istanbul Bogazici gun batimi", imageUrl = "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200"),
31     GalleryImage(id = "2", prompt = "Paris Eyfel Kulesi gece", imageUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34"),
32     GalleryImage(id = "3", prompt = "Tokyo sakura mevsimi", imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf"),
!! ^ error
33     GalleryImage(id = "4", prompt = "Roma Kolezyum", imageUrl = "https://images.unsplash.com/photo-1552832230-c0197dd311b5"),
34     GalleryImage(id = "5", prompt = "Barcelona Sagrada Familia", imageUrl = "https://images.unsplash.com/photo-1583422409516-2895a77efded"),
35     GalleryImage(id = "6", prompt = "Dubai gece silueti", imageUrl = "https://images.unsplash.com/photo-1512453979798-5ea266f8880c"),

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryState.kt:33:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
30     GalleryImage(id = "1", prompt = "Istanbul Bogazici gun batimi", imageUrl = "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200"),
31     GalleryImage(id = "2", prompt = "Paris Eyfel Kulesi gece", imageUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34"),
32     GalleryImage(id = "3", prompt = "Tokyo sakura mevsimi", imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf"),
33     GalleryImage(id = "4", prompt = "Roma Kolezyum", imageUrl = "https://images.unsplash.com/photo-1552832230-c0197dd311b5"),
!! ^ error
34     GalleryImage(id = "5", prompt = "Barcelona Sagrada Familia", imageUrl = "https://images.unsplash.com/photo-1583422409516-2895a77efded"),
35     GalleryImage(id = "6", prompt = "Dubai gece silueti", imageUrl = "https://images.unsplash.com/photo-1512453979798-5ea266f8880c"),
36 )

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryState.kt:34:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
31     GalleryImage(id = "2", prompt = "Paris Eyfel Kulesi gece", imageUrl = "https://images.unsplash.com/photo-1502602898657-3e91760cbb34"),
32     GalleryImage(id = "3", prompt = "Tokyo sakura mevsimi", imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf"),
33     GalleryImage(id = "4", prompt = "Roma Kolezyum", imageUrl = "https://images.unsplash.com/photo-1552832230-c0197dd311b5"),
34     GalleryImage(id = "5", prompt = "Barcelona Sagrada Familia", imageUrl = "https://images.unsplash.com/photo-1583422409516-2895a77efded"),
!! ^ error
35     GalleryImage(id = "6", prompt = "Dubai gece silueti", imageUrl = "https://images.unsplash.com/photo-1512453979798-5ea266f8880c"),
36 )
37 

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryState.kt:35:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
32     GalleryImage(id = "3", prompt = "Tokyo sakura mevsimi", imageUrl = "https://images.unsplash.com/photo-1540959733332-eab4deabeeaf"),
33     GalleryImage(id = "4", prompt = "Roma Kolezyum", imageUrl = "https://images.unsplash.com/photo-1552832230-c0197dd311b5"),
34     GalleryImage(id = "5", prompt = "Barcelona Sagrada Familia", imageUrl = "https://images.unsplash.com/photo-1583422409516-2895a77efded"),
35     GalleryImage(id = "6", prompt = "Dubai gece silueti", imageUrl = "https://images.unsplash.com/photo-1512453979798-5ea266f8880c"),
!! ^ error
36 )
37 

```

### style, UnusedPrivateMember (1)

Private function is unused and should be removed.

[Documentation](https://detekt.dev/docs/rules/style#unusedprivatemember)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/main/kotlin/com/travel/companion/feature/gallery/GalleryScreen.kt:413:13
```
Private function `GalleryScreenPreview` is unused.
```
```kotlin
410 
411 @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
412 @Composable
413 private fun GalleryScreenPreview() {
!!!             ^ error
414     com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
415         GalleryScreen(
416             state = GalleryState(

```

### style, WildcardImport (2)

Wildcard imports should be replaced with imports using fully qualified class names. Wildcard imports can lead to naming conflicts. A library update can introduce naming clashes with your classes which results in compilation errors.

[Documentation](https://detekt.dev/docs/rules/style#wildcardimport)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/test/kotlin/com/travel/companion/feature/gallery/GalleryViewModelTest.kt:21:1
```
org.junit.Assert.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
18 import kotlinx.coroutines.flow.MutableStateFlow
19 import kotlinx.coroutines.test.advanceUntilIdle
20 import kotlinx.coroutines.test.runTest
21 import org.junit.Assert.*
!! ^ error
22 import org.junit.Before
23 import org.junit.Rule
24 import org.junit.Test

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/gallery/src/test/kotlin/com/travel/companion/feature/gallery/usecase/GenerateImageUseCaseTest.kt:11:1
```
org.junit.Assert.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
8   */
9  import com.travel.companion.core.ai.AiImageService
10 import kotlinx.coroutines.test.runTest
11 import org.junit.Assert.*
!! ^ error
12 import org.junit.Test
13 
14 class GenerateImageUseCaseTest {

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2026-03-28 22:37:37 UTC
