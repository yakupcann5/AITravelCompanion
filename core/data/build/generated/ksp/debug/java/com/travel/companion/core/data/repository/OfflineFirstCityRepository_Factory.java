package com.travel.companion.core.data.repository;

import com.travel.companion.core.data.prepopulate.CityPrepopulator;
import com.travel.companion.core.database.dao.CityDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
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
public final class OfflineFirstCityRepository_Factory implements Factory<OfflineFirstCityRepository> {
  private final Provider<CityDao> cityDaoProvider;

  private final Provider<CityPrepopulator> cityPrepopulatorProvider;

  public OfflineFirstCityRepository_Factory(Provider<CityDao> cityDaoProvider,
      Provider<CityPrepopulator> cityPrepopulatorProvider) {
    this.cityDaoProvider = cityDaoProvider;
    this.cityPrepopulatorProvider = cityPrepopulatorProvider;
  }

  @Override
  public OfflineFirstCityRepository get() {
    return newInstance(cityDaoProvider.get(), cityPrepopulatorProvider.get());
  }

  public static OfflineFirstCityRepository_Factory create(
      javax.inject.Provider<CityDao> cityDaoProvider,
      javax.inject.Provider<CityPrepopulator> cityPrepopulatorProvider) {
    return new OfflineFirstCityRepository_Factory(Providers.asDaggerProvider(cityDaoProvider), Providers.asDaggerProvider(cityPrepopulatorProvider));
  }

  public static OfflineFirstCityRepository_Factory create(Provider<CityDao> cityDaoProvider,
      Provider<CityPrepopulator> cityPrepopulatorProvider) {
    return new OfflineFirstCityRepository_Factory(cityDaoProvider, cityPrepopulatorProvider);
  }

  public static OfflineFirstCityRepository newInstance(CityDao cityDao,
      CityPrepopulator cityPrepopulator) {
    return new OfflineFirstCityRepository(cityDao, cityPrepopulator);
  }
}
