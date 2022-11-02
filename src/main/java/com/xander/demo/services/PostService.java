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

  public List<Post> listPosts(String title) {
    return postRepository.findAll();
  }

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  public Post savePost(Post post, MultipartFile file1) throws IOException {
    Image image1;

    if (file1.getSize() != 0) {
      image1 = toImageEntity(file1);
      image1.setPreviewImage(true);
      post.addImageToPost(image1);
    }

    log.info("Saving new Post. Title: {}", post.getName());

    Post postFromDb = postRepository.save(post);

    postFromDb.setPreviewImageId(postFromDb.getImages().get(0).getId());

    return postRepository.save(postFromDb);

  }

  private Image toImageEntity(MultipartFile file) throws IOException {
    Image image = new Image();
    image.setName(file.getName());
    image.setOriginalFileName(file.getOriginalFilename());
    image.setContentType(file.getContentType());
    image.setSize(file.getSize());
    image.setBytes(file.getBytes());

    return image;
  }

  public void deletePost(Long id) {
    log.info("Deleting post {}", id);

    postRepository.deleteById(id);
  }

  public Post getPostById(Long id) {
    return postRepository.findById(id).orElse(null);
  }
}
