package com.example.FMIbook.server.departments;

import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.domain.department.DepartmentDTO;
import com.example.FMIbook.domain.department.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<DepartmentDTO> findAll(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) Integer offset,
            @RequestParam(required = false) String sort
    ) {
        return this.departmentService.findAll(limit, offset, sort);
    }

    @GetMapping("{departmentId}")
    public DepartmentDTO getOne(@PathVariable UUID departmentId) {
        return departmentService.getOne(departmentId);
    }

    @PostMapping
    public DepartmentDTO addOne(@RequestBody @Valid Department department) {
        return this.departmentService.addOne(department);
    }

    @PutMapping("{departmentId}")
    public  DepartmentDTO update(
            @PathVariable UUID departmentId,
            @RequestBody DepartmentDTO departmentDto
    ) {
        return departmentService.update(departmentId, departmentDto);
    }

    @DeleteMapping("{departmentId}")
    public void delete(@PathVariable UUID departmentId) {
        departmentService.delete(departmentId);
    }
}
