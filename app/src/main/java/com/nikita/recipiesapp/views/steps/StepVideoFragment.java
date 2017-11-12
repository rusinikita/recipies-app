package com.nikita.recipiesapp.views.steps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class StepVideoFragment extends Fragment implements Renderer<AppState> {
  private static final String PLAY_POSITION = "play_position";
  private static final String PLAY_VIDEO = "play_video";
  private SimpleDraweeView imageView;
  private SimpleExoPlayerView videoView;
  private SimpleExoPlayer player;
  private String nowPlayingUrl;
  private String restoredVideoUrl = null;
  private long restoredPlayPosition = -1;

  /**
   * Mandatory empty constructor for the fragment manager to instantiate the
   * fragment (e.g. upon screen orientation changes).
   */
  public StepVideoFragment() {
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState != null) {
      restoredVideoUrl = savedInstanceState.getString(PLAY_VIDEO);
      restoredPlayPosition = savedInstanceState.getLong(PLAY_POSITION, -1);
    }
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
      String imageUrl = step.thumbnailURL == null || step.thumbnailURL.isEmpty() ? recipe.image : step.thumbnailURL;
      imageView.setImageURI(imageUrl);
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

      if (videoUrl.equals(restoredVideoUrl) && restoredPlayPosition > 0) {
        player.seekTo(restoredPlayPosition);
      }

      player.setPlayWhenReady(true);
      nowPlayingUrl = videoUrl;
    }
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if (restoredVideoUrl != null) {
      outState.putString(PLAY_VIDEO, restoredVideoUrl);
      outState.putLong(PLAY_POSITION, restoredPlayPosition);
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
      restoredVideoUrl = nowPlayingUrl;
      restoredPlayPosition = player.getCurrentPosition();
      player.stop();
      player.release();
      player = null;
    }
  }
}
