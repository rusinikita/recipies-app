package com.nikita.recipiesapp.views.steps;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.RecyclerViewMatcher;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.middlewares.DataLoadingMiddleware;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.nikita.recipiesapp.EspressoTestUtils.callRender;

@RunWith(AndroidJUnit4.class)
@LargeTest
public final class StepListActivityTest {

  @BeforeClass
  public static void initialize() {
    App.appStore.setUiTestMode(true);
  }

  @Rule
  public ActivityTestRule<StepListActivity> testRule = new ActivityTestRule<>(StepListActivity.class);

  @Test
  public void shouldShowItems() throws Exception {
    List<Recipe> dummyRecipes = DataLoadingMiddleware.getDummyRecipes();
    Recipe selectedRecipe = dummyRecipes.get(0);
    callRender(testRule, AppState.initial()
        .withRecipes(dummyRecipes)
        .withSelectedRecipe(selectedRecipe));

    // 9 ingredients + 7 steps + 2 headers
    onView(RecyclerViewMatcher.withRecyclerView(R.id.step_list).atPositionWithScroll(1))
        .check(matches(withText(selectedRecipe.ingredients.get(0).description)));

    onView(RecyclerViewMatcher.withRecyclerView(R.id.step_list).atPositionWithScroll(selectedRecipe.ingredients.size() + 3))
        .check(matches(withText(selectedRecipe.steps.get(0).shortDescription)));
  }
}
