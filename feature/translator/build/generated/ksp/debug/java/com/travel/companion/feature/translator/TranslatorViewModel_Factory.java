package com.travel.companion.feature.translator;

import com.travel.companion.core.data.repository.TranslationHistoryRepository;
import com.travel.companion.feature.translator.usecase.TranslateTextUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class TranslatorViewModel_Factory implements Factory<TranslatorViewModel> {
  private final Provider<TranslateTextUseCase> translateTextUseCaseProvider;

  private final Provider<TranslationHistoryRepository> translationHistoryRepositoryProvider;

  public TranslatorViewModel_Factory(Provider<TranslateTextUseCase> translateTextUseCaseProvider,
      Provider<TranslationHistoryRepository> translationHistoryRepositoryProvider) {
    this.translateTextUseCaseProvider = translateTextUseCaseProvider;
    this.translationHistoryRepositoryProvider = translationHistoryRepositoryProvider;
  }

  @Override
  public TranslatorViewModel get() {
    return newInstance(translateTextUseCaseProvider.get(), translationHistoryRepositoryProvider.get());
  }

  public static TranslatorViewModel_Factory create(
      javax.inject.Provider<TranslateTextUseCase> translateTextUseCaseProvider,
      javax.inject.Provider<TranslationHistoryRepository> translationHistoryRepositoryProvider) {
    return new TranslatorViewModel_Factory(Providers.asDaggerProvider(translateTextUseCaseProvider), Providers.asDaggerProvider(translationHistoryRepositoryProvider));
  }

  public static TranslatorViewModel_Factory create(
      Provider<TranslateTextUseCase> translateTextUseCaseProvider,
      Provider<TranslationHistoryRepository> translationHistoryRepositoryProvider) {
    return new TranslatorViewModel_Factory(translateTextUseCaseProvider, translationHistoryRepositoryProvider);
  }

  public static TranslatorViewModel newInstance(TranslateTextUseCase translateTextUseCase,
      TranslationHistoryRepository translationHistoryRepository) {
    return new TranslatorViewModel(translateTextUseCase, translationHistoryRepository);
  }
}
