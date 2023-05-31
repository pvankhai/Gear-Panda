package com.pvkhai.gearpandabackend.controllers;

import com.pvkhai.gearpandabackend.services.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/image")
public class ImagesController {

    @Autowired
    ImagesService imagesService;

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            imagesService.store(file);

            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            return "OK";
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
           return "Failed";
        }
    }
}
