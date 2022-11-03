package com.xander.demo.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.awt.image.BufferedImage;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

public class FileUploadUtil {
  public static void saveFile(BufferedImage file, String fileName, String ext, String uploadDir) throws IOException {
    Path uploadPath = Paths.get(uploadDir);

    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ImageIO.write(file, ext, os);
    InputStream inputStream = new ByteArrayInputStream(os.toByteArray());

    Path filePath = uploadPath.resolve(fileName);

    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
  }
}
