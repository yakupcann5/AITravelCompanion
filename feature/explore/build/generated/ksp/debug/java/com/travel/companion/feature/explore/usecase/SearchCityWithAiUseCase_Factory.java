package com.travel.companion.feature.explore.usecase;

import com.travel.companion.core.ai.AiCityService;
import com.travel.companion.core.data.repository.CityRepository;
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
public final class SearchCityWithAiUseCase_Factory implements Factory<SearchCityWithAiUseCase> {
  private final Provider<CityRepository> cityRepositoryProvider;

  private final Provider<AiCityService> aiCityServiceProvider;

  public SearchCityWithAiUseCase_Factory(Provider<CityRepository> cityRepositoryProvider,
      Provider<AiCityService> aiCityServiceProvider) {
    this.cityRepositoryProvider = cityRepositoryProvider;
    this.aiCityServiceProvider = aiCityServiceProvider;
  }

  @Override
  public SearchCityWithAiUseCase get() {
    return newInstance(cityRepositoryProvider.get(), aiCityServiceProvider.get());
  }

  public static SearchCityWithAiUseCase_Factory create(
      javax.inject.Provider<CityRepository> cityRepositoryProvider,
      javax.inject.Provider<AiCityService> aiCityServiceProvider) {
    return new SearchCityWithAiUseCase_Factory(Providers.asDaggerProvider(cityRepositoryProvider), Providers.asDaggerProvider(aiCityServiceProvider));
  }

  public static SearchCityWithAiUseCase_Factory create(
      Provider<CityRepository> cityRepositoryProvider,
      Provider<AiCityService> aiCityServiceProvider) {
    return new SearchCityWithAiUseCase_Factory(cityRepositoryProvider, aiCityServiceProvider);
  }

  public static SearchCityWithAiUseCase newInstance(CityRepository cityRepository,
      AiCityService aiCityService) {
    return new SearchCityWithAiUseCase(cityRepository, aiCityService);
  }
}
