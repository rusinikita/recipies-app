package com.nikita.recipiesapp.common;


import android.support.annotation.NonNull;

public interface Reducer<State> {
  @NonNull
  State reduce(@NonNull final State previousState, @NonNull final Action action);
}
