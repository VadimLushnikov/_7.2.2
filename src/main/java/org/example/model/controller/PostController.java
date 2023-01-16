package org.example.model.controller;

import com.google.gson.Gson;
import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
import java.util.Optional;

public class PostController {
  public static final String APPLICATION_JSON = "application/json";
  private final PostService service;

  public PostController(PostService service) {
    this.service = service;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var data = service.all();
    final var gson = new Gson();
    response.getWriter().print(gson.toJson(data));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    // TODO: deserialize request & serialize response
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    //System.out.println(service.getById(id).getContent());
    final Optional<Post> data = service.getById(id);
    if(data.isEmpty()){
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().print("id: " + id + " not found");
      return;
    }
    response.getWriter().print(gson.toJson(data));
  }

  //public void save(Reader body, HttpServletResponse response) throws IOException {
  public void save(String body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final Post post = gson.fromJson(body, Post.class);
    final Post data = service.save(post);
    response.getWriter().print(gson.toJson(data));
  }

  public void removeById(long id, HttpServletResponse response) throws IOException{
    // TODO: deserialize request & serialize response
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final Optional<Post> data = service.removeById(id);
    if(data.isEmpty()){
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      response.getWriter().print("id: " + id + " not found");
    }
    response.getWriter().print(gson.toJson(data));
  }
}
