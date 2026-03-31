package com.travel.companion.core.ai.ondevice;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
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
public final class HybridAiTextService_Factory implements Factory<HybridAiTextService> {
  private final Provider<FeatureAvailabilityChecker> availabilityCheckerProvider;

  public HybridAiTextService_Factory(
      Provider<FeatureAvailabilityChecker> availabilityCheckerProvider) {
    this.availabilityCheckerProvider = availabilityCheckerProvider;
  }

  @Override
  public HybridAiTextService get() {
    return newInstance(availabilityCheckerProvider.get());
  }

  public static HybridAiTextService_Factory create(
      javax.inject.Provider<FeatureAvailabilityChecker> availabilityCheckerProvider) {
    return new HybridAiTextService_Factory(Providers.asDaggerProvider(availabilityCheckerProvider));
  }

  public static HybridAiTextService_Factory create(
      Provider<FeatureAvailabilityChecker> availabilityCheckerProvider) {
    return new HybridAiTextService_Factory(availabilityCheckerProvider);
  }

  public static HybridAiTextService newInstance(FeatureAvailabilityChecker availabilityChecker) {
    return new HybridAiTextService(availabilityChecker);
  }
}
