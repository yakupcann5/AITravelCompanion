package com.travel.companion.core.ai.fake;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class FakeAiImageService_Factory implements Factory<FakeAiImageService> {
  @Override
  public FakeAiImageService get() {
    return newInstance();
  }

  public static FakeAiImageService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FakeAiImageService newInstance() {
    return new FakeAiImageService();
  }

  private static final class InstanceHolder {
    static final FakeAiImageService_Factory INSTANCE = new FakeAiImageService_Factory();
  }
}
