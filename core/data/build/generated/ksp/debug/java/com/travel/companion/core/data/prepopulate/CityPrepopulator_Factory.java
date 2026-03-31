package com.travel.companion.core.data.prepopulate;

import android.content.Context;
import com.travel.companion.core.database.dao.CityDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata({
    "dagger.hilt.android.qualifiers.ApplicationContext",
    "com.travel.companion.core.common.di.IoDispatcher"
})
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
public final class CityPrepopulator_Factory implements Factory<CityPrepopulator> {
  private final Provider<CityDao> cityDaoProvider;

  private final Provider<Context> contextProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public CityPrepopulator_Factory(Provider<CityDao> cityDaoProvider,
      Provider<Context> contextProvider, Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.cityDaoProvider = cityDaoProvider;
    this.contextProvider = contextProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public CityPrepopulator get() {
    return newInstance(cityDaoProvider.get(), contextProvider.get(), ioDispatcherProvider.get());
  }

  public static CityPrepopulator_Factory create(javax.inject.Provider<CityDao> cityDaoProvider,
      javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new CityPrepopulator_Factory(Providers.asDaggerProvider(cityDaoProvider), Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static CityPrepopulator_Factory create(Provider<CityDao> cityDaoProvider,
      Provider<Context> contextProvider, Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new CityPrepopulator_Factory(cityDaoProvider, contextProvider, ioDispatcherProvider);
  }

  public static CityPrepopulator newInstance(CityDao cityDao, Context context,
      CoroutineDispatcher ioDispatcher) {
    return new CityPrepopulator(cityDao, context, ioDispatcher);
  }
}
