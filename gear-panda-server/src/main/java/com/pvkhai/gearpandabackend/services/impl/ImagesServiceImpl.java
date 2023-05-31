package com.pvkhai.gearpandabackend.services.impl;


import com.pvkhai.gearpandabackend.models.Images;
import com.pvkhai.gearpandabackend.repositories.ImagesRepository;
import com.pvkhai.gearpandabackend.services.ImagesService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImagesServiceImpl implements ImagesService {
    @Autowired
    ImagesRepository imagesRepository;

    @Override
    public Images store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        System.out.printf(file.getName());
        Images image = new Images(fileName, file.getContentType(), file.getBytes());
        return imagesRepository.save(image);
    }

    @Override
    public Boolean isImageFile(MultipartFile file) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public Stream<Images> getAllFiles() {
        return imagesRepository.findAll().stream();
    }



    /**
     * Check if it's an image file or not
     *
     * @param file
     * @return
     */
//    @Override
//    boolean isImageFile(MultipartFile file) {
//        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
//        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"})
//                .contains(fileExtension.trim().toLowerCase());
//    }

//    public String storeFile(MultipartFile file) {
//        try {
//            if (file.isEmpty()) {
//                throw new RuntimeException("Failed to store empty file");
//            }
//
//            // Check file image
//            if (!isImageFile(file)) {
//                throw new RuntimeException("You can upload image file");
//            }
//            // File must be <= 5Mb
//            float fileSizeInMegabytes = file.getSize() / 1_000_000f;
//            if (fileSizeInMegabytes > 5.0f) {
//                throw new RuntimeException("File must be <= 5Mb");
//            }
//            // File must rename
//            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
//            String generateFileName = UUID.randomUUID().toString().replace("-", "");
//            generateFileName = generateFileName + "." + fileExtension;
//            Path destinationFilePath = this.storageFolder.resolve(
//                            Paths.get(generateFileName))
//                    .normalize().toAbsolutePath();
//            if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
//                throw new RuntimeException("Cannot store file outside current directory");
//            }
//            try (InputStream inputStream = file.getInputStream()) {
//                Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
//            }
//            return generateFileName;
//        } catch (IOException exception) {
//            throw new RuntimeException("Cannot initialize storage", exception);
//        }
//    }

}
