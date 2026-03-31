# detekt

## Metrics

* 38 number of properties

* 41 number of functions

* 14 number of classes

* 2 number of packages

* 9 number of kt files

## Complexity Report

* 880 lines of code (loc)

* 737 source lines of code (sloc)

* 521 logical lines of code (lloc)

* 45 comment lines of code (cloc)

* 63 cyclomatic complexity (mcc)

* 13 cognitive complexity

* 6 number of total code smells

* 6% comment source ratio

* 120 mcc per 1,000 lloc

* 11 code smells per 1,000 lloc

## Findings (6)

### complexity, LongMethod (1)

One method should have one responsibility. Long methods tend to handle many things at once. Prefer smaller methods to make them easier to understand.

[Documentation](https://detekt.dev/docs/rules/complexity#longmethod)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/translator/src/main/kotlin/com/travel/companion/feature/translator/TranslatorScreen.kt:54:14
```
The function TranslatorScreen is too long (56). The maximum length is 50.
```
```kotlin
51  * @date 2026-03-26
52  */
53 @Composable
54 internal fun TranslatorScreen(
!!              ^ error
55     state: TranslatorState,
56     onIntent: (TranslatorIntent) -> Unit,
57     modifier: Modifier = Modifier,

```

### style, MaxLineLength (2)

Line detected, which is longer than the defined maximum line length in the code style.

[Documentation](https://detekt.dev/docs/rules/style#maxlinelength)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/translator/src/main/kotlin/com/travel/companion/feature/translator/TranslatorScreen.kt:366:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
363             }
364         }
365         IconButton(onClick = onClear) {
366             Icon(Icons.Default.DeleteOutline, contentDescription = stringResource(R.string.translator_clear_button_desc))
!!! ^ error
367         }
368     }
369 }

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/translator/src/main/kotlin/com/travel/companion/feature/translator/TranslatorViewModel.kt:59:1
```
Line detected, which is longer than the defined maximum line length in the code style.
```
```kotlin
56                 )
57             }
58             is TranslatorIntent.CopyResult -> emitEffect(TranslatorEffect.CopiedToClipboard)
59             is TranslatorIntent.ClearAll -> reduce { TranslatorState(sourceLang = it.sourceLang, targetLang = it.targetLang, history = it.history) }
!! ^ error
60             is TranslatorIntent.LoadHistory -> observeHistory()
61             is TranslatorIntent.ClearHistory -> clearHistory()
62             is TranslatorIntent.SelectHistoryItem -> reduce {

```

### style, UnusedPrivateMember (1)

Private function is unused and should be removed.

[Documentation](https://detekt.dev/docs/rules/style#unusedprivatemember)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/translator/src/main/kotlin/com/travel/companion/feature/translator/TranslatorScreen.kt:373:13
```
Private function `TranslatorScreenPreview` is unused.
```
```kotlin
370 
371 @androidx.compose.ui.tooling.preview.Preview(showBackground = true)
372 @Composable
373 private fun TranslatorScreenPreview() {
!!!             ^ error
374     com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
375         TranslatorScreen(
376             state = TranslatorState(

```

### style, WildcardImport (2)

Wildcard imports should be replaced with imports using fully qualified class names. Wildcard imports can lead to naming conflicts. A library update can introduce naming clashes with your classes which results in compilation errors.

[Documentation](https://detekt.dev/docs/rules/style#wildcardimport)

* /Users/yakupcan/AndroidStudioProjects/AI/feature/translator/src/test/kotlin/com/travel/companion/feature/translator/TranslatorViewModelTest.kt:22:1
```
org.junit.Assert.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
19 import kotlinx.coroutines.flow.flowOf
20 import kotlinx.coroutines.test.advanceUntilIdle
21 import kotlinx.coroutines.test.runTest
22 import org.junit.Assert.*
!! ^ error
23 import org.junit.Before
24 import org.junit.Rule
25 import org.junit.Test

```

* /Users/yakupcan/AndroidStudioProjects/AI/feature/translator/src/test/kotlin/com/travel/companion/feature/translator/usecase/TranslateTextUseCaseTest.kt:13:1
```
org.junit.Assert.* is a wildcard import. Replace it with fully qualified imports.
```
```kotlin
10 import kotlinx.coroutines.flow.Flow
11 import kotlinx.coroutines.flow.flowOf
12 import kotlinx.coroutines.test.runTest
13 import org.junit.Assert.*
!! ^ error
14 import org.junit.Test
15 
16 class TranslateTextUseCaseTest {

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2026-03-28 22:37:37 UTC
