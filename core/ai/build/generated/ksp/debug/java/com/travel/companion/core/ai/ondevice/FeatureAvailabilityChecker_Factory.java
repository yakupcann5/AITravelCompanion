package com.travel.companion.core.ai.ondevice;

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
public final class FeatureAvailabilityChecker_Factory implements Factory<FeatureAvailabilityChecker> {
  @Override
  public FeatureAvailabilityChecker get() {
    return newInstance();
  }

  public static FeatureAvailabilityChecker_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FeatureAvailabilityChecker newInstance() {
    return new FeatureAvailabilityChecker();
  }

  private static final class InstanceHolder {
    static final FeatureAvailabilityChecker_Factory INSTANCE = new FeatureAvailabilityChecker_Factory();
  }
}
