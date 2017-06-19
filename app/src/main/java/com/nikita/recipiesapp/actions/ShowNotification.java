package com.nikita.recipiesapp.actions;

import com.nikita.recipiesapp.common.redux.Action;


public final class ShowNotification implements Action {
  public final String message;

  public ShowNotification(String message) {
    this.message = message;
  }
}
