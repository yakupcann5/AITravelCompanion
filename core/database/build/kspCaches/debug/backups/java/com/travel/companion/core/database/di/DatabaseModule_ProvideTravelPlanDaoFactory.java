package com.travel.companion.core.database.di;

import com.travel.companion.core.database.TravelDatabase;
import com.travel.companion.core.database.dao.TravelPlanDao;
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
public final class DatabaseModule_ProvideTravelPlanDaoFactory implements Factory<TravelPlanDao> {
  private final Provider<TravelDatabase> databaseProvider;

  public DatabaseModule_ProvideTravelPlanDaoFactory(Provider<TravelDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TravelPlanDao get() {
    return provideTravelPlanDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTravelPlanDaoFactory create(
      javax.inject.Provider<TravelDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTravelPlanDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static DatabaseModule_ProvideTravelPlanDaoFactory create(
      Provider<TravelDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTravelPlanDaoFactory(databaseProvider);
  }

  public static TravelPlanDao provideTravelPlanDao(TravelDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTravelPlanDao(database));
  }
}
