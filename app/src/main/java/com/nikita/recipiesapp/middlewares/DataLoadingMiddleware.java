package com.nikita.recipiesapp.middlewares;


import android.accounts.NetworkErrorException;
import android.net.Uri;

import com.nikita.recipiesapp.actions.AddLoadedRecipes;
import com.nikita.recipiesapp.actions.LoadData;
import com.nikita.recipiesapp.actions.ShowDataLoading;
import com.nikita.recipiesapp.actions.ShowError;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.models.Ingredient;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.models.Step;
import com.nikita.recipiesapp.common.redux.Action;
import com.nikita.recipiesapp.common.redux.Middleware;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public final class DataLoadingMiddleware extends Middleware<AppState> {

  @Override
  public void consume(Action action) {
    wrappedConsumer.consume(action);
    if (action instanceof LoadData) {
      store.dispatch(new ShowDataLoading());
      try {
        store.dispatch(new AddLoadedRecipes(getRecipes()));
      } catch (Exception e) {
        store.dispatch(new ShowError(e.getMessage()));
      }
    }
  }

  public List<Recipe> getRecipes() throws Exception {
    Uri uri = Uri.parse("http://go.udacity.com/android-baking-app-json");

    URL url = new URL(uri.toString());
    InputStream in = url.openConnection().getInputStream();
    Scanner scanner = new Scanner(in);
    scanner.useDelimiter("\\A");

    if (scanner.hasNext()) {
      String jsonData = scanner.next();

      return parse(jsonData);
    } else {
      throw new NetworkErrorException("No data in response");
    }
  }

  public List<Recipe> parse(String response) throws JSONException {
    LinkedList<Recipe> recipes = new LinkedList<>();

    JSONArray ja = new JSONArray(response);
    for (int i = 0; i < ja.length(); i++) {
      JSONObject jo = ja.getJSONObject(i);

      int id = jo.getInt("id");
      String name = jo.getString("name");
      int servings = jo.getInt("servings");
      String image = jo.getString("image");
      List<Ingredient> ingredients = getIngredients(jo.getJSONArray("ingredients"));
      List<Step> steps = getSteps(jo.getJSONArray("steps"));

      recipes.add(new Recipe(id, name, ingredients, steps, servings, image));
    }

    return recipes;
  }

  public List<Ingredient> getIngredients(JSONArray ja) throws JSONException {
    LinkedList<Ingredient> ingredients = new LinkedList<>();

    for (int i = 0; i < ja.length(); i++) {
      JSONObject jo = ja.getJSONObject(i);
      int quantity = jo.getInt("quantity");
      String measure = jo.getString("measure");
      String ingredient = jo.getString("ingredient");

      ingredients.add(new Ingredient(quantity, measure, ingredient));
    }

    return new ArrayList<>(ingredients);
  }

  public List<Step> getSteps(JSONArray ja) throws JSONException {
    LinkedList<Step> steps = new LinkedList<>();

    for (int i = 0; i < ja.length(); i++) {
      JSONObject jo = ja.getJSONObject(i);
      int id = jo.getInt("id");
      String shortDescription = jo.getString("shortDescription");
      String description = jo.getString("description");
      String videoUrl = jo.getString("videoUrl");
      String thumbnailUrl = jo.getString("thumbnailUrl");

      steps.add(new Step(id, shortDescription, description, videoUrl, thumbnailUrl));
    }

    return new ArrayList<>(steps);
  }
}
