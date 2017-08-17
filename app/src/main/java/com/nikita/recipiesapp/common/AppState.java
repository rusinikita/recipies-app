package com.nikita.recipiesapp.common;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.models.Step;

import java.util.Collections;
import java.util.List;

public final class AppState {
  public final boolean isDataLoading;
  @Nullable
  public final String notification;
  @Nullable
  public final String error;
  @NonNull
  public final List<Recipe> recipes;
  @Nullable
  private final Recipe selectedRecipe;
  @Nullable
  private final Step selectedStep;

  public AppState(boolean isDataLoading,
                  @Nullable String notification,
                  @Nullable String error,
                  @NonNull List<Recipe> recipes,
                  @Nullable Recipe selectedRecipe,
                  @Nullable Step selectedStep) {
    this.isDataLoading = isDataLoading;
    this.notification = notification;
    this.error = error;
    this.recipes = recipes;
    this.selectedRecipe = selectedRecipe;
    this.selectedStep = selectedStep;
  }

  private static AppState create(boolean isDataLoading,
                                 @Nullable String notification,
                                 @Nullable String error,
                                 @NonNull List<Recipe> recipes,
                                 @Nullable Recipe selectedRecipe,
                                 @Nullable Step selectedStep) {
    return new AppState(isDataLoading, notification, error, recipes, selectedRecipe, selectedStep);
  }

  @NonNull
  public final Recipe selectedRecipe() {
    if (selectedRecipe != null) {
      return selectedRecipe;
    }
    throw new IllegalStateException("no selected recipe");
  }

  @NonNull
  public final Step selectedStep() {
    if (selectedStep != null) {
      return selectedStep;
    }
    throw new IllegalStateException("no selected step");
  }

  @Nullable
  public final Step nextStep() {
    final List<Step> steps = selectedRecipe().steps;
    final int currentStepIndex = currentStepIndex();
    if (steps.size() - 1 > currentStepIndex) {
      return steps.get(currentStepIndex + 1);
    } else {
      return null;
    }
  }

  @Nullable
  public final Step previousStep() {
    final List<Step> steps = selectedRecipe().steps;
    final int currentStepIndex = currentStepIndex();
    if (currentStepIndex() > 0) {
      return steps.get(currentStepIndex - 1);
    } else {
      return null;
    }
  }

  private int currentStepIndex() {
    Recipe recipe = selectedRecipe();
    return recipe.steps.indexOf(selectedStep());
  }

  @NonNull
  public static AppState initial() {
    return create(false, null, null, Collections.emptyList(), null, null);
  }

  @NonNull
  public AppState withLoading(boolean isDataLoading) {
    return create(isDataLoading, notification, error, recipes, selectedRecipe, selectedStep);
  }

  @NonNull
  public AppState withNotification(@NonNull String notification) {
    return create(isDataLoading, notification, error, recipes, selectedRecipe, selectedStep);
  }

  @NonNull
  public AppState withError(@NonNull String error) {
    return create(isDataLoading, notification, error, recipes, selectedRecipe, selectedStep);
  }

  @NonNull
  public AppState withRecipes(@NonNull List<Recipe> recipes) {
    return create(isDataLoading, notification, error, recipes, selectedRecipe, selectedStep);
  }

  @NonNull
  public AppState withSelectedRecipe(@NonNull Recipe recipe) {
    return create(isDataLoading, notification, error, recipes, recipe, recipe.steps.get(0));
  }

  @NonNull
  public AppState withSelectedStep(@NonNull Step step) {
    return create(isDataLoading, notification, error, recipes, selectedRecipe, step);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder().append("isDataLoading:").append(isDataLoading).append("\n")
        .append("notification:").append(notification).append("\n")
        .append("error:").append(error).append("\n")
        .append("recipes : [\n");

    for (Recipe recipe : recipes) {
      sb.append("    ").append(recipe.toString()).append("\n");
    }

    sb.append("]\n")
        .append("selectedRecipe:").append(selectedRecipe).append("\n")
        .append("selectedStep:").append(selectedStep).append("\n");
    return sb.toString();
  }
}
