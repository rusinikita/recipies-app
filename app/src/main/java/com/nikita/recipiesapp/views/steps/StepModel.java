package com.nikita.recipiesapp.views.steps;


import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.common.models.Step;
import com.nikita.recipiesapp.views.common.TextViewHolder;

class StepModel extends EpoxyModelWithHolder<TextViewHolder> {
  @EpoxyAttribute
  Step step;

  @Override
  protected TextViewHolder createNewHolder() {
    return new TextViewHolder();
  }

  @Override
  protected int getDefaultLayout() {
    return R.layout.step_list_step_item;
  }

  @Override
  public void bind(TextViewHolder holder) {
    super.bind(holder);
    holder.text.setText(step.shortDescription);
  }
}
