package com.nikita.recipiesapp.views.steps;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.actions.MoveToNextStep;
import com.nikita.recipiesapp.actions.MoveToPreviousStep;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.redux.Renderer;

/**
 * An activity representing a single Step detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link StepListActivity}.
 */
public class StepDetailActivity extends AppCompatActivity implements Renderer<AppState> {

  private View prevFab;
  private View nextFab;

  public static void start(@NonNull Context context) {
    context.startActivity(new Intent(context, StepDetailActivity.class));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.step_detail_activity);
    Toolbar toolbar = findViewById(R.id.toolbar);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      //noinspection ConstantConditions
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    prevFab = findViewById(R.id.previous_fab);
    if (prevFab != null) {
      prevFab.setOnClickListener(view -> App.appStore.dispatch(new MoveToPreviousStep()));
    }
    nextFab = findViewById(R.id.next_fab);
    if (nextFab != null) {
      nextFab.setOnClickListener(view -> App.appStore.dispatch(new MoveToNextStep()));
    }

    boolean landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    boolean isTablet = getResources().getConfiguration().smallestScreenWidthDp > 600;
    if (landscape && !isTablet) {
      getWindow().getDecorView().setSystemUiVisibility(
          View.SYSTEM_UI_FLAG_LAYOUT_STABLE
              | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
              | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
              | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    App.appStore.subscribe(this);
  }

  @Override
  public void render(@NonNull AppState state) {
    setTitle(state.selectedStep().shortDescription);
    if (prevFab != null) {
      prevFab.setVisibility(state.previousStep() != null ? View.VISIBLE : View.GONE);
    }
    if (nextFab != null) {
      nextFab.setVisibility(state.nextStep() != null ? View.VISIBLE : View.GONE);
    }
  }
}
