package com.travel.companion.core.data.repository;

import com.travel.companion.core.database.dao.TranslationHistoryDao;
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
public final class OfflineFirstTranslationHistoryRepository_Factory implements Factory<OfflineFirstTranslationHistoryRepository> {
  private final Provider<TranslationHistoryDao> translationHistoryDaoProvider;

  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  public OfflineFirstTranslationHistoryRepository_Factory(
      Provider<TranslationHistoryDao> translationHistoryDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    this.translationHistoryDaoProvider = translationHistoryDaoProvider;
    this.ioDispatcherProvider = ioDispatcherProvider;
  }

  @Override
  public OfflineFirstTranslationHistoryRepository get() {
    return newInstance(translationHistoryDaoProvider.get(), ioDispatcherProvider.get());
  }

  public static OfflineFirstTranslationHistoryRepository_Factory create(
      javax.inject.Provider<TranslationHistoryDao> translationHistoryDaoProvider,
      javax.inject.Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new OfflineFirstTranslationHistoryRepository_Factory(Providers.asDaggerProvider(translationHistoryDaoProvider), Providers.asDaggerProvider(ioDispatcherProvider));
  }

  public static OfflineFirstTranslationHistoryRepository_Factory create(
      Provider<TranslationHistoryDao> translationHistoryDaoProvider,
      Provider<CoroutineDispatcher> ioDispatcherProvider) {
    return new OfflineFirstTranslationHistoryRepository_Factory(translationHistoryDaoProvider, ioDispatcherProvider);
  }

  public static OfflineFirstTranslationHistoryRepository newInstance(
      TranslationHistoryDao translationHistoryDao, CoroutineDispatcher ioDispatcher) {
    return new OfflineFirstTranslationHistoryRepository(translationHistoryDao, ioDispatcher);
  }
}
