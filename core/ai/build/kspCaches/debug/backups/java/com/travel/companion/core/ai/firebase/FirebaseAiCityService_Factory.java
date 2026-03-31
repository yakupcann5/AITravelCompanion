package com.travel.companion.core.ai.firebase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class FirebaseAiCityService_Factory implements Factory<FirebaseAiCityService> {
  @Override
  public FirebaseAiCityService get() {
    return newInstance();
  }

  public static FirebaseAiCityService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseAiCityService newInstance() {
    return new FirebaseAiCityService();
  }

  private static final class InstanceHolder {
    static final FirebaseAiCityService_Factory INSTANCE = new FirebaseAiCityService_Factory();
  }
}
