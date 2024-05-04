package com.example.FMIbook.domain.material;

import com.example.FMIbook.domain.material.exception.MaterialNotFoundException;
import com.example.FMIbook.utils.storage.FirebaseStorageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@Service
public class MaterialService {
    @Autowired
    private final FirebaseStorageService storageService;
    @Autowired
    private final MaterialRepository  materialRepository;

    public void delete(UUID id) throws IOException {
        Optional<Material> materialOpt = materialRepository.findById(id);

        if (materialOpt.isEmpty()) {
            throw new MaterialNotFoundException();
        }

        Material material = materialOpt.get();

        storageService.delete(material.getName());
        materialRepository.delete(materialOpt.get());
    }

    public Material addOne(MultipartFile file) throws IOException {
        String name = storageService.save(file);
        String originalName = file.getOriginalFilename();
        URL url = storageService.getFileUrl(name);

        Material material = Material
                .builder()
                .originalName(originalName)
                .name(name)
                .url(url.toString())
                .build();
        materialRepository.save(material);
        return material;
    }

    public Material getOne(UUID id) {
        Optional<Material> materialOpt = materialRepository.findById(id);

        if (materialOpt.isEmpty()) {
            throw new MaterialNotFoundException();
        }

        return materialOpt.get();
    }
}
