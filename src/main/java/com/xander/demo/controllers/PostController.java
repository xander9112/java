package com.xander.demo.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xander.demo.models.Post;
import com.xander.demo.services.PostService;

@RestController
public class PostController {
  private final PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping(value = "/posts")
  public ResponseEntity<Iterable<Post>> read() {
    final Iterable<Post> posts = postService.findAll();

    return posts != null
        ? new ResponseEntity<>(posts, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping(value = "/posts")
  // @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Post> createPost(@RequestParam(name = "file1", required = false) MultipartFile file1, Post post)
      throws IOException {
    return new ResponseEntity<>(postService.savePost(post, file1), HttpStatus.OK);

  }

  @DeleteMapping(value = "/posts/{id}")
  public ResponseEntity<Post> deletePost(@PathVariable(name = "id") Long id)
      throws IOException {
    postService.deletePost(id);

    return new ResponseEntity<>(null, HttpStatus.OK);

  }
}
