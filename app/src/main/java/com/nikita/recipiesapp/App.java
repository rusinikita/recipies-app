package com.nikita.recipiesapp;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.nikita.recipiesapp.actions.LoadData;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Middleware;
import com.nikita.recipiesapp.common.redux.Reducer;
import com.nikita.recipiesapp.common.redux.Store;
import com.nikita.recipiesapp.middlewares.AddRecipeToWidgetMiddleware;
import com.nikita.recipiesapp.middlewares.DataLoadingMiddleware;
import com.nikita.recipiesapp.middlewares.LoggingMiddleware;
import com.nikita.recipiesapp.reducers.AddLoadedRecipesReducer;
import com.nikita.recipiesapp.reducers.SelectRecipeReducer;
import com.nikita.recipiesapp.reducers.SelectStepReducer;
import com.nikita.recipiesapp.reducers.ShowErrorReducer;
import com.nikita.recipiesapp.reducers.ShowLoadingReducer;
import com.nikita.recipiesapp.reducers.ShowNotificationReducer;


public final class App extends Application {

  public static final String PREF_NAME = "prefs";
  public static final String PREF_KEY_WIDGET = "widget_text";
  public static final Store<AppState> appStore;
  public static Context appContext;

  static {
    Reducer<AppState> appReducer = new Store.ReducerCombiner<>(new Reducer[]{
        new ShowLoadingReducer(),
        new ShowErrorReducer(),
        new ShowNotificationReducer(),
        new AddLoadedRecipesReducer(),
        new SelectRecipeReducer(),
        new SelectStepReducer()
    });
    Middleware<AppState>[] middlewares = new Middleware[]{
        new DataLoadingMiddleware(),
        new LoggingMiddleware(),
        new AddRecipeToWidgetMiddleware()
    };
    appStore = new Store<>(appReducer, middlewares, AppState.initial());
    if (!BuildConfig.IS_UI_TESTING) {
      appStore.dispatch(new LoadData());
    }
  }

  @Override
  public void onCreate() {
    super.onCreate();
    appContext = this;
    Fresco.initialize(this);
  }
}
