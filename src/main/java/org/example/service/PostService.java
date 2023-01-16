package org.example.service;

import org.example.exception.NotFoundException;
import org.example.model.Post;
import org.example.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ConcurrentSkipListMap;

@Service
public class PostService {
  private final PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public ConcurrentSkipListMap all() {
    return repository.all();
  }

  public Optional<Post> getById(long id) {
    //return Optional.ofNullable(repository.getById(id).orElseThrow(NotFoundException::new));
    return repository.getById(id);
  }

  public Post save(Post post) {
    return repository.save(post);
  }

  public Optional<Post> removeById(long id) {
    return repository.removeById(id);
  }
}

