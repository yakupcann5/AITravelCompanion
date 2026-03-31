package com.travel.companion.feature.gallery;

import com.travel.companion.core.data.repository.GeneratedImageRepository;
import com.travel.companion.feature.gallery.usecase.GenerateImageUseCase;
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
public final class GalleryViewModel_Factory implements Factory<GalleryViewModel> {
  private final Provider<GenerateImageUseCase> generateImageUseCaseProvider;

  private final Provider<GeneratedImageRepository> generatedImageRepositoryProvider;

  public GalleryViewModel_Factory(Provider<GenerateImageUseCase> generateImageUseCaseProvider,
      Provider<GeneratedImageRepository> generatedImageRepositoryProvider) {
    this.generateImageUseCaseProvider = generateImageUseCaseProvider;
    this.generatedImageRepositoryProvider = generatedImageRepositoryProvider;
  }

  @Override
  public GalleryViewModel get() {
    return newInstance(generateImageUseCaseProvider.get(), generatedImageRepositoryProvider.get());
  }

  public static GalleryViewModel_Factory create(
      javax.inject.Provider<GenerateImageUseCase> generateImageUseCaseProvider,
      javax.inject.Provider<GeneratedImageRepository> generatedImageRepositoryProvider) {
    return new GalleryViewModel_Factory(Providers.asDaggerProvider(generateImageUseCaseProvider), Providers.asDaggerProvider(generatedImageRepositoryProvider));
  }

  public static GalleryViewModel_Factory create(
      Provider<GenerateImageUseCase> generateImageUseCaseProvider,
      Provider<GeneratedImageRepository> generatedImageRepositoryProvider) {
    return new GalleryViewModel_Factory(generateImageUseCaseProvider, generatedImageRepositoryProvider);
  }

  public static GalleryViewModel newInstance(GenerateImageUseCase generateImageUseCase,
      GeneratedImageRepository generatedImageRepository) {
    return new GalleryViewModel(generateImageUseCase, generatedImageRepository);
  }
}
