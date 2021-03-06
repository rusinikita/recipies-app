package com.nikita.recipiesapp.views.steps;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.models.Step;
import com.nikita.recipiesapp.common.redux.Renderer;

public class StepDetailFragment extends Fragment implements Renderer<AppState> {
  private TextView description;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public StepDetailFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.step_detail, container, false);
    description = rootView.findViewById(R.id.step_detail);

    App.appStore.subscribe(this);

    return rootView;
  }

  @Override
  public void render(@NonNull AppState appState) {
    Step step = appState.selectedStep();
    description.setText(step.description);
  }
}
