package com.nikita.recipiesapp.views.common;


import android.view.View;

import com.airbnb.epoxy.EpoxyHolder;
import com.airbnb.epoxy.EpoxyModelWithHolder;
import com.nikita.recipiesapp.R;

public final class LoadingModel extends EpoxyModelWithHolder<LoadingModel.LoadingHolder> {

  @Override
  protected LoadingHolder createNewHolder() {
    return new LoadingHolder();
  }

  @Override
  protected int getDefaultLayout() {
    return R.layout.common_loading_layout;
  }

  static class LoadingHolder extends EpoxyHolder {
    @Override
    protected void bindView(View itemView) {}
  }
}
