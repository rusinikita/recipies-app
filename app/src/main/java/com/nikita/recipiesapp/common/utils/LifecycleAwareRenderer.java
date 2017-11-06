package com.nikita.recipiesapp.common.utils;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.annotation.Nullable;

import com.nikita.recipiesapp.common.redux.Renderer;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public final class LifecycleAwareRenderer<State> implements LifecycleObserver {
    private final Renderer<State> renderer;
    private final Lifecycle lifecycle;
    private final Disposable disposable;
    @Nullable
    private State pendingState;

    public <Subscriber extends LifecycleOwner & Renderer<State>> LifecycleAwareRenderer(Subscriber subscriber, Observable<State> observable) {
        renderer = subscriber;
        lifecycle = subscriber.getLifecycle();

        disposable = observable
                .subscribe(state -> {
                    if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
                        renderer.render(state);
                    } else {
                        pendingState = state;
                    }
                });

        lifecycle.addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {
        if (pendingState != null) {
            renderer.render(pendingState);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    void destroy() {
        disposable.dispose();
    }
}
