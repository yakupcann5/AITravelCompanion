# detekt

## Metrics

* 0 number of properties

* 7 number of functions

* 0 number of classes

* 1 number of packages

* 4 number of kt files

## Complexity Report

* 219 lines of code (loc)

* 178 source lines of code (sloc)

* 103 logical lines of code (lloc)

* 25 comment lines of code (cloc)

* 10 cyclomatic complexity (mcc)

* 1 cognitive complexity

* 5 number of total code smells

* 14% comment source ratio

* 97 mcc per 1,000 lloc

* 48 code smells per 1,000 lloc

## Findings (5)

### complexity, LongMethod (1)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* /Users/yakupcan/AndroidStudioProjects/AI/core/ui/src/main/kotlin/com/travel/companion/core/ui/CityCard.kt:33:5
```
The function CityCard is too long (55). The maximum length is 50.
```
```kotlin
30  * @date 2026-03-27
31  */
32 @Composable
33 fun CityCard(
!!     ^ error
34     city: City,
35     onClick: () -> Unit,
36     modifier: Modifier = Modifier,

```

### performance, SpreadOperator (1)

In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.

[Documentation](https://detekt.dev/docs/rules/performance#spreadoperator)

* /Users/yakupcan/AndroidStudioProjects/AI/core/ui/src/main/kotlin/com/travel/companion/core/ui/UiTextExtensions.kt:16:47
```
In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.
```
```kotlin
13  */
14 @Composable
15 fun UiText.asString(): String = when (this) {
16     is UiText.StringResource -> stringResource(resId, *args.toTypedArray())
!!                                               ^ error
17     is UiText.DynamicString -> value
18 }
19 

```

### style, UnusedPrivateMember (3)

Private function is unused and should be removed.

[Documentation](https://detekt.dev/docs/rules/style#unusedprivatemember)

* /Users/yakupcan/AndroidStudioProjects/AI/core/ui/src/main/kotlin/com/travel/companion/core/ui/CityCard.kt:96:13
```
Private function `CityCardPreview` is unused.
```
```kotlin
93  
94  @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
95  @Composable
96  private fun CityCardPreview() {
!!              ^ error
97      com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
98          CityCard(
99              city = City(

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ui/src/main/kotlin/com/travel/companion/core/ui/ErrorScreen.kt:49:13
```
Private function `ErrorScreenPreview` is unused.
```
```kotlin
46 
47 @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
48 @Composable
49 private fun ErrorScreenPreview() {
!!             ^ error
50     com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
51         ErrorScreen(message = "Bir hata olustu", onRetry = {})
52     }

```

* /Users/yakupcan/AndroidStudioProjects/AI/core/ui/src/main/kotlin/com/travel/companion/core/ui/LoadingIndicator.kt:28:13
```
Private function `LoadingIndicatorPreview` is unused.
```
```kotlin
25 
26 @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
27 @Composable
28 private fun LoadingIndicatorPreview() {
!!             ^ error
29     com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
30         LoadingIndicator()
31     }

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2026-03-28 22:37:36 UTC
