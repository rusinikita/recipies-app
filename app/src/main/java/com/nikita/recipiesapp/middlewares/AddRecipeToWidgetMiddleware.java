package com.nikita.recipiesapp.middlewares;

import android.content.Context;
import android.content.SharedPreferences;

import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.actions.AddSelectedRecipeToWidget;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.models.Ingredient;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.redux.Action;
import com.nikita.recipiesapp.common.redux.Middleware;

public final class AddRecipeToWidgetMiddleware extends Middleware<AppState> {
  @Override
  public void consume(Action action) {
    if (action instanceof AddSelectedRecipeToWidget) {
      Recipe recipe = store.getState().selectedRecipe();
      StringBuilder sb = new StringBuilder();

      sb.append("<b>" + recipe.name + "</b>\n");
      sb.append("<ul>\n");
      for (Ingredient ingredient : recipe.ingredients) {
        sb.append("<li>" + ingredient.ingredient + ", " + ingredient.quantity + " " + ingredient.measure + "</li>\n");
      }
      sb.append("</ul>");

      SharedPreferences sharedPreferences = App.appContext.getSharedPreferences(App.PREF_NAME, Context.MODE_PRIVATE);
      sharedPreferences.edit().putString(App.PREF_KEY_WIDGET, sb.toString()).apply();
    }

    wrappedConsumer.consume(action);
  }
}
