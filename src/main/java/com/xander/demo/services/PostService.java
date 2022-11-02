package com.xander.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xander.demo.models.Image;
import com.xander.demo.models.Post;
import com.xander.demo.repositories.ImageRepository;
import com.xander.demo.repositories.PostRepository;
import com.xander.demo.utils.FileUploadUtil;
import com.xander.demo.utils.GetFileExtension;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {
  private final PostRepository postRepository;
  private final ImageRepository imageRepository;
  private final ImageService imageService;

  public List<Post> listPosts(String title) {
    return postRepository.findAll();
  }

  public List<Post> findAll() {
    return postRepository.findAll();
  }

  public Post savePost(Post post, MultipartFile file1) throws IOException {
    Image image1;

    log.info("Saving new Post. Title: {}", post.getName());

    // Post postFromDb = postRepository.save(post);

    if (file1.getSize() != 0) {
      image1 = toImageEntity(file1);

      Image image = imageRepository.save(image1);

      String fileName = image.getId().toString() + '.' + GetFileExtension.getFileExtension(
          StringUtils.cleanPath(file1.getOriginalFilename()));

      String uploadDir = "media/" + image.getId().toString();

      imageService.generateImages(file1, uploadDir, fileName);

      try {
        post.addImageToPost(image);
      } catch (Exception e) {
        log.error("Add image error", e);
      }
    }

    // postFromDb.setPreviewImageId(postFromDb.getImages().get(0).getId());

    // return postRepository.save(postFromDb);
    return postRepository.save(post);

  }

  private Image toImageEntity(MultipartFile file) throws IOException {
    Image image = new Image();
    image.setName(file.getName());
    image.setOriginalFileName(file.getOriginalFilename());
    image.setContentType(file.getContentType());
    image.setSize(file.getSize());
    image.setExt(GetFileExtension.getFileExtension(file.getOriginalFilename()));

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
