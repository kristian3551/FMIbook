package com.example.FMIbook.utils.storage;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseStorageService {
    @Value("${firebase.storage-bucket}")
    private String bucketName;

    public URL getFileUrl(String name) {
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        Blob blob = storage.get(bucketName, name);
        return blob.signUrl(365 * 50, TimeUnit.DAYS);
    }

    private String generateFileName(String name) throws IOException {
        if (name == null) {
            throw new IOException("file name is null");
        }
        String[] tokens = name.split("\\.");
        String extension = tokens[tokens.length - 1];
        return UUID.randomUUID() + "." + extension;
    }

    public String save(MultipartFile file) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();
        String name = generateFileName(file.getOriginalFilename());
        bucket.create(name, file.getBytes(), file.getContentType());
        return name;
    }

    public void delete(String name) throws IOException {
        Bucket bucket = StorageClient.getInstance().bucket();

        if (name.isEmpty()) {
            throw new IOException("invalid file name");
        }

        Blob blob = bucket.get(name);

        if (blob == null) {
            throw new IOException("file not found");
        }

        blob.delete();
    }
}
