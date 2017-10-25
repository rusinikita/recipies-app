package com.nikita.recipiesapp.views.common;


import android.support.annotation.StringRes;
import android.view.View;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.nikita.recipiesapp.R;

public class ButtonModel extends EpoxyModelWithHolder<TextViewHolder> {
  @EpoxyAttribute
  @StringRes
  int text;
  @EpoxyAttribute
  View.OnClickListener clickListener;

  @Override
  protected TextViewHolder createNewHolder() {
    return new TextViewHolder();
  }

  @Override
  protected int getDefaultLayout() {
    return R.layout.common_flat_button_item;
  }

  @Override
  public void bind(TextViewHolder holder) {
    super.bind(holder);
    holder.text.setText(text);
    holder.text.setOnClickListener(clickListener);
  }
}
