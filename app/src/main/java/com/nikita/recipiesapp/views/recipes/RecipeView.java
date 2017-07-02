package com.nikita.recipiesapp.views.recipes;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.airbnb.epoxy.ModelProp;
import com.airbnb.epoxy.ModelView;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nikita.recipiesapp.R;

@ModelView(defaultLayout = R.layout.recipes_item)
final class RecipeView extends CardView {
  private final SimpleDraweeView image;
  private final TextView name;

  public RecipeView(Context context) {
    this(context, null);
  }

  public RecipeView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public RecipeView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    LayoutInflater.from(context).inflate(R.layout.recipes_view, this, true);

    image = (SimpleDraweeView) findViewById(R.id.image);
    name = (TextView) findViewById(R.id.name);
  }

  @ModelProp()
  void setImageUri(String imageUri) {
    image.setImageURI(imageUri);
  }

  @ModelProp()
  void setName(String name) {
    this.name.setText(name);
  }

  @ModelProp(options = ModelProp.Option.DoNotHash)
  public void setOnClickListener(OnClickListener listener) {
    super.setOnClickListener(listener);
  }
}
