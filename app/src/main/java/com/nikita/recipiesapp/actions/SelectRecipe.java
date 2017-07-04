package com.nikita.recipiesapp.actions;


import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.redux.Action;

public final class SelectRecipe implements Action {
  public final Recipe recipe;

  public SelectRecipe(Recipe recipe) {
    this.recipe = recipe;
  }
}
