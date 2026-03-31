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
public final class GetRecentSearchesUseCase_Factory implements Factory<GetRecentSearchesUseCase> {
  private final Provider<CityRepository> cityRepositoryProvider;

  public GetRecentSearchesUseCase_Factory(Provider<CityRepository> cityRepositoryProvider) {
    this.cityRepositoryProvider = cityRepositoryProvider;
  }

  @Override
  public GetRecentSearchesUseCase get() {
    return newInstance(cityRepositoryProvider.get());
  }

  public static GetRecentSearchesUseCase_Factory create(
      javax.inject.Provider<CityRepository> cityRepositoryProvider) {
    return new GetRecentSearchesUseCase_Factory(Providers.asDaggerProvider(cityRepositoryProvider));
  }

  public static GetRecentSearchesUseCase_Factory create(
      Provider<CityRepository> cityRepositoryProvider) {
    return new GetRecentSearchesUseCase_Factory(cityRepositoryProvider);
  }

  public static GetRecentSearchesUseCase newInstance(CityRepository cityRepository) {
    return new GetRecentSearchesUseCase(cityRepository);
  }
}
