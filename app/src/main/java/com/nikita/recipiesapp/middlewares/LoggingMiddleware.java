package com.nikita.recipiesapp.middlewares;


import android.util.Log;

import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Action;
import com.nikita.recipiesapp.common.redux.Middleware;

public final class LoggingMiddleware extends Middleware<AppState> {
  @Override
  public void consume(Action action) {
    Log.d("Action", action.toString());
    wrappedConsumer.consume(action);
    Log.d("State", store.getState().toString());
  }
}
