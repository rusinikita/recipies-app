package com.nikita.recipiesapp.common.redux;


public interface Consumer<T> {
  void consume(T t);
}
