package com.xander.demo.services;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xander.demo.models.Image;
import com.xander.demo.models.Story;
import com.xander.demo.repositories.StoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryService {
  private final StoryRepository storyRepository;
  private final ImageService imageService;

  private String uploadDir = "media/";

  public List<Story> listStorys(String title) {
    return storyRepository.findAll();
  }

  public List<Story> findAll() {
    return storyRepository.findAll();
  }

  public Story saveStory(Story story, MultipartFile file) throws IOException {
    log.info("Saving new Story. Title: {}", story.getName());

    Story storyFromDb = storyRepository.save(story);

    if (file.getSize() != 0) {

      Image image = imageService.save(file, uploadDir);

      try {
        storyFromDb.addImageToStory(image);
      } catch (Exception e) {
        log.error("Add image error", e);
      }
    }

    return storyRepository.save(storyFromDb);
  }

  public void deleteStory(Long id) {
    log.info("Deleting story {}", id);

    storyRepository.deleteById(id);
  }

  public Story getStoryById(Long id) {
    return storyRepository.findById(id).orElse(null);
  }
}
