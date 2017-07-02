package com.nikita.recipiesapp.reducers;


import android.support.annotation.NonNull;

import com.nikita.recipiesapp.actions.ShowError;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Action;
import com.nikita.recipiesapp.common.redux.Reducer;

public final class ShowErrorReducer  implements Reducer<AppState> {
  @NonNull
  @Override
  public AppState reduce(@NonNull AppState previousState, @NonNull Action action) {
    if (action instanceof ShowError) {
      return previousState
        .withLoading(false)
        .withError(((ShowError) action).message);
    }
    return previousState;
  }
}