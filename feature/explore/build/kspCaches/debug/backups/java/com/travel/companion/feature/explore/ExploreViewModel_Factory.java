package com.travel.companion.feature.explore;

import com.travel.companion.core.data.repository.CityRepository;
import com.travel.companion.feature.explore.usecase.GetPopularCitiesUseCase;
import com.travel.companion.feature.explore.usecase.GetRecentSearchesUseCase;
import com.travel.companion.feature.explore.usecase.SearchCityWithAiUseCase;
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
public final class ExploreViewModel_Factory implements Factory<ExploreViewModel> {
  private final Provider<GetPopularCitiesUseCase> getPopularCitiesUseCaseProvider;

  private final Provider<GetRecentSearchesUseCase> getRecentSearchesUseCaseProvider;

  private final Provider<SearchCityWithAiUseCase> searchCityWithAiUseCaseProvider;

  private final Provider<CityRepository> cityRepositoryProvider;

  public ExploreViewModel_Factory(Provider<GetPopularCitiesUseCase> getPopularCitiesUseCaseProvider,
      Provider<GetRecentSearchesUseCase> getRecentSearchesUseCaseProvider,
      Provider<SearchCityWithAiUseCase> searchCityWithAiUseCaseProvider,
      Provider<CityRepository> cityRepositoryProvider) {
    this.getPopularCitiesUseCaseProvider = getPopularCitiesUseCaseProvider;
    this.getRecentSearchesUseCaseProvider = getRecentSearchesUseCaseProvider;
    this.searchCityWithAiUseCaseProvider = searchCityWithAiUseCaseProvider;
    this.cityRepositoryProvider = cityRepositoryProvider;
  }

  @Override
  public ExploreViewModel get() {
    return newInstance(getPopularCitiesUseCaseProvider.get(), getRecentSearchesUseCaseProvider.get(), searchCityWithAiUseCaseProvider.get(), cityRepositoryProvider.get());
  }

  public static ExploreViewModel_Factory create(
      javax.inject.Provider<GetPopularCitiesUseCase> getPopularCitiesUseCaseProvider,
      javax.inject.Provider<GetRecentSearchesUseCase> getRecentSearchesUseCaseProvider,
      javax.inject.Provider<SearchCityWithAiUseCase> searchCityWithAiUseCaseProvider,
      javax.inject.Provider<CityRepository> cityRepositoryProvider) {
    return new ExploreViewModel_Factory(Providers.asDaggerProvider(getPopularCitiesUseCaseProvider), Providers.asDaggerProvider(getRecentSearchesUseCaseProvider), Providers.asDaggerProvider(searchCityWithAiUseCaseProvider), Providers.asDaggerProvider(cityRepositoryProvider));
  }

  public static ExploreViewModel_Factory create(
      Provider<GetPopularCitiesUseCase> getPopularCitiesUseCaseProvider,
      Provider<GetRecentSearchesUseCase> getRecentSearchesUseCaseProvider,
      Provider<SearchCityWithAiUseCase> searchCityWithAiUseCaseProvider,
      Provider<CityRepository> cityRepositoryProvider) {
    return new ExploreViewModel_Factory(getPopularCitiesUseCaseProvider, getRecentSearchesUseCaseProvider, searchCityWithAiUseCaseProvider, cityRepositoryProvider);
  }

  public static ExploreViewModel newInstance(GetPopularCitiesUseCase getPopularCitiesUseCase,
      GetRecentSearchesUseCase getRecentSearchesUseCase,
      SearchCityWithAiUseCase searchCityWithAiUseCase, CityRepository cityRepository) {
    return new ExploreViewModel(getPopularCitiesUseCase, getRecentSearchesUseCase, searchCityWithAiUseCase, cityRepository);
  }
}
