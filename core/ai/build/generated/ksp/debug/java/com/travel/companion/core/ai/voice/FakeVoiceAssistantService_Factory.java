package com.travel.companion.core.ai.voice;

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
public final class FakeVoiceAssistantService_Factory implements Factory<FakeVoiceAssistantService> {
  @Override
  public FakeVoiceAssistantService get() {
    return newInstance();
  }

  public static FakeVoiceAssistantService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FakeVoiceAssistantService newInstance() {
    return new FakeVoiceAssistantService();
  }

  private static final class InstanceHolder {
    static final FakeVoiceAssistantService_Factory INSTANCE = new FakeVoiceAssistantService_Factory();
  }
}
