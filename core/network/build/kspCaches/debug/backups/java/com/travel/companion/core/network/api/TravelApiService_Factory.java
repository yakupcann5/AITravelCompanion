package com.travel.companion.core.network.api;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import io.ktor.client.HttpClient;
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
public final class TravelApiService_Factory implements Factory<TravelApiService> {
  private final Provider<HttpClient> clientProvider;

  public TravelApiService_Factory(Provider<HttpClient> clientProvider) {
    this.clientProvider = clientProvider;
  }

  @Override
  public TravelApiService get() {
    return newInstance(clientProvider.get());
  }

  public static TravelApiService_Factory create(javax.inject.Provider<HttpClient> clientProvider) {
    return new TravelApiService_Factory(Providers.asDaggerProvider(clientProvider));
  }

  public static TravelApiService_Factory create(Provider<HttpClient> clientProvider) {
    return new TravelApiService_Factory(clientProvider);
  }

  public static TravelApiService newInstance(HttpClient client) {
    return new TravelApiService(client);
  }
}
