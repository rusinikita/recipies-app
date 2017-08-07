package com.nikita.recipiesapp.views.steps;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.actions.SelectStep;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.models.Step;
import com.nikita.recipiesapp.common.redux.Renderer;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends LifecycleActivity implements Renderer<AppState> {
  private final StepListController stepListController = new StepListController(this::changeStep);
  private boolean shouldOpenDetailsActivity = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.step_list_activity);

    Toolbar toolbar = findViewById(R.id.toolbar);
    toolbar.setTitle(getTitle());

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null)
        .show());

    RecyclerView recyclerView = findViewById(R.id.step_list);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setAdapter(stepListController.getAdapter());

    shouldOpenDetailsActivity = findViewById(R.id.step_detail_container) == null;

    App.appStore.subscribe(this);
  }

  @Override
  public void render(@NonNull AppState appState) {
    Recipe recipe = appState.selectedRecipe();
    stepListController.setData(recipe.ingredients, recipe.steps, appState.selectedStepId);
  }

  private void changeStep(Step step) {

    App.appStore.dispatch(new SelectStep(step));

    if (shouldOpenDetailsActivity) {
      StepDetailActivity.start(this);
    }
  }
}
