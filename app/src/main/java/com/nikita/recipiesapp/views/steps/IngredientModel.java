package com.nikita.recipiesapp.views.steps;


import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.common.models.Ingredient;

class IngredientModel extends EpoxyModelWithHolder<IngredientModel.IngredientHolder> {
  @EpoxyAttribute
  Ingredient ingredient;

  @Override
  protected int getDefaultLayout() {
    return R.layout.step_list_ingredient_item;
  }

  @Override
  protected IngredientHolder createNewHolder() {
    return new IngredientHolder();
  }

  @Override
  public void bind(IngredientHolder holder) {
    super.bind(holder);
    holder.text.setText(ingredient.ingredient + ", " + ingredient.quantity + " " + ingredient.measure);
  }

  static final class IngredientHolder extends EpoxyHolder {
    TextView text;

    @Override
    protected void bindView(View itemView) {
      text = (TextView) itemView;
    }
  }
}
