package com.nikita.recipiesapp.reducers;


import android.support.annotation.NonNull;

import com.nikita.recipiesapp.actions.AddLoadedRecipes;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Action;
import com.nikita.recipiesapp.common.redux.Reducer;

public final class AddLoadedRecipesReducer implements Reducer<AppState> {
  @NonNull
  @Override
  public AppState reduce(@NonNull AppState previousState, @NonNull Action action) {
    if (action instanceof AddLoadedRecipes) {
      return previousState
        .withLoading(false)
        .withRecipes(((AddLoadedRecipes) action).recipes)
        .withError("");
    }
    return previousState;
  }
}
