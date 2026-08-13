package com.artistprofile.image.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("!gcp")
public class LocalStorageService implements StorageService {

    private final Path storageDir;

    public LocalStorageService(
            @Value("${local.storage.dir:uploads}") String storageDir) throws IOException {
        this.storageDir = Paths.get(storageDir).toAbsolutePath().normalize();
        Files.createDirectories(this.storageDir);
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        String objectName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        Path target = this.storageDir.resolve(objectName).normalize();
        if (!target.startsWith(this.storageDir)) {
            throw new IOException("Invalid file path: " + file.getOriginalFilename());
        }
        Files.copy(file.getInputStream(), target);
        return target.toUri().toString();
    }
}
