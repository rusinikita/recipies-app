package com.nikita.recipiesapp.actions;

import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.redux.Action;

import java.util.List;


public final class AddLoadedRecipes implements Action {
  public final List<Recipe> recipes;

  public AddLoadedRecipes(List<Recipe> recipes) {
    this.recipes = recipes;
  }
}
