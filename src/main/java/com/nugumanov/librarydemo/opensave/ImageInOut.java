package com.nugumanov.librarydemo.opensave;

import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public interface ImageInOut {

    BufferedImage inputImage(File file);

    void outputImage(BufferedImage image, File outFile, String format);

    File convert(MultipartFile file) throws IOException;
}
