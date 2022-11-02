package com.xander.demo.services;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xander.demo.utils.FileUploadUtil;

@Service
public class ImageService {
  private int SMALL_WIDTH = 320;
  private int MEDIUM_WIDTH = 640;
  private int HIGH_WIDTH = 1024;

  void generateImages(MultipartFile file, String filePath, String fileName) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(file.getBytes());
    BufferedImage bufferedImage = ImageIO.read(inputStream);

    String extension = "";

    int index = fileName.lastIndexOf('.');

    if (index > 0) {
      extension = fileName.substring(index + 1);
    }

    BufferedImage small = resizeImage(bufferedImage, SMALL_WIDTH, SMALL_WIDTH * 5 / 4);
    BufferedImage mid = resizeImage(bufferedImage, MEDIUM_WIDTH, MEDIUM_WIDTH * 5 / 4);
    BufferedImage high = resizeImage(bufferedImage, HIGH_WIDTH, HIGH_WIDTH * 5 / 4);

    FileUploadUtil.saveFile(small, "small." + extension, extension, filePath);
    FileUploadUtil.saveFile(mid, "mid." + extension, extension, filePath);
    FileUploadUtil.saveFile(high, "high." + extension, extension, filePath);
  }

  BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

    Graphics2D graphics2D = resizedImage.createGraphics();

    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);

    graphics2D.dispose();

    return resizedImage;
  }
}
