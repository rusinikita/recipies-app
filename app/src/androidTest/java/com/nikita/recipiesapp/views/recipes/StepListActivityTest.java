package com.nikita.recipiesapp.views.recipes;

import android.support.test.espresso.intent.Intents;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.RecyclerViewMatcher;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.middlewares.DataLoadingMiddleware;
import com.nikita.recipiesapp.views.steps.StepDetailActivity;
import com.nikita.recipiesapp.views.steps.StepListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nikita.recipiesapp.EspressoTestUtils.callRender;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class StepListActivityTest {

  @Rule
  public ActivityTestRule<StepListActivity> testRule = new ActivityTestRule<>(StepListActivity.class);

  @Test
  public void shouldOpenStepDetailScreen() throws Exception {
    List<Recipe> dummyRecipies = DataLoadingMiddleware.getDummyRecipies();
    callRender(testRule, AppState.initial().withRecipes(dummyRecipies));

    Intents.init();

    onView(withId(R.id.recycler_view))
        .perform(actionOnItemAtPosition(0, click()));

    intended(hasComponent(StepDetailActivity.class.getName()));

    Intents.release();
  }

  @Test
  public void shouldShowItems() throws Exception {
    List<Recipe> dummyRecipies = DataLoadingMiddleware.getDummyRecipies();
    Recipe selectedRecipe = dummyRecipies.get(0);
    callRender(testRule, AppState.initial()
        .withRecipes(dummyRecipies)
        .withSelectedRecipe(selectedRecipe));

    // 9 ingredients + 7 steps + 2 headers
    onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPositionWithScroll(1))
        .check(matches(hasDescendant(withText(selectedRecipe.ingredients.get(0).ingredient))));

    onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPositionWithScroll(selectedRecipe.ingredients.size() + 2))
        .check(matches(hasDescendant(withText(selectedRecipe.steps.get(0).shortDescription))));
  }
}
