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
        return departments.getContent().stream().map(DepartmentDTO::serializeFromEntity).toList();
    }

    public DepartmentDTO getOne(UUID id) {
        Optional<Department> department = departmentRepository.findById(id);

        if (department.isEmpty()) {
            throw new DepartmentNotFoundException();
        }

        departmentRepository.save(department.get());
        return DepartmentDTO.serializeFromEntity(department.get());
    }

    public DepartmentDTO addOne(Department department) {
        departmentRepository.save(department);
        return DepartmentDTO.serializeFromEntity(department);
    }

    public DepartmentDTO update(UUID id, DepartmentDTO departmentDto) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);

        if (departmentOpt.isEmpty()) {
            throw new DepartmentNotFoundException();
        }

        Department department = departmentOpt.get();

        if (departmentDto.getName() != null) {
            department.setName(departmentDto.getName());
        }

        departmentRepository.save(department);
        return DepartmentDTO.serializeFromEntity(department);
    }

    public void delete(UUID id) {
        Optional<Department> departmentOpt = departmentRepository.findById(id);

        if (departmentOpt.isEmpty()) {
            throw new DepartmentNotFoundException();
        }

        departmentRepository.delete(departmentOpt.get());
    }
}
