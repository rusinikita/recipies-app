package com.nikita.recipiesapp.common;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.models.Step;

import java.util.Collections;
import java.util.List;

public final class AppState {
  public final boolean isLoading;
  @Nullable
  public final String notification;
  @Nullable
  public final String error;

  @NonNull
  public final List<Recipe> recipes;

  @NonNull
  private final String selectedRecipeId;
  @NonNull
  private final String selectedStepId;

  public AppState(boolean isLoading,
                  String notification,
                  String error,
                  List<Recipe> recipes,
                  String selectedRecipeId,
                  String selectedStepId) {
    this.isLoading = isLoading;
    this.notification = notification;
    this.error = error;
    this.recipes = recipes;
    this.selectedRecipeId = selectedRecipeId;
    this.selectedStepId = selectedStepId;
  }

  public static AppState create(boolean isLoading,
                                String notification,
                                String error,
                                List<Recipe> recipes,
                                String selectedRecipeId,
                                String selectedStepId) {
    return new AppState(isLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public static AppState initial() {
    return create(true, null, null, Collections.emptyList(), "", "");
  }

  public AppState withLoading(boolean isLoading) {
    return create(isLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public AppState withNotification(@NonNull String notification) {
    return create(isLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public AppState withError(@NonNull String error) {
    return create(isLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public AppState withRecipes(@NonNull List<Recipe> recipes) {
    return create(isLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public AppState withSelectedRecipe(@NonNull Recipe recipe) {
    return create(isLoading, notification, error, recipes, recipe.id, selectedStepId);
  }

  public AppState withSelectedStep(@NonNull Step step) {
    return create(isLoading, notification, error, recipes, selectedRecipeId, step.id);
  }
}
