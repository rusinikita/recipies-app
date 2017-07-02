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

  private final int selectedRecipeId;
  private final int selectedStepId;

  public AppState(boolean isDataLoading,
                  String notification,
                  String error,
                  List<Recipe> recipes,
                  int selectedRecipeId,
                  int selectedStepId) {
    this.isDataLoading = isDataLoading;
    this.notification = notification;
    this.error = error;
    this.recipes = recipes;
    this.selectedRecipeId = selectedRecipeId;
    this.selectedStepId = selectedStepId;
  }

  public static AppState create(boolean isDataLoading,
                                String notification,
                                String error,
                                List<Recipe> recipes,
                                int selectedRecipeId,
                                int selectedStepId) {
    return new AppState(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public static AppState initial() {
    return create(false, null, null, Collections.emptyList(), -1, -1);
  }

  public AppState withLoading(boolean isDataLoading) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public AppState withNotification(@NonNull String notification) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public AppState withError(@NonNull String error) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public AppState withRecipes(@NonNull List<Recipe> recipes) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, selectedStepId);
  }

  public AppState withSelectedRecipe(@NonNull Recipe recipe) {
    return create(isDataLoading, notification, error, recipes, recipe.id, selectedStepId);
  }

  public AppState withSelectedStep(@NonNull Step step) {
    return create(isDataLoading, notification, error, recipes, selectedRecipeId, step.id);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder()
      .append("isDataLoading:" + isDataLoading + "\n")
      .append("notification:" + notification + "\n")
      .append("error:" + error + "\n")
      .append("recipes : [\n");

    for (Recipe recipe : recipes) {
      sb.append("    " + recipe.toString() + "\n");
    }

    sb.append("]\n")
      .append("selectedRecipeId:" + selectedRecipeId + "\n")
      .append("selectedStepId:" + selectedStepId + "\n");
    return sb.toString();
  }
}
