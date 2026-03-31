package com.travel.companion.feature.gallery.usecase;

import com.travel.companion.core.ai.AiImageService;
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
public final class GenerateImageUseCase_Factory implements Factory<GenerateImageUseCase> {
  private final Provider<AiImageService> aiImageServiceProvider;

  public GenerateImageUseCase_Factory(Provider<AiImageService> aiImageServiceProvider) {
    this.aiImageServiceProvider = aiImageServiceProvider;
  }

  @Override
  public GenerateImageUseCase get() {
    return newInstance(aiImageServiceProvider.get());
  }

  public static GenerateImageUseCase_Factory create(
      javax.inject.Provider<AiImageService> aiImageServiceProvider) {
    return new GenerateImageUseCase_Factory(Providers.asDaggerProvider(aiImageServiceProvider));
  }

  public static GenerateImageUseCase_Factory create(
      Provider<AiImageService> aiImageServiceProvider) {
    return new GenerateImageUseCase_Factory(aiImageServiceProvider);
  }

  public static GenerateImageUseCase newInstance(AiImageService aiImageService) {
    return new GenerateImageUseCase(aiImageService);
  }
}
