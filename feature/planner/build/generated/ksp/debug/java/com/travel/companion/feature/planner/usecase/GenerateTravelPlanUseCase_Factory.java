package com.travel.companion.feature.planner.usecase;

import com.travel.companion.core.ai.AiPlannerService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Provider;
import dagger.internal.Providers;
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
public final class GenerateTravelPlanUseCase_Factory implements Factory<GenerateTravelPlanUseCase> {
  private final Provider<AiPlannerService> aiPlannerServiceProvider;

  public GenerateTravelPlanUseCase_Factory(Provider<AiPlannerService> aiPlannerServiceProvider) {
    this.aiPlannerServiceProvider = aiPlannerServiceProvider;
  }

  @Override
  public GenerateTravelPlanUseCase get() {
    return newInstance(aiPlannerServiceProvider.get());
  }

  public static GenerateTravelPlanUseCase_Factory create(
      javax.inject.Provider<AiPlannerService> aiPlannerServiceProvider) {
    return new GenerateTravelPlanUseCase_Factory(Providers.asDaggerProvider(aiPlannerServiceProvider));
  }

  public static GenerateTravelPlanUseCase_Factory create(
      Provider<AiPlannerService> aiPlannerServiceProvider) {
    return new GenerateTravelPlanUseCase_Factory(aiPlannerServiceProvider);
  }

  public static GenerateTravelPlanUseCase newInstance(AiPlannerService aiPlannerService) {
    return new GenerateTravelPlanUseCase(aiPlannerService);
  }
}
