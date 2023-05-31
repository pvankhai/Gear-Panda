package com.pvkhai.gearpandabackend.services;

import com.pvkhai.gearpandabackend.models.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface ImagesService {
    Images store(MultipartFile file) throws IOException;

    Boolean isImageFile(MultipartFile file);

    Stream<Images> getAllFiles();

}
