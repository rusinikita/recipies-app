package com.nikita.recipiesapp;


import android.app.Activity;
import android.content.Intent;
import android.support.test.rule.ActivityTestRule;

import com.nikita.recipiesapp.common.redux.Renderer;

public final class EspressoTestUtils {
  private EspressoTestUtils() {}

  public static <S, A extends Activity & Renderer<S>> void callRender(ActivityTestRule<A> rule, S state) {
    A activity = rule.launchActivity(new Intent());
    activity.runOnUiThread(() -> activity.render(state));
  }
}