package com.example.FMIbook.utils.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;

public interface StorageService {
    URL getFileUrl(String name);
    String save(MultipartFile file) throws IOException;
    void delete(String name) throws IOException;
}
