package com.xander.demo.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xander.demo.models.Image;
import com.xander.demo.repositories.ImageRepository;

@RestController
public class ImageController {
  private final ImageRepository imageRepository;

  @Autowired
  public ImageController(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  // @GetMapping(value = "/media/{id}/{size}")
  // public @ResponseBody byte[] read(@PathVariable(name = "id") String id,
  // @PathVariable(name = "size") String size) {

  // Optional<Image> image = imageRepository.findById(UUID.fromString(id));

  // // if (image.isEmpty()) {
  // // return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  // // }

  // // File f = new File("media/" + id + "/" + size + "." +
  // image.get().getExt());

  // InputStream in = getClass()
  // .getResourceAsStream"media/" + id + "/" + size + "." + image.get().getExt());

  // ByteArrayOutputStream os = new ByteArrayOutputStream();
  // ImageIO.write(file, ext, os);
  // InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

  // return IOUtils.toByteArray(in);
  // }
}
