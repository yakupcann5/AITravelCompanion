package com.travel.companion.core.data.repository;

import android.content.Context;
import com.travel.companion.core.database.dao.GeneratedImageDao;
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
public final class OfflineFirstGeneratedImageRepository_Factory implements Factory<OfflineFirstGeneratedImageRepository> {
  private final Provider<GeneratedImageDao> generatedImageDaoProvider;

  private final Provider<Context> contextProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public OfflineFirstGeneratedImageRepository_Factory(
      Provider<GeneratedImageDao> generatedImageDaoProvider, Provider<Context> contextProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.generatedImageDaoProvider = generatedImageDaoProvider;
    this.contextProvider = contextProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public OfflineFirstGeneratedImageRepository get() {
    return newInstance(generatedImageDaoProvider.get(), contextProvider.get(), ioDispatcherProvider.get());
  }

  public static OfflineFirstGeneratedImageRepository_Factory create(
      javax.inject.Provider<GeneratedImageDao> generatedImageDaoProvider,
      javax.inject.Provider<Context> contextProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new OfflineFirstGeneratedImageRepository_Factory(Providers.asDaggerProvider(generatedImageDaoProvider), Providers.asDaggerProvider(contextProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static OfflineFirstGeneratedImageRepository_Factory create(
      Provider<GeneratedImageDao> generatedImageDaoProvider, Provider<Context> contextProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new OfflineFirstGeneratedImageRepository_Factory(generatedImageDaoProvider, contextProvider, ioDispatcherProvider);
  }

  public static OfflineFirstGeneratedImageRepository newInstance(
      GeneratedImageDao generatedImageDao, Context context, CoroutineDispatcher ioDispatcher) {
    return new OfflineFirstGeneratedImageRepository(generatedImageDao, context, ioDispatcher);
  }
}
