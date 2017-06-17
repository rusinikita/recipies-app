package com.nikita.recipiesapp.common;


public abstract class Middleware<State> implements Consumer<Action> {
  protected Store<State> store;
  protected Consumer<Action> wrappedConsumer ;

  public void init(Store<State> store, Consumer<Action> wrappedConsumer ) {
    this.store = store;
    this.wrappedConsumer = wrappedConsumer ;
  }
}
