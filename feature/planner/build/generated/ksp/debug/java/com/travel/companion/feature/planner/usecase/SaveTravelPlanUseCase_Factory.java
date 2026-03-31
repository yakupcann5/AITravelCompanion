package com.travel.companion.feature.planner.usecase;

import com.travel.companion.core.data.repository.TravelPlanRepository;
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
public final class SaveTravelPlanUseCase_Factory implements Factory<SaveTravelPlanUseCase> {
  private final Provider<TravelPlanRepository> travelPlanRepositoryProvider;

  public SaveTravelPlanUseCase_Factory(
      Provider<TravelPlanRepository> travelPlanRepositoryProvider) {
    this.travelPlanRepositoryProvider = travelPlanRepositoryProvider;
  }

  @Override
  public SaveTravelPlanUseCase get() {
    return newInstance(travelPlanRepositoryProvider.get());
  }

  public static SaveTravelPlanUseCase_Factory create(
      javax.inject.Provider<TravelPlanRepository> travelPlanRepositoryProvider) {
    return new SaveTravelPlanUseCase_Factory(Providers.asDaggerProvider(travelPlanRepositoryProvider));
  }

  public static SaveTravelPlanUseCase_Factory create(
      Provider<TravelPlanRepository> travelPlanRepositoryProvider) {
    return new SaveTravelPlanUseCase_Factory(travelPlanRepositoryProvider);
  }

  public static SaveTravelPlanUseCase newInstance(TravelPlanRepository travelPlanRepository) {
    return new SaveTravelPlanUseCase(travelPlanRepository);
  }
}
