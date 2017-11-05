package com.nikita.recipiesapp.views.recipes;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.actions.SelectRecipe;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.redux.Renderer;
import com.nikita.recipiesapp.common.utils.GridLayoutManagerDividerDecoration;
import com.nikita.recipiesapp.views.steps.StepListActivity;

public final class RecipesActivity extends AppCompatActivity implements Renderer<AppState> {
  private AppBarLayout appBar;
  private final RecipesController recipesController = new RecipesController(this::onRecipeSelection);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.recipes_activity);

    appBar = findViewById(R.id.app_bar);
    setSupportActionBar(findViewById(R.id.toolbar));

    RecyclerView recyclerView = findViewById(R.id.recycler_view);
    int spanCount = getResources().getInteger(R.integer.recipes_column_count);
    Drawable divider = getResources().getDrawable(R.drawable.item_offset_divider);
    GridLayoutManagerDividerDecoration offsetItemDecoration = new GridLayoutManagerDividerDecoration(divider, divider, spanCount);
    recyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
    recyclerView.addItemDecoration(offsetItemDecoration);
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

  private void onRecipeSelection(Recipe recipe) {
    App.appStore.dispatch(new SelectRecipe(recipe));
    startActivity(new Intent(this, StepListActivity.class));
  }
}
