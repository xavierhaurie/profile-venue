package com.profilevenue.image.service;

import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file) throws IOException;
}
