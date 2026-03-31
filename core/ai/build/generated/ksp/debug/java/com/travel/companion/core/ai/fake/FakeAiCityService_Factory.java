package com.travel.companion.core.ai.fake;

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
public final class FakeAiCityService_Factory implements Factory<FakeAiCityService> {
  @Override
  public FakeAiCityService get() {
    return newInstance();
  }

  public static FakeAiCityService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FakeAiCityService newInstance() {
    return new FakeAiCityService();
  }

  private static final class InstanceHolder {
    static final FakeAiCityService_Factory INSTANCE = new FakeAiCityService_Factory();
  }
}
