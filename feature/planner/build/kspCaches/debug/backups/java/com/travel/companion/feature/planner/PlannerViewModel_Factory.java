package com.travel.companion.feature.planner;

import androidx.lifecycle.SavedStateHandle;
import com.travel.companion.core.ai.voice.VoiceAssistantService;
import com.travel.companion.core.data.repository.CityRepository;
import com.travel.companion.feature.planner.usecase.GenerateTravelPlanUseCase;
import com.travel.companion.feature.planner.usecase.SaveTravelPlanUseCase;
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
public final class PlannerViewModel_Factory implements Factory<PlannerViewModel> {
  private final Provider<SavedStateHandle> savedStateHandleProvider;

  private final Provider<GenerateTravelPlanUseCase> generateTravelPlanUseCaseProvider;

  private final Provider<SaveTravelPlanUseCase> saveTravelPlanUseCaseProvider;

  private final Provider<CityRepository> cityRepositoryProvider;

  private final Provider<VoiceAssistantService> voiceAssistantServiceProvider;

  public PlannerViewModel_Factory(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GenerateTravelPlanUseCase> generateTravelPlanUseCaseProvider,
      Provider<SaveTravelPlanUseCase> saveTravelPlanUseCaseProvider,
      Provider<CityRepository> cityRepositoryProvider,
      Provider<VoiceAssistantService> voiceAssistantServiceProvider) {
    this.savedStateHandleProvider = savedStateHandleProvider;
    this.generateTravelPlanUseCaseProvider = generateTravelPlanUseCaseProvider;
    this.saveTravelPlanUseCaseProvider = saveTravelPlanUseCaseProvider;
    this.cityRepositoryProvider = cityRepositoryProvider;
    this.voiceAssistantServiceProvider = voiceAssistantServiceProvider;
  }

  @Override
  public PlannerViewModel get() {
    return newInstance(savedStateHandleProvider.get(), generateTravelPlanUseCaseProvider.get(), saveTravelPlanUseCaseProvider.get(), cityRepositoryProvider.get(), voiceAssistantServiceProvider.get());
  }

  public static PlannerViewModel_Factory create(
      javax.inject.Provider<SavedStateHandle> savedStateHandleProvider,
      javax.inject.Provider<GenerateTravelPlanUseCase> generateTravelPlanUseCaseProvider,
      javax.inject.Provider<SaveTravelPlanUseCase> saveTravelPlanUseCaseProvider,
      javax.inject.Provider<CityRepository> cityRepositoryProvider,
      javax.inject.Provider<VoiceAssistantService> voiceAssistantServiceProvider) {
    return new PlannerViewModel_Factory(Providers.asDaggerProvider(savedStateHandleProvider), Providers.asDaggerProvider(generateTravelPlanUseCaseProvider), Providers.asDaggerProvider(saveTravelPlanUseCaseProvider), Providers.asDaggerProvider(cityRepositoryProvider), Providers.asDaggerProvider(voiceAssistantServiceProvider));
  }

  public static PlannerViewModel_Factory create(Provider<SavedStateHandle> savedStateHandleProvider,
      Provider<GenerateTravelPlanUseCase> generateTravelPlanUseCaseProvider,
      Provider<SaveTravelPlanUseCase> saveTravelPlanUseCaseProvider,
      Provider<CityRepository> cityRepositoryProvider,
      Provider<VoiceAssistantService> voiceAssistantServiceProvider) {
    return new PlannerViewModel_Factory(savedStateHandleProvider, generateTravelPlanUseCaseProvider, saveTravelPlanUseCaseProvider, cityRepositoryProvider, voiceAssistantServiceProvider);
  }

  public static PlannerViewModel newInstance(SavedStateHandle savedStateHandle,
      GenerateTravelPlanUseCase generateTravelPlanUseCase,
      SaveTravelPlanUseCase saveTravelPlanUseCase, CityRepository cityRepository,
      VoiceAssistantService voiceAssistantService) {
    return new PlannerViewModel(savedStateHandle, generateTravelPlanUseCase, saveTravelPlanUseCase, cityRepository, voiceAssistantService);
  }
}
