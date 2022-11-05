package com.xander.demo.services;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.io.*;

import org.imgscalr.Scalr;
import org.jboss.logging.Logger;
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
  private static final Logger LOGGER = Logger.getLogger(ImageService.class);

  private int SMALL_WIDTH = 320;
  private int MEDIUM_WIDTH = 640;
  private int BIG_WIDTH = 1080;

  private String SMALL_NAME = "small";
  private String MEDIUM_NAME = "mid";
  private String BIG_NAME = "big";

  public Image save(MultipartFile file, String uploadDir) throws IOException {
    Image image = imageRepository.save(toImageEntity(file));

    String fileName = image.getId().toString() + '.' +
        GetFileExtension.getFileExtension(
            StringUtils.cleanPath(file.getOriginalFilename()));

    BufferedImage bufferedImage = getBufferedImage(file);

    int originalWidth = bufferedImage.getWidth();
    int originalHeight = bufferedImage.getHeight();

    double ratio = ((double) originalWidth / originalHeight);

    image.setWidth(BIG_WIDTH);
    image.setHeight(BIG_WIDTH / ratio);

    generateImages(file, uploadDir + image.getId().toString(), fileName);

    Image newImage = imageRepository.save(image);

    return newImage;
  }

  public void deleteImage(String uploadDir, Long id) {
    Optional<Image> image = imageRepository.findById(id);

    LOGGER.info(uploadDir + SMALL_NAME + "." + image.get().getExt());

    if (!image.isEmpty()) {
      File f_small = new File(uploadDir + id);
      // File f_small = new File(uploadDir + id + "/" + SMALL_NAME + "." +
      // image.get().getExt());
      // File f_medium = new File(uploadDir + id + "/" + MEDIUM_NAME + "." +
      // image.get().getExt());
      // File f_big = new File(uploadDir + id + "/" + BIG_NAME + "." +
      // image.get().getExt());

      deleteDirectory(f_small);
      // f_small.delete();
      // f_medium.delete();
      // f_big.delete();
    }
  }

  boolean deleteDirectory(File directoryToBeDeleted) {
    File[] allContents = directoryToBeDeleted.listFiles();
    if (allContents != null) {
      for (File file : allContents) {
        deleteDirectory(file);
      }
    }
    return directoryToBeDeleted.delete();
  }

  void generateImages(MultipartFile file, String filePath, String fileName) throws IOException {
    BufferedImage bufferedImage = getBufferedImage(file);

    String extension = GetFileExtension.getFileExtension(file.getOriginalFilename());

    int originalWidth = bufferedImage.getWidth();
    int originalHeight = bufferedImage.getHeight();

    int ratio = originalWidth / originalHeight;

    BufferedImage small = resizeImage(bufferedImage, SMALL_WIDTH, SMALL_WIDTH / ratio);
    BufferedImage mid = resizeImage(bufferedImage, MEDIUM_WIDTH, MEDIUM_WIDTH / ratio);
    BufferedImage big = resizeImage(bufferedImage, BIG_WIDTH, BIG_WIDTH / ratio);

    FileUploadUtil.saveFile(small, SMALL_NAME + "." + extension, extension, filePath);
    FileUploadUtil.saveFile(mid, MEDIUM_NAME + "." + extension, extension, filePath);
    FileUploadUtil.saveFile(big, BIG_NAME + "." + extension, extension, filePath);
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
