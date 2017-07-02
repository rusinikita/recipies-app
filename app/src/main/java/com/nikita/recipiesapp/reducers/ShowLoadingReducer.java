package com.nikita.recipiesapp.reducers;


import android.support.annotation.NonNull;

import com.nikita.recipiesapp.actions.ShowDataLoading;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Action;
import com.nikita.recipiesapp.common.redux.Reducer;

public final class ShowLoadingReducer implements Reducer<AppState> {
  @NonNull
  @Override
  public AppState reduce(@NonNull AppState previousState, @NonNull Action action) {
    if (action instanceof ShowDataLoading) {
      return previousState
        .withLoading(true)
        .withError("");
    }
    return previousState;
  }
}