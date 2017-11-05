package com.nikita.recipiesapp.views.recipes;

import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.RecyclerViewMatcher;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.middlewares.DataLoadingMiddleware;
import com.nikita.recipiesapp.views.steps.StepListActivity;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nikita.recipiesapp.EspressoTestUtils.callRender;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class RecipesActivityTest {

  @BeforeClass
  public static void initialize() {
    App.appStore.setUiTestMode(true);
  }

  @Rule
  public ActivityTestRule<RecipesActivity> testRule = new ActivityTestRule<>(
    RecipesActivity.class);

  @Test
  public void shouldHideLoading() {
    callRender(testRule, AppState.initial().withLoading(false));

    onView(withId(R.id.loading_indicator))
      .check(doesNotExist());
  }

  @Test
  public void shouldShowLoading() {
    callRender(testRule, AppState.initial().withLoading(true));

    onView(withId(R.id.loading_indicator))
      .check(matches(isDisplayed()));
  }

  @Test
  public void shouldShowError() {
    String errorText = "text text";
    callRender(testRule, AppState.initial().withError(errorText));

    onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(errorText)))
        //isDisplayed() not working
        // Snackbar VISIBLE but its rect height 0 for user
        .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    // Snackbar showing some time, need to clear to fix conflicts with other tests
    testRule.getActivity().finish();
  }

  @Test
  public void shouldShowNotification() {
    String errorText = "text text";
    callRender(testRule, AppState.initial().withNotification(errorText));

    onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(errorText)))
        //isDisplayed() not working
        // Snackbar VISIBLE but its rect height 0 for user
        .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    // Snackbar showing some time, need to clear to fix conflicts with other tests
    testRule.getActivity().finish();
  }

  @Test
  public void shouldShowFirstRecipe() throws Exception {
    List<Recipe> dummyRecipes = DataLoadingMiddleware.getDummyRecipes();
    callRender(testRule, AppState.initial().withRecipes(dummyRecipes));

    onView(withText(dummyRecipes.get(0).name))
            .check(matches(isDisplayed()));
  }

  @Test
  public void shouldShowLastRecipe() throws Exception {
    List<Recipe> dummyRecipes = DataLoadingMiddleware.getDummyRecipes();
    callRender(testRule, AppState.initial().withRecipes(dummyRecipes));
    int lastIndex = 3;

    onView(RecyclerViewMatcher.withRecyclerView(R.id.recycler_view).atPositionWithScroll(lastIndex))
        .check(matches(hasDescendant(withText(dummyRecipes.get(lastIndex).name))));
  }

  @Test
  public void shouldOpenRecipeScreenOnItemClick() throws Exception {
    List<Recipe> dummyRecipes = DataLoadingMiddleware.getDummyRecipes();
    callRender(testRule, AppState.initial().withRecipes(dummyRecipes));

    Intents.init();

    onView(withId(R.id.recycler_view))
        .perform(actionOnItemAtPosition(0, click()));

    intended(hasComponent(StepListActivity.class.getName()));

    Intents.release();
  }
}