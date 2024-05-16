package com.example.FMIbook.domain.department;

import com.example.FMIbook.domain.department.exception.DepartmentNotFoundException;
import com.example.FMIbook.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentDTO> findAll(
            Integer limit,
            Integer offset,
            String sort
    ) {
        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "name", Sort.Direction.ASC);
        Page<Department> departments = departmentRepository.findAll(page);
        return departments.getContent().stream().map(DepartmentDTO::serializeLightweight).toList();
    }

    public DepartmentDTO getOne(UUID id) {
        Department department = departmentRepository.findById(id).orElseThrow(DepartmentNotFoundException::new);
        return DepartmentDTO.serializeFromEntity(department);
    }

    public DepartmentDTO addOne(Department department) {
        departmentRepository.save(department);
        return DepartmentDTO.serializeFromEntity(department);
    }

    public DepartmentDTO update(UUID id, DepartmentDTO departmentDto) {
        Department department = departmentRepository.findById(id).orElseThrow(DepartmentNotFoundException::new);

        if (departmentDto.getName() != null) {
            department.setName(departmentDto.getName());
        }

        departmentRepository.save(department);
        return DepartmentDTO.serializeFromEntity(department);
    }

    public void delete(UUID id) {
        Department department = departmentRepository.findById(id).orElseThrow(DepartmentNotFoundException::new);
        departmentRepository.delete(department);
    }
}
