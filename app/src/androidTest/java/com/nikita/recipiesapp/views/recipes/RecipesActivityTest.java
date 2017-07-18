package com.nikita.recipiesapp.views.recipes;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.common.AppState;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.nikita.recipiesapp.EspressoTestUtils.callRender;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecipesActivityTest {

  @Rule
  public ActivityTestRule<RecipesActivity> mActivityRule = new ActivityTestRule<>(
    RecipesActivity.class);

  @Test
  public void shouldHideLoading() {
    callRender(mActivityRule, AppState.initial().withLoading(false));

    onView(withId(R.id.loading_indicator))
      .check(doesNotExist());
  }

  @Test
  public void shouldShowLoading() {
    callRender(mActivityRule, AppState.initial().withLoading(true));

    onView(withId(R.id.loading_indicator))
      .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
  }
}