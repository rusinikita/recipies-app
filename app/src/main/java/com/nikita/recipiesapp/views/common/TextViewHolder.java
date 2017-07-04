package com.nikita.recipiesapp.views.common;

import android.view.View;
import android.widget.TextView;

import com.airbnb.epoxy.EpoxyHolder;


public final class TextViewHolder extends EpoxyHolder {
  public TextView text;

  @Override
  protected void bindView(View itemView) {
    text = (TextView) itemView;
  }
}
