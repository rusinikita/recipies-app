package com.nikita.recipiesapp.reducers;


import android.support.annotation.NonNull;

import com.nikita.recipiesapp.actions.ShowNotification;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Action;
import com.nikita.recipiesapp.common.redux.Reducer;

public final class ShowNotificationReducer implements Reducer<AppState> {
  @NonNull
  @Override
  public AppState reduce(@NonNull AppState previousState, @NonNull Action action) {
    if (action instanceof ShowNotification) {
      return previousState
        .withLoading(false)
        .withError(((ShowNotification) action).message);
    }
    return previousState;
  }
}