package com.nugumanov.librarydemo.controller;

import com.nugumanov.librarydemo.opensave.ImageInOut;
import com.nugumanov.librarydemo.opensave.ImageOpenSave;
import com.nugumanov.wavelettransform.WaveletBufferedImage;
import com.nugumanov.wavelettransform.WaveletImage;
import com.nugumanov.wavelettransform.transforms.TransformType;
import com.nugumanov.wavelettransform.transforms.WaveletType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String upload(Model model) {
        String confirm1 = null;
        model.addAttribute("confirm1", confirm1);
        return "upload";
    }

    @PostMapping("/")
    public String forward(
            @RequestParam("file") MultipartFile file,
            @ModelAttribute("confirm1") String confirm1,
            Model model
    ) throws IOException {

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
            if (confirm1.equals("on")) {
                waveletImage = new WaveletBufferedImage(waveletBufferedImage, TransformType.REVERSE, WaveletType.HAAR, 2);
            } else {
                waveletImage = new WaveletBufferedImage(waveletBufferedImage, TransformType.FORWARD, WaveletType.HAAR, 2);
            }

            imageInOut.outputImage(waveletImage.getTransformedImage(), new File(uploadPath + "/" + resultFileName), "png");

            String filename = "/img/" + resultFileName;
            model.addAttribute("filename", filename);
        }

        return "upload";
    }
}
