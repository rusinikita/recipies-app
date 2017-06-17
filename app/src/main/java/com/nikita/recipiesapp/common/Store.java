package com.nikita.recipiesapp.common;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.nikita.recipiesapp.BuildConfig;
import com.nikita.recipiesapp.common.utils.Check;

public final class Store<State> {
  private final MutableLiveData<State> liveData = new MutableLiveData<>();
  private final Reducer<State> reducer;

  public Store(@NonNull Reducer<State> reducer, @NonNull State initialState) {
    this.reducer = reducer;
    liveData.setValue(initialState);
  }

  @NonNull
  public State getState() {
    State state = liveData.getValue();
    Check.notNull(state);
    return state;
  }

  public <Subscriber extends LifecycleOwner & Renderer<State>> void subscribe(final Subscriber subscriber) {
    if (BuildConfig.IS_UI_TESTING) return;

    liveData.observe(subscriber, state -> {
      Check.notNull(state);
      subscriber.render(state);
    });
  }

  public void dispatch(Action action) {
    if (BuildConfig.IS_UI_TESTING) return;

    State state = liveData.getValue();
    Check.notNull(state);

    liveData.setValue(reducer.reduce(state, action));
  }

  public static class ReducerCombiner<State> implements Reducer<State> {
    private final Reducer<State>[] reducers;

    public ReducerCombiner(Reducer<State>[] reducers) {
      this.reducers = reducers;
    }

    @NonNull
    @Override
    public State reduce(@NonNull State previousState, @NonNull Action action) {

      State state = previousState;
      for (Reducer<State> reducer : reducers) {
        state = reducer.reduce(state, action);
      }

      return state;
    }
  }
}
