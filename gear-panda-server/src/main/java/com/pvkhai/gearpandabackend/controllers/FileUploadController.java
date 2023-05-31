package com.pvkhai.gearpandabackend.controllers;

import com.pvkhai.gearpandabackend.models.ResponseObject;
import com.pvkhai.gearpandabackend.services.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("api/v1/fileUpload")
public class FileUploadController {
    @Autowired
    private IStorageService iStorageService;

    @PostMapping
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String generatedFileName = iStorageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Upload file successfully!", generatedFileName)
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("FAILED", exception.getMessage(), "")
            );
        }
    }

}
