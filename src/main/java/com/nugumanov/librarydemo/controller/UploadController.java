package com.nugumanov.librarydemo.controller;

import com.nugumanov.librarydemo.domain.Image;
import com.nugumanov.librarydemo.opensave.ImageInOut;
import com.nugumanov.librarydemo.opensave.ImageOpenSave;
import com.nugumanov.librarydemo.repos.ImageRepo;
import com.nugumanov.wavelettransform.WaveletBufferedImage;
import com.nugumanov.wavelettransform.WaveletImage;
import com.nugumanov.wavelettransform.transforms.TransformType;
import com.nugumanov.wavelettransform.transforms.WaveletType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {

    @Autowired
    private ImageRepo imageRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String upload(Model model) {

        Iterable<Image> images = imageRepo.findAll();

        String reverse = null;

        model.addAttribute("images", images);
        model.addAttribute("reverse", reverse);

        return "upload";
    }

    @PostMapping(params = "transform")
    public String transform(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("reverse") String reverse,
            Model model
    ) throws IOException {

        Image image = new Image();

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + "Haar." + file.getOriginalFilename();

            ImageInOut imageInOut = new ImageOpenSave();
            File waveletFile = imageInOut.convert(file);
            BufferedImage waveletBufferedImage = imageInOut.inputImage(waveletFile);
            WaveletImage waveletImage;
            if (reverse.equals("on")) {
                waveletImage = new WaveletBufferedImage(waveletBufferedImage, TransformType.REVERSE, WaveletType.HAAR, 2);
                image.setReverse(true);
            } else {
                waveletImage = new WaveletBufferedImage(waveletBufferedImage, TransformType.FORWARD, WaveletType.HAAR, 2);
                image.setReverse(false);
            }

            imageInOut.outputImage(waveletImage.getTransformedImage(), new File(uploadPath + "/" + resultFileName), "png");

            String filename = "/img/" + resultFileName;
            image.setFilename(filename);
            model.addAttribute("filename", filename);
        }

        imageRepo.save(image);
        Iterable<Image> images = imageRepo.findAll();
        model.addAttribute("images", images);

        return "upload";
    }

    @PostMapping(params = "clear")
    public String clear(Model model) {

        imageRepo.deleteAll();
        return "upload";
    }

    @PostMapping(params = "delete")
    public String delete(
            Model model,
            @RequestParam(value = "image") Image image) {
        imageRepo.delete(image);
        return "upload";
    }
}
