package com.example.FMIbook.server.material;

import com.example.FMIbook.utils.storage.FirebaseStorageService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/material")
public class MaterialController {
    @Autowired
    private final FirebaseStorageService storageService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> add(@RequestParam(name = "files") MultipartFile[] files) throws IOException {
        ArrayList<String> filePaths = new ArrayList<>();
        for (MultipartFile file : files) {
            String path = storageService.save(file);

            filePaths.add(path);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("filePaths", filePaths);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public void delete(@RequestParam(name = "filePath") String filePath) throws IOException {
        storageService.delete(filePath);
    }
}
