package com.nikita.recipiesapp.views.steps;

import android.arch.lifecycle.LifecycleActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.StepDetailActivity;

/**
 * An activity representing a list of Steps. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepListActivity extends LifecycleActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_step_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    toolbar.setTitle(getTitle());

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
      .setAction("Action", null)
      .show());

    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.step_list);
  }
}
