package com.xander.demo.utils;

public class GetFileExtension {
  private static final String WINDOWS_FILE_SEPARATOR = "\\";
  private static final String UNIX_FILE_SEPARATOR = "/";
  private static final String FILE_EXTENSION = ".";

  public static String getFileExtension(String fileName) {
    if (fileName == null) {
      throw new IllegalArgumentException("fileName must not be null!");
    }

    String extension = "";

    int indexOfLastExtension = fileName.lastIndexOf(FILE_EXTENSION);

    // check last file separator, windows and unix
    int lastSeparatorPosWindows = fileName.lastIndexOf(WINDOWS_FILE_SEPARATOR);
    int lastSeparatorPosUnix = fileName.lastIndexOf(UNIX_FILE_SEPARATOR);

    // takes the greater of the two values, which mean last file separator
    int indexOflastSeparator = Math.max(lastSeparatorPosWindows, lastSeparatorPosUnix);

    // make sure the file extension appear after the last file separator
    if (indexOfLastExtension > indexOflastSeparator) {
      extension = fileName.substring(indexOfLastExtension + 1);
    }

    return extension;

  }
}
