package com.nikita.recipiesapp.actions;


import com.nikita.recipiesapp.common.models.Step;
import com.nikita.recipiesapp.common.redux.Action;

public final class SelectStep implements Action {
  public final Step step;

  public SelectStep(Step step) {
    this.step = step;
  }
}
