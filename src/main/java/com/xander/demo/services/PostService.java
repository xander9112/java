package com.xander.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xander.demo.models.Image;
import com.xander.demo.models.Post;
import com.xander.demo.repositories.PostRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final ImageService imageService;

  private String uploadDir = "media/";

  public List<Post> listPosts(String title) {
    return postRepository.findAll();
  }

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  public Post savePost(Post post, MultipartFile file) throws IOException {
    log.info("Saving new Post. Title: {}", post.getName());

    Post postFromDb = postRepository.save(post);

    if (file.getSize() != 0) {

      Image image = imageService.save(file, uploadDir);

      try {
        postFromDb.addImageToPost(image);
      } catch (Exception e) {
        log.error("Add image error", e);
      }
    }

    return postRepository.save(postFromDb);
  }

  public void deletePost(Long id) {
    log.info("Deleting post {}", id);

    postRepository.deleteById(id);
  }

  public Post getPostById(Long id) {
    return postRepository.findById(id).orElse(null);
  }
}
