package com.nikita.recipiesapp.views.recipes;


import com.airbnb.epoxy.Typed2EpoxyController;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.redux.Consumer;
import com.nikita.recipiesapp.views.common.LoadingModel;

import java.util.List;

final class RecipesController extends Typed2EpoxyController<List<Recipe>, Boolean> {
  private final Consumer<Recipe> recipeClick;

  RecipesController(Consumer<Recipe> recipeClick) {
    this.recipeClick = recipeClick;
  }

  @Override
  protected void buildModels(List<Recipe> data, Boolean isLoading) {
    new LoadingModel()
      .id(1)
      .addIf(isLoading, this);

    for (Recipe recipe : data) {
      new RecipeViewModel_()
        .id(recipe.id)
        .recipe(recipe)
        .onClickListener_OnClickListener((model, parentView, clickedView, position) -> recipeClick.consume(model.recipe()))
        .addTo(this);
    }
  }
}
