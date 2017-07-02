package com.nikita.recipiesapp.views.recipes;


import com.airbnb.epoxy.AutoModel;
import com.airbnb.epoxy.Typed2EpoxyController;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.views.common.LoadingModel;

import java.util.List;

final class RecipesController extends Typed2EpoxyController<List<Recipe>, Boolean> {
  @AutoModel LoadingModel loadingModel;

  @Override
  protected void buildModels(List<Recipe> data, Boolean isLoading) {
    loadingModel.addIf(isLoading, this);

    for (Recipe recipe : data) {
      new RecipeViewModel_()
        .id(recipe.id)
        .imageUri(recipe.getImageUri())
        .name(recipe.name)
        .onClickListener_OnClickListener((model, parentView, clickedView, position) -> {})
        .addTo(this);
    }
  }
}
