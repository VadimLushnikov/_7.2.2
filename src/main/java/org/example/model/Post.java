package org.example.model;

import java.util.concurrent.atomic.AtomicLong;

public class Post {
  //private long id;
  private long id;
  private String content;

  public Post() {
  }

  //public Post(long id, String content) {
  public Post(long id, String content) {
    this.id = id;
    this.content = content;
  }
  public Post(String content) {
    this.id = 0;
    this.content = content;
  }

  //public long getId() {
  public long getId() {
    return id;
  }

  //public void setId(long id) {
  public void setId(long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return this.id+" id"+"\n\r"+
            this.content+" content";
  }
}
