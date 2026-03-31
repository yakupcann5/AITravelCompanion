package com.travel.companion.core.data.repository;

import com.travel.companion.core.database.dao.TravelPlanDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("com.travel.companion.core.common.di.IoDispatcher")
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
public final class OfflineFirstTravelPlanRepository_Factory implements Factory<OfflineFirstTravelPlanRepository> {
  private final Provider<TravelPlanDao> travelPlanDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public OfflineFirstTravelPlanRepository_Factory(Provider<TravelPlanDao> travelPlanDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.travelPlanDaoProvider = travelPlanDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public OfflineFirstTravelPlanRepository get() {
    return newInstance(travelPlanDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static OfflineFirstTravelPlanRepository_Factory create(
      javax.inject.Provider<TravelPlanDao> travelPlanDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new OfflineFirstTravelPlanRepository_Factory(Providers.asDaggerProvider(travelPlanDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static OfflineFirstTravelPlanRepository_Factory create(
      Provider<TravelPlanDao> travelPlanDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new OfflineFirstTravelPlanRepository_Factory(travelPlanDaoProvider, ioDispatcherProvider);
  }

  public static OfflineFirstTravelPlanRepository newInstance(TravelPlanDao travelPlanDao,
      CoroutineDispatcher ioDispatcher) {
    return new OfflineFirstTravelPlanRepository(travelPlanDao, ioDispatcher);
  }
}
