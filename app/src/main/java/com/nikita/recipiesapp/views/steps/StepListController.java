package com.nikita.recipiesapp.views.steps;


import com.airbnb.epoxy.Typed3EpoxyController;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.common.models.Ingredient;
import com.nikita.recipiesapp.common.models.Step;
import com.nikita.recipiesapp.common.redux.Consumer;
import com.nikita.recipiesapp.views.common.HeaderModel_;

import java.util.List;

public final class StepListController extends Typed3EpoxyController<List<Ingredient>, List<Step>, Integer> {
  private final Consumer<Step> stepClick;

  public StepListController(Consumer<Step> stepClick) {
    this.stepClick = stepClick;
  }

  @Override
  protected void buildModels(List<Ingredient> ingredients, List<Step> steps, Integer selectedStep) {
    new HeaderModel_()
      .id(R.string.step_list_ingredients_header)
      .header(R.string.step_list_ingredients_header)
      .addTo(this);

    for (Ingredient ingredient : ingredients) {
      new IngredientModel_()
        .id(ingredient.hashCode())
        .ingredient(ingredient)
        .addTo(this);
    }

    new HeaderModel_()
      .id(R.string.step_list_steps_header)
      .header(R.string.step_list_steps_header)
      .addTo(this);

    for (Step step : steps) {
      new StepModel_()
        .id(step.id)
        .step(step)
        .clickListener((model, parentView, clickedView, position) -> stepClick.consume(model.step()))
        .addTo(this);
    }
  }
}
