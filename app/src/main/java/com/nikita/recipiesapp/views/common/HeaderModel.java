package com.nikita.recipiesapp.views.common;


import android.support.annotation.StringRes;

import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.nikita.recipiesapp.R;

public class HeaderModel extends EpoxyModelWithHolder<TextViewHolder> {
  @EpoxyAttribute
  @StringRes
  int header;

  @Override
  protected TextViewHolder createNewHolder() {
    return new TextViewHolder();
  }

  @Override
  protected int getDefaultLayout() {
    return R.layout.common_header_item;
  }

  @Override
  public void bind(TextViewHolder holder) {
    super.bind(holder);
    holder.text.setText(header);
  }
}
