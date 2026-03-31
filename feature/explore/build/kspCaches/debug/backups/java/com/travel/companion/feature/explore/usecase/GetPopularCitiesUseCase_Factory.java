package com.travel.companion.feature.explore.usecase;

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
public final class GetPopularCitiesUseCase_Factory implements Factory<GetPopularCitiesUseCase> {
  private final Provider<CityRepository> cityRepositoryProvider;

  public GetPopularCitiesUseCase_Factory(Provider<CityRepository> cityRepositoryProvider) {
    this.cityRepositoryProvider = cityRepositoryProvider;
  }

  @Override
  public GetPopularCitiesUseCase get() {
    return newInstance(cityRepositoryProvider.get());
  }

  public static GetPopularCitiesUseCase_Factory create(
      javax.inject.Provider<CityRepository> cityRepositoryProvider) {
    return new GetPopularCitiesUseCase_Factory(Providers.asDaggerProvider(cityRepositoryProvider));
  }

  public static GetPopularCitiesUseCase_Factory create(
      Provider<CityRepository> cityRepositoryProvider) {
    return new GetPopularCitiesUseCase_Factory(cityRepositoryProvider);
  }

  public static GetPopularCitiesUseCase newInstance(CityRepository cityRepository) {
    return new GetPopularCitiesUseCase(cityRepository);
  }
}
