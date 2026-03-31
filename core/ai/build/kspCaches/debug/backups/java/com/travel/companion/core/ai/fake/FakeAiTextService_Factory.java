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
public final class FakeAiTextService_Factory implements Factory<FakeAiTextService> {
  @Override
  public FakeAiTextService get() {
    return newInstance();
  }

  public static FakeAiTextService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FakeAiTextService newInstance() {
    return new FakeAiTextService();
  }

  private static final class InstanceHolder {
    static final FakeAiTextService_Factory INSTANCE = new FakeAiTextService_Factory();
  }
}
