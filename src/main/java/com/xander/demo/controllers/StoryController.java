package com.xander.demo.controllers;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.xander.demo.models.Story;
import com.xander.demo.services.StoryService;

@RestController
public class StoryController {
  private final StoryService storyService;

  @Autowired
  public StoryController(StoryService storyService) {
    this.storyService = storyService;
  }

  @GetMapping(value = "/stories")
  public ResponseEntity<Iterable<Story>> read() {
    final Iterable<Story> stories = storyService.findAll();

    return stories != null
        ? new ResponseEntity<>(stories, HttpStatus.OK)
        : new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @PostMapping(value = "/stories")
  public ResponseEntity<Story> createStory(@RequestParam(name = "file", required = false) MultipartFile file1,
      Story story)
      throws IOException {
    return new ResponseEntity<>(storyService.saveStory(story, file1), HttpStatus.OK);

  }
}
