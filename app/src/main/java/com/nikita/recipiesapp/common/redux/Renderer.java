package com.nikita.recipiesapp.common.redux;


import android.support.annotation.NonNull;

public interface Renderer<State> {
  void render(@NonNull final State state);
}
