package com.artistprofile.image.controller;

import com.artistprofile.image.dto.ImageDTO;
import com.artistprofile.image.entity.Image;
import com.artistprofile.image.exception.ImageNotFoundException;
import com.artistprofile.image.service.GcsStorageService;
import com.artistprofile.image.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.artistprofile.image.repository.ImageRepository;   // adjust to actual package
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ImageController {

    private final ImageRepository imageRepository;
    private final StorageService storageService;

    public ImageController(ImageRepository imageRepository,
                           StorageService storageService) {
        this.imageRepository = imageRepository;
        this.storageService = storageService;
    }

    private final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @PostMapping(value = "/images/upload", consumes = "multipart/form-data")
    public ResponseEntity<Image> uploadImage(
        @ModelAttribute ImageDTO imageDTO,
        @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.info("Received empty file to upload");
            return ResponseEntity.status(500).build();
        }
        logger.info("Received upload: {} ({} bytes)",
                file.getOriginalFilename(), file.getSize());
        String url;
        try {
            url = storageService.upload(file);
        } catch (Exception e) {
            String errorMessage = "Error saving the file just uploaded " + file.getOriginalFilename();
            logger.error(errorMessage, e);
            return ResponseEntity.status(500).build();
        }

        // Save the local information
        Image image = imageDTO.toEntity();
        image.setUrl(url);
        Image saved = imageRepository.save(image);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/images/{imageId}")
    public ImageDTO getImage(@PathVariable Long imageId) {

        logger.info("getImage() called with imageId {}", imageId);
        ImageDTO imageDTO = imageRepository.findById(imageId)
                .map(ImageDTO::from)
                .orElseThrow(() -> new ImageNotFoundException(imageId));

        return imageDTO;
    }

    @GetMapping("/images/profile/{profileId}")
    public List<ImageDTO> getImagesByProfile(@PathVariable Long profileId) {
        logger.info("getImagesByProfile() called with profileId {}", profileId);
        List<ImageDTO> imageDTOs = imageRepository.findByProfileId(profileId)
                .stream()
                .map(ImageDTO::from)
                .toList();

        return imageDTOs;
    }

}

