package com.nikita.recipiesapp;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nikita.recipiesapp.actions.LoadData;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Middleware;
import com.nikita.recipiesapp.common.redux.Reducer;
import com.nikita.recipiesapp.common.redux.Store;
import com.nikita.recipiesapp.middlewares.DataLoadingMiddleware;
import com.nikita.recipiesapp.middlewares.LoggingMiddleware;
import com.nikita.recipiesapp.reducers.AddLoadedRecipesReducer;
import com.nikita.recipiesapp.reducers.ShowErrorReducer;
import com.nikita.recipiesapp.reducers.ShowLoadingReducer;
import com.nikita.recipiesapp.reducers.ShowNotificationReducer;


public final class App extends Application {

  public static final Store<AppState> appStore;

  static {
    Reducer<AppState> appReducer = new Store.ReducerCombiner<>(new Reducer[]{
      new ShowLoadingReducer(),
      new ShowErrorReducer(),
      new ShowNotificationReducer(),
      new AddLoadedRecipesReducer()
    });
    Middleware<AppState>[] middlewares = new Middleware[]{
      new DataLoadingMiddleware(),
      new LoggingMiddleware()
    };
    appStore = new Store<>(appReducer, middlewares, AppState.initial());
    appStore.dispatch(new LoadData());
  }

  @Override
  public void onCreate() {
    super.onCreate();
    Fresco.initialize(this);
  }
}
