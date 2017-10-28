package com.nikita.recipiesapp.views;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.R;

public final class IngredientsWidgetProvider extends AppWidgetProvider {
  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    super.onUpdate(context, appWidgetManager, appWidgetIds);
    CharSequence text = getWidgetText(context);
    boolean isEmpty = text.length() == 0;

    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
    views.setViewVisibility(R.id.ingredients_widget_empty_view, isEmpty ? View.VISIBLE : View.GONE);
    views.setViewVisibility(R.id.ingredients_widget_list, !isEmpty ? View.VISIBLE : View.GONE);
    if (!isEmpty) {
      Intent intent = new Intent(context, ViewsService.class);
      views.setRemoteAdapter(R.id.ingredients_widget_list, intent);
    }

    appWidgetManager.updateAppWidget(appWidgetIds, views);
  }

  public static CharSequence getWidgetText(Context context) {
    SharedPreferences sharedPreferences = context.getSharedPreferences(App.PREF_NAME, Context.MODE_PRIVATE);
    String text = sharedPreferences.getString(App.PREF_KEY_WIDGET, "");
    return Html.fromHtml(text);
  }

  public static class ViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
      return new ViewFactory(getApplicationContext());
    }
  }

  /**
   * class for supporting scrolling of single item
   */
  private static class ViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context context;

    private ViewFactory(Context context) {
      this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
      return 1;
    }

    @Override
    public RemoteViews getViewAt(int position) {
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_item);
      views.setTextViewText(R.id.ingredients_widget_text, getWidgetText(context));
      return views;
    }

    @Override
    public RemoteViews getLoadingView() {
      return null;
    }

    @Override
    public int getViewTypeCount() {
      return 1;
    }

    @Override
    public long getItemId(int position) {
      return 1;
    }

    @Override
    public boolean hasStableIds() {
      return true;
    }
  }
}
