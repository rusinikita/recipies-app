package com.nikita.recipiesapp.common.redux;


import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.nikita.recipiesapp.common.utils.Check;

public final class Store<State> {
  private final MutableLiveData<State> liveData = new MutableLiveData<>();
  private final Reducer<State> reducer;
  private final Consumer<Action> actionConsumer;
  private boolean isUiTestingMode = false;

  public Store(@NonNull Reducer<State> reducer, @NonNull State initialState) {
    this.reducer = reducer;
    liveData.setValue(initialState);
    this.actionConsumer = this.getStoreConsumer();
  }

  public Store(@NonNull Reducer<State> reducer, @NonNull Middleware<State>[] middlewares, @NonNull State initialState) {
    this.reducer = reducer;
    liveData.setValue(initialState);
    this.actionConsumer = combineMiddlewares(this, middlewares);
  }

  @NonNull
  public State getState() {
    State state = liveData.getValue();
    Check.notNull(state);
    return state;
  }

  public <Subscriber extends LifecycleOwner & Renderer<State>> void subscribe(final Subscriber subscriber) {
    if (isUiTestingMode) return;

    liveData.observe(subscriber, state -> {
      Check.notNull(state);
      subscriber.render(state);
    });
  }

  public void dispatch(Action action) {
    if (isUiTestingMode) return;

    actionConsumer.consume(action);
  }

  @VisibleForTesting
  public void setUiTestMode(boolean isUiTest) {
    Log.d("", "Test mode changed: " + isUiTest);
    isUiTestingMode = isUiTest;
  }

  private Consumer<Action> getStoreConsumer() {
    return action -> liveData.postValue(reducer.reduce(getState(), action));
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

  private static <State> Consumer<Action> combineMiddlewares(Store<State> store, Middleware<State>[] middlewares) {
    Consumer<Action> currentConsumer = store.getStoreConsumer();
    for (Middleware<State> middleware : middlewares) {
      middleware.init(store, currentConsumer);
      currentConsumer = middleware;
    }
    return currentConsumer;
  }
}
