package com.nikita.recipiesapp.common.models;


public final class Step {
  public final int id;
  public final String shortDescription;
  public final String description;
  public final String videoURL;
  public final String thumbnailURL;

  public Step(int id,
              String shortDescription,
              String description,
              String videoURL,
              String thumbnailURL) {
    this.id = id;
    this.shortDescription = shortDescription;
    this.description = description;
    this.videoURL = videoURL;
    this.thumbnailURL = thumbnailURL;
  }
}
