package com.example.FMIbook.domain.users.teacher;

import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.domain.department.DepartmentRepository;
import com.example.FMIbook.domain.department.exception.DepartmentNotFoundException;
import com.example.FMIbook.domain.users.teacher.exception.TeacherNotFoundException;
import com.example.FMIbook.utils.ServiceUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Data
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentRepository departmentRepository;

    public List<TeacherDTO> findAll(
            Integer limit,
            Integer offset,
            String sort
    ) {
        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "name", Sort.Direction.ASC);
        Page<Teacher> teachers = teacherRepository.findAll(page);
        return teachers.getContent().stream().map(TeacherDTO::serializeLightweight).toList();
    }

    public TeacherDTO getOne(UUID id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(TeacherNotFoundException::new);
        return TeacherDTO.serializeFromEntity(teacher);
    }

    public TeacherDTO addOne(Teacher teacher) {
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacherRepository.save(teacher);
        return TeacherDTO.serializeFromEntity(teacher);
    }

    public TeacherDTO update(UUID id, TeacherRequestDTO teacherDto) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(TeacherNotFoundException::new);

        if (teacherDto.getName() != null) {
            teacher.setName(teacherDto.getName());
        }

        if (teacherDto.getEmail() != null) {
            teacher.setEmail(teacherDto.getEmail());
        }

        if (teacherDto.getDegree() != null) {
            teacher.setDegree(teacherDto.getDegree());
        }

        if (teacherDto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(teacherDto.getDepartmentId()).orElseThrow(DepartmentNotFoundException::new);
            teacher.setDepartment(department);
        }

        teacherRepository.save(teacher);
        return TeacherDTO.serializeFromEntity(teacher);
    }

    public void delete(UUID id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(TeacherNotFoundException::new);
        teacherRepository.delete(teacher);
    }
}
