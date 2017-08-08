package com.nikita.recipiesapp.reducers;


import android.support.annotation.NonNull;

import com.nikita.recipiesapp.actions.MoveToNextStep;
import com.nikita.recipiesapp.actions.MoveToPreviousStep;
import com.nikita.recipiesapp.actions.SelectStep;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Action;
import com.nikita.recipiesapp.common.redux.Reducer;

public final class SelectStepReducer implements Reducer<AppState> {
  @NonNull
  @Override
  public AppState reduce(@NonNull AppState previousState, @NonNull Action action) {
    if (action instanceof SelectStep) {
      return previousState
        .withSelectedStep(((SelectStep) action).step);
    } else if (action instanceof MoveToNextStep) {
      return previousState.withSelectedStep(previousState.nextStep());
    } else if (action instanceof MoveToPreviousStep) {
      return previousState.withSelectedStep(previousState.previousStep());
    }
    return previousState;
  }
}
