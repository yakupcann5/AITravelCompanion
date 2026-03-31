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
public final class FirebaseAiTextService_Factory implements Factory<FirebaseAiTextService> {
  @Override
  public FirebaseAiTextService get() {
    return newInstance();
  }

  public static FirebaseAiTextService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseAiTextService newInstance() {
    return new FirebaseAiTextService();
  }

  private static final class InstanceHolder {
    static final FirebaseAiTextService_Factory INSTANCE = new FirebaseAiTextService_Factory();
  }
}
