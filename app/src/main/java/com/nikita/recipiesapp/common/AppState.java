package com.nikita.recipiesapp.common;


import com.nikita.recipiesapp.common.models.Recipe;

import java.util.List;

public final class AppState {
  public final List<Recipe> recipes;

  private final String selectedRecipeId;
  private final String selectedStepId;

  public AppState(List<Recipe> recipes,
                  String selectedRecipeId,
                  String selectedStepId) {
    this.recipes = recipes;
    this.selectedRecipeId = selectedRecipeId;
    this.selectedStepId = selectedStepId;
  }
}
