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

  public final int selectedRecipeId;
  public final int selectedStepId;

  public AppState(boolean isDataLoading,
                  @Nullable String notification,
                  @Nullable String error,
                  @NonNull List<Recipe> recipes,
                  int selectedRecipeId,
                  int selectedStepId) {
    this.isDataLoading = isDataLoading;
    this.notification = notification;
    this.error = error;
    this.recipes = recipes;
    this.selectedRecipeId = selectedRecipeId;
    this.selectedStepId = selectedStepId;
  }

  private static AppState create(boolean isDataLoading,
                                 String notification,
                                 String error,
                                 List<Recipe> recipes,
                                 int selectedRecipeId,
                                 int selectedStepId) {
    return new AppState(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  @NonNull
  public final Recipe selectedRecipe() {
    for (Recipe recipe : recipes) {
      if (recipe.id == selectedRecipeId) {
        return recipe;
      }
    }
    throw new IllegalStateException("no selected recipe");
  }

  @NonNull
  public final Step selectedStep() {
    for (Step step : selectedRecipe().steps) {
      if (step.id == selectedStepId) {
        return step;
      }
    }
    throw new IllegalStateException("no selected step");
  }

  @Nullable
  public final Step nextStep() {
    final List<Step> steps = selectedRecipe().steps;
    final int currentStepIndex = currentStepIndex();
    if (steps.size() > currentStepIndex) {
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
    return create(false, null, null, Collections.emptyList(), -1, -1);
  }

  @NonNull
  public AppState withLoading(boolean isDataLoading) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  @NonNull
  public AppState withNotification(@NonNull String notification) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  @NonNull
  public AppState withError(@NonNull String error) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  @NonNull
  public AppState withRecipes(@NonNull List<Recipe> recipes) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  @NonNull
  public AppState withSelectedRecipe(@NonNull Recipe recipe) {
    return create(isDataLoading, notification, error, recipes, recipe.id, selectedStepId);
  }

  @NonNull
  public AppState withSelectedStep(@NonNull Step step) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, step.id);
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
        .append("selectedRecipeId:").append(selectedRecipeId).append("\n")
        .append("selectedStepId:").append(selectedStepId).append("\n");
    return sb.toString();
  }
}
