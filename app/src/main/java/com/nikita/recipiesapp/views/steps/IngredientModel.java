package com.nikita.recipiesapp.views.steps;


import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.common.models.Ingredient;
import com.nikita.recipiesapp.views.common.TextViewHolder;

class IngredientModel extends EpoxyModelWithHolder<TextViewHolder> {
  @EpoxyAttribute
  Ingredient ingredient;

  @Override
  protected int getDefaultLayout() {
    return R.layout.step_list_ingredient_item;
  }

  @Override
  protected TextViewHolder createNewHolder() {
    return new TextViewHolder();
  }

  @Override
  public void bind(TextViewHolder holder) {
    super.bind(holder);
    holder.text.setText(ingredient.description);
  }
}
