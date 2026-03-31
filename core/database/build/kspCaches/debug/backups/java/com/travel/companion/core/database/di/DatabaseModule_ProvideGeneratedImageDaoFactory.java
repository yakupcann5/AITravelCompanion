package com.travel.companion.core.database.di;

import com.travel.companion.core.database.TravelDatabase;
import com.travel.companion.core.database.dao.GeneratedImageDao;
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
public final class DatabaseModule_ProvideGeneratedImageDaoFactory implements Factory<GeneratedImageDao> {
  private final Provider<TravelDatabase> databaseProvider;

  public DatabaseModule_ProvideGeneratedImageDaoFactory(Provider<TravelDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public GeneratedImageDao get() {
    return provideGeneratedImageDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideGeneratedImageDaoFactory create(
      javax.inject.Provider<TravelDatabase> databaseProvider) {
    return new DatabaseModule_ProvideGeneratedImageDaoFactory(Providers.asDaggerProvider(databaseProvider));
  }

  public static DatabaseModule_ProvideGeneratedImageDaoFactory create(
      Provider<TravelDatabase> databaseProvider) {
    return new DatabaseModule_ProvideGeneratedImageDaoFactory(databaseProvider);
  }

  public static GeneratedImageDao provideGeneratedImageDao(TravelDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideGeneratedImageDao(database));
  }
}
