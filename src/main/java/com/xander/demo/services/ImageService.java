package com.xander.demo.services;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.xander.demo.models.Image;
import com.xander.demo.repositories.ImageRepository;
import com.xander.demo.utils.FileUploadUtil;
import com.xander.demo.utils.GetFileExtension;

@Service
public class ImageService {

  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  private ImageRepository imageRepository;

  private int SMALL_WIDTH = 320;
  private int MEDIUM_WIDTH = 640;
  private int HIGH_WIDTH = 1080;

  public Image save(MultipartFile file, String uploadDir) throws IOException {
    Image image = imageRepository.save(toImageEntity(file));

    String fileName = image.getId().toString() + '.' +
        GetFileExtension.getFileExtension(
            StringUtils.cleanPath(file.getOriginalFilename()));

    BufferedImage bufferedImage = getBufferedImage(file);

    int originalWidth = bufferedImage.getWidth();
    int originalHeight = bufferedImage.getHeight();

    double ratio = ((double) originalWidth / originalHeight);

    image.setWidth(HIGH_WIDTH);
    image.setHeight(HIGH_WIDTH / ratio);

    generateImages(file, uploadDir + image.getId().toString(), fileName);

    Image newImage = imageRepository.save(image);

    return newImage;
  }

  void generateImages(MultipartFile file, String filePath, String fileName) throws IOException {
    BufferedImage bufferedImage = getBufferedImage(file);

    String extension = GetFileExtension.getFileExtension(file.getOriginalFilename());

    int originalWidth = bufferedImage.getWidth();
    int originalHeight = bufferedImage.getHeight();

    int ratio = originalWidth / originalHeight;

    BufferedImage small = resizeImage(bufferedImage, SMALL_WIDTH, SMALL_WIDTH / ratio);
    BufferedImage mid = resizeImage(bufferedImage, MEDIUM_WIDTH, MEDIUM_WIDTH / ratio);
    BufferedImage big = resizeImage(bufferedImage, HIGH_WIDTH, HIGH_WIDTH / ratio);

    FileUploadUtil.saveFile(small, "small." + extension, extension, filePath);
    FileUploadUtil.saveFile(mid, "mid." + extension, extension, filePath);
    FileUploadUtil.saveFile(big, "big." + extension, extension, filePath);
  }

  BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
    return Scalr.resize(originalImage, targetWidth);
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

  private BufferedImage getBufferedImage(MultipartFile file) throws IOException {
    InputStream inputStream = new ByteArrayInputStream(file.getBytes());

    return ImageIO.read(inputStream);
  }
}
