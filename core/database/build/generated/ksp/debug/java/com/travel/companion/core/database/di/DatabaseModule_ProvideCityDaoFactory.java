package com.travel.companion.core.database.di;

import com.travel.companion.core.database.TravelDatabase;
import com.travel.companion.core.database.dao.CityDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvideCityDaoFactory implements Factory<CityDao> {
  private final Provider<TravelDatabase> databaseProvider;

  public DatabaseModule_ProvideCityDaoFactory(Provider<TravelDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public CityDao get() {
    return provideCityDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideCityDaoFactory create(
      javax.inject.Provider<TravelDatabase> databaseProvider) {
    return new DatabaseModule_ProvideCityDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static DatabaseModule_ProvideCityDaoFactory create(
      Provider<TravelDatabase> databaseProvider) {
    return new DatabaseModule_ProvideCityDaoFactory(databaseProvider);
  }

  public static CityDao provideCityDao(TravelDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideCityDao(database));
  }
}
