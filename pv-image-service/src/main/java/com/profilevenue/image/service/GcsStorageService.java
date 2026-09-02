package com.profilevenue.image.service;

import java.io.IOException;
import java.util.UUID;

import com.google.cloud.storage.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Profile("gcp")
public class GcsStorageService implements StorageService {

    private final Storage storage;
    private final String bucket;

    public GcsStorageService(@Value("${gcs.bucket}") String bucket) {
        this.bucket = bucket;
        this.storage = StorageOptions.getDefaultInstance().getService();
        ensureBucketExists();
    }

    private void ensureBucketExists() {
        if (storage.get(bucket) == null) {
            storage.create(
                    BucketInfo.newBuilder(bucket)
                            .setLocation("US")            // or "US-EAST1", "EUROPE-WEST1", etc.
                            .build()
            );
        }
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        String objectName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucket, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        storage.create(blobInfo, file.getBytes());

        return String.format("https://storage.googleapis.com/%s/%s", bucket, objectName);
    }

}
