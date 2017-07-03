package com.nikita.recipiesapp.views.recipes;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Renderer;

public final class RecipesActivity extends LifecycleActivity implements Renderer<AppState> {
  private AppBarLayout appBar;
  private final RecipesController recipesController = new RecipesController();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.recipes_activity);

    appBar = (AppBarLayout) findViewById(R.id.app_bar);

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
    recyclerView.setAdapter(recipesController.getAdapter());

    App.appStore.subscribe(this);
  }

  @Override
  public void render(@NonNull AppState appState) {
    recipesController.setData(appState.recipes, appState.isDataLoading);
    String error = appState.error;
    if (error != null && !error.isEmpty()) {
      Snackbar.make(appBar, error, Snackbar.LENGTH_INDEFINITE).show();
    }
    String notification = appState.notification;
    if (notification != null && !notification.isEmpty()) {
      Snackbar.make(appBar, notification, Snackbar.LENGTH_INDEFINITE).show();
    }
  }
}
