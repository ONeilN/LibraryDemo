package com.nugumanov.librarydemo.controller;

import com.nugumanov.librarydemo.domain.Image;
import com.nugumanov.librarydemo.repos.ImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class UploadController {

    @Autowired
    private ImageRepo imageRepo;

    @Value("/images")
    private String uploadPath;

    @GetMapping("/")
    public String upload(Model model) {
        return "upload";
    }

    @PostMapping("/")
    public String add(
            @RequestParam("file") MultipartFile file,
            Model model
    ) throws IOException {

        Image image = new Image();

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String resultFileName = "Haar." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            image.setFileName(resultFileName);
        }

        imageRepo.save(image);

        Iterable<Image> images = imageRepo.findAll();

        model.addAttribute("images", images);

        return "upload";
    }
}
