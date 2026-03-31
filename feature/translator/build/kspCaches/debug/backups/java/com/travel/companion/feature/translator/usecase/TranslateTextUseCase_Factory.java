package com.travel.companion.feature.translator.usecase;

import com.travel.companion.core.ai.AiTextService;
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
public final class TranslateTextUseCase_Factory implements Factory<TranslateTextUseCase> {
  private final Provider<AiTextService> aiTextServiceProvider;

  public TranslateTextUseCase_Factory(Provider<AiTextService> aiTextServiceProvider) {
    this.aiTextServiceProvider = aiTextServiceProvider;
  }

  @Override
  public TranslateTextUseCase get() {
    return newInstance(aiTextServiceProvider.get());
  }

  public static TranslateTextUseCase_Factory create(
      javax.inject.Provider<AiTextService> aiTextServiceProvider) {
    return new TranslateTextUseCase_Factory(Providers.asDaggerProvider(aiTextServiceProvider));
  }

  public static TranslateTextUseCase_Factory create(Provider<AiTextService> aiTextServiceProvider) {
    return new TranslateTextUseCase_Factory(aiTextServiceProvider);
  }

  public static TranslateTextUseCase newInstance(AiTextService aiTextService) {
    return new TranslateTextUseCase(aiTextService);
  }
}
