package com.nikita.recipiesapp.actions;

import com.nikita.recipiesapp.common.redux.Action;


public final class ShowError implements Action {
  public final String message;

  public ShowError(String message) {
    this.message = message;
  }
}
