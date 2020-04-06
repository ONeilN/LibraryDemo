package com.nugumanov.librarydemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {

    @Value("${upload.path}")
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

        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + "Haar." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFileName));

            String filename = "/img/" + resultFileName;
            model.addAttribute("filename", filename);
        }

        return "upload";
    }
}
