package com.example.FMIbook.domain.course.course_material;

import com.example.FMIbook.domain.course.course_material.exception.CourseMaterialNotFoundException;
import com.example.FMIbook.domain.course.section.Section;
import com.example.FMIbook.domain.course.section.SectionRepository;
import com.example.FMIbook.domain.course.section.exception.SectionNotFoundException;
import com.example.FMIbook.domain.material.Material;
import com.example.FMIbook.domain.material.MaterialService;
import com.example.FMIbook.domain.policy.CreatePolicy;
import com.example.FMIbook.domain.policy.DeletePolicy;
import com.example.FMIbook.domain.policy.ReadPolicy;
import com.example.FMIbook.domain.policy.exception.CannotCreateException;
import com.example.FMIbook.domain.policy.exception.CannotDeleteException;
import com.example.FMIbook.domain.policy.exception.CannotReadException;
import com.example.FMIbook.domain.users.user.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@Service
public class CourseMaterialService {
    @Autowired
    private final CourseMaterialRepository courseMaterialRepository;
    @Autowired
    private final SectionRepository sectionRepository;
    @Autowired
    private final MaterialService materialService;

    @Transactional
    public CourseMaterialDTO addOne(CourseMaterialRequest requestDto, User user) throws IOException {
        Optional<Section> sectionOpt = sectionRepository.findById(requestDto.getSectionId());

        if (sectionOpt.isEmpty()) {
            throw new SectionNotFoundException();
        }

        if (!CreatePolicy.canCreateSection(user, sectionOpt.get())) {
            throw new CannotCreateException();
        }

        List<Material> materials = new ArrayList<>();

        for (MultipartFile file : requestDto.getFiles()) {
            materials.add(materialService.addOne(file));
        }

        CourseMaterial courseMaterial = CourseMaterial
                .builder()
                .name(requestDto.getName())
                .description(requestDto.getDescription())
                .section(sectionOpt.get())
                .materials(materials)
                .build();
        courseMaterialRepository.save(courseMaterial);
        return CourseMaterialDTO.serializeFromEntity(courseMaterial);
    }

    public CourseMaterialDTO findOne(UUID id, User user) {
        Optional<CourseMaterial> courseMaterial = courseMaterialRepository.findById(id);

        if (courseMaterial.isEmpty()) {
            throw new CourseMaterialNotFoundException();
        }

        if (!ReadPolicy.canReadSection(user, courseMaterial.get().getSection())) {
            throw new CannotReadException();
        }

        return CourseMaterialDTO.serializeFromEntity(courseMaterial.get());
    }

    @Transactional
    public void delete(UUID id, User user) throws IOException {
        Optional<CourseMaterial> courseMaterial = courseMaterialRepository.findById(id);

        if (courseMaterial.isEmpty()) {
            throw new CourseMaterialNotFoundException();
        }

        if (!DeletePolicy.canDeleteSection(user, courseMaterial.get().getSection())) {
            throw new CannotDeleteException();
        }

        for (Material material : courseMaterial.get().getMaterials()) {
            materialService.delete(material.getId());
        }

        courseMaterialRepository.delete(courseMaterial.get());
    }
}
