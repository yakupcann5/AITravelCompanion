package com.travel.companion.core.database.di;

import com.travel.companion.core.database.TravelDatabase;
import com.travel.companion.core.database.dao.TranslationHistoryDao;
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
public final class DatabaseModule_ProvideTranslationHistoryDaoFactory implements Factory<TranslationHistoryDao> {
  private final Provider<TravelDatabase> databaseProvider;

  public DatabaseModule_ProvideTranslationHistoryDaoFactory(
      Provider<TravelDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TranslationHistoryDao get() {
    return provideTranslationHistoryDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideTranslationHistoryDaoFactory create(
      javax.inject.Provider<TravelDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTranslationHistoryDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static DatabaseModule_ProvideTranslationHistoryDaoFactory create(
      Provider<TravelDatabase> databaseProvider) {
    return new DatabaseModule_ProvideTranslationHistoryDaoFactory(databaseProvider);
  }

  public static TranslationHistoryDao provideTranslationHistoryDao(TravelDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideTranslationHistoryDao(database));
  }
}
