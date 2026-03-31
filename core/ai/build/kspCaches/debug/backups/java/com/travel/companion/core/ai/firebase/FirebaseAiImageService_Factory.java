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
public final class FirebaseAiImageService_Factory implements Factory<FirebaseAiImageService> {
  @Override
  public FirebaseAiImageService get() {
    return newInstance();
  }

  public static FirebaseAiImageService_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FirebaseAiImageService newInstance() {
    return new FirebaseAiImageService();
  }

  private static final class InstanceHolder {
    static final FirebaseAiImageService_Factory INSTANCE = new FirebaseAiImageService_Factory();
  }
}
