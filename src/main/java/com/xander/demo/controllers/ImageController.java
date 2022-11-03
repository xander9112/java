package com.xander.demo.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

  @ResponseBody
  @GetMapping(value = "/media/{id}/{size}")
  public ResponseEntity<byte[]> read(@PathVariable(name = "id") Long id,
      @PathVariable(name = "size") String size) throws IOException {

    Optional<Image> image = imageRepository.findById(id);
    HttpHeaders headers = new HttpHeaders();

    headers.setCacheControl(CacheControl.noCache().getHeaderValue());
    headers.setContentType(MediaType.IMAGE_JPEG);

    ResponseEntity<byte[]> responseEntity;

    if (image.isEmpty()) {
      responseEntity = new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
    } else {
      File file = new File("media/" + id + "/" + size + "." + image.get().getExt());

      if (!file.exists()) {
        responseEntity = new ResponseEntity<>(null, headers, HttpStatus.NOT_FOUND);
      } else {
        byte[] fileContent = Files.readAllBytes(file.toPath());

        responseEntity = new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
      }

    }

    return responseEntity;

  }
}
