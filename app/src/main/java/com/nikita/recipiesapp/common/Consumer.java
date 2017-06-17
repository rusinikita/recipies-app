package com.nikita.recipiesapp.common;


public interface Consumer<T> {
  void consume(T t);
}
