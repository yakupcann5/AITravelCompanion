# detekt

## Metrics

* 0 number of properties

* 5 number of functions

* 9 number of classes

* 2 number of packages

* 3 number of kt files

## Complexity Report

* 94 lines of code (loc)

* 56 source lines of code (sloc)

* 37 logical lines of code (lloc)

* 19 comment lines of code (cloc)

* 7 cyclomatic complexity (mcc)

* 1 cognitive complexity

* 1 number of total code smells

* 33% comment source ratio

* 189 mcc per 1,000 lloc

* 27 code smells per 1,000 lloc

## Findings (1)

### performance, SpreadOperator (1)

In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.

[Documentation](https://detekt.dev/docs/rules/performance#spreadoperator)

* /Users/yakupcan/AndroidStudioProjects/AI/core/common/src/main/kotlin/com/travel/companion/core/common/UiText.kt:23:47
```
In most cases using a spread operator causes a full copy of the array to be created before calling a method. This may result in a performance penalty.
```
```kotlin
20     data class DynamicString(val value: String) : UiText
21 
22     fun asString(context: Context): String = when (this) {
23         is StringResource -> context.getString(resId, *args.toTypedArray())
!!                                               ^ error
24         is DynamicString -> value
25     }
26 }

```

generated with [detekt version 1.23.7](https://detekt.dev/) on 2026-03-28 22:37:36 UTC
