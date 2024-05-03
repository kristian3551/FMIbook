package com.example.FMIbook.domain.users.teacher;

import com.example.FMIbook.domain.users.teacher.exception.TeacherNotFoundException;
import com.example.FMIbook.utils.ServiceUtils;
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
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, PasswordEncoder passwordEncoder) {
        this.teacherRepository = teacherRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<TeacherDTO> findAll(
            Integer limit,
            Integer offset,
            String sort
    ) {
        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "name", Sort.Direction.ASC);
        Page<Teacher> teachers = teacherRepository.findAll(page);
        return teachers.getContent().stream().map(TeacherDTO::serializeFromEntity).toList();
    }

    public TeacherDTO getOne(UUID id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException();
        }

        return TeacherDTO.serializeFromEntity(teacher.get());
    }

    public TeacherDTO addOne(Teacher teacher) {
        teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));
        teacherRepository.save(teacher);
        return TeacherDTO.serializeFromEntity(teacher);
    }

    public TeacherDTO update(UUID id, TeacherDTO teacherDto) {
        Optional<Teacher> teacherOpt = teacherRepository.findById(id);

        if (teacherOpt.isEmpty()) {
            throw new TeacherNotFoundException();
        }

        Teacher teacher = teacherOpt.get();
        if (teacherDto.getName() != null) {
            teacher.setName(teacherDto.getName());
        }

        if (teacherDto.getEmail() != null) {
            teacher.setEmail(teacherDto.getEmail());
        }

        if (teacherDto.getDegree() != null) {
            teacher.setDegree(teacherDto.getDegree());
        }

        teacherRepository.save(teacher);
        return TeacherDTO.serializeFromEntity(teacher);
    }

    public void delete(UUID id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);

        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException();
        }

        teacherRepository.delete(teacher.get());
    }
}
