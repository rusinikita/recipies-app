package com.nikita.recipiesapp.views.steps;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.nikita.recipiesapp.App;
import com.nikita.recipiesapp.R;
import com.nikita.recipiesapp.common.AppState;
import com.nikita.recipiesapp.common.models.Recipe;
import com.nikita.recipiesapp.common.models.Step;
import com.nikita.recipiesapp.common.redux.Renderer;

public class StepVideoFragment extends LifecycleFragment implements Renderer<AppState> {
  private SimpleDraweeView imageView;
  private SimpleExoPlayerView videoView;
  private SimpleExoPlayer player;
  private String nowPlayingUrl;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public StepVideoFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.step_video, container, false);
    imageView = rootView.findViewById(R.id.step_video_image);
    videoView = rootView.findViewById(R.id.step_video_video);

    App.appStore.subscribe(this);

    return rootView;
  }

  @Override
  public void render(@NonNull AppState appState) {
    Recipe recipe = appState.selectedRecipe();
    Step step = appState.selectedStep();
    String videoURL = step.videoURL;
    if (videoURL != null && !videoURL.isEmpty()) {
      videoView.setVisibility(View.VISIBLE);
      imageView.setVisibility(View.GONE);
      playVideo(videoURL);
    } else {
      imageView.setVisibility(View.VISIBLE);
      videoView.setVisibility(View.GONE);
      releasePlayer();
      imageView.setImageURI(recipe.getImageUri());
    }
  }

  @Override
  public void onPause() {
    super.onPause();
    releasePlayer();
  }

  private void playVideo(@NonNull String videoUrl) {
    initializePlayerIfNeed();
    if (!videoUrl.equals(nowPlayingUrl)) {

      DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
      Context context = getContext();
      DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
          Util.getUserAgent(context, "recipes-app"), bandwidthMeter);
      ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
      MediaSource videoSource = new ExtractorMediaSource(Uri.parse(videoUrl),
          dataSourceFactory, extractorsFactory, null, null);
      LoopingMediaSource loopingSource = new LoopingMediaSource(videoSource, 10);

      player.prepare(loopingSource);
      player.setPlayWhenReady(true);
      nowPlayingUrl = videoUrl;
    }
  }

  private void initializePlayerIfNeed() {
    if (player == null) {
      BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
      TrackSelection.Factory videoTrackSelectionFactory =
          new AdaptiveTrackSelection.Factory(bandwidthMeter);
      TrackSelector trackSelector =
          new DefaultTrackSelector(videoTrackSelectionFactory);

      player = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector);
      videoView.setPlayer(player);
    }
  }

  private void releasePlayer() {
    if (player != null) {
      player.stop();
      player.release();
      player = null;
    }
  }
}
