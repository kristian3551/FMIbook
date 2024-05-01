package com.example.FMIbook.domain.student;

import com.example.FMIbook.server.student.StudentNotFoundException;
import com.example.FMIbook.utils.ServiceUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<StudentDTO> findAll(Integer limit, Integer offset, String sort) {
        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "name", Sort.Direction.ASC);
        Page<Student> students = studentRepository.findAll(page);
        return students.getContent().stream().map(StudentDTO::serializeFromEntity).toList();
    }

    public StudentDTO getOne(UUID id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException();
        }

        return StudentDTO.serializeFromEntity(student.get());
    }

    public StudentDTO addOne(Student student) {
        studentRepository.save(student);
        return StudentDTO.serializeFromEntity(student);
    }

    public StudentDTO update(UUID id, @Valid StudentDTO studentDto) {
        Optional<Student> studentOpt = studentRepository.findById(id);

        if (studentOpt.isEmpty()) {
            throw new StudentNotFoundException();
        }

        Student student = studentOpt.get();

        if (studentDto.getName() != null) {
            student.setName(studentDto.getName());
        }

        if (studentDto.getFacultyNumber() != null) {
            student.setFacultyNumber(studentDto.getFacultyNumber());
        }

        if (studentDto.getEmail() != null) {
            student.setEmail(studentDto.getEmail());
        }

        if (studentDto.getSemester() != null) {
            student.setSemester(studentDto.getSemester());
        }

        if (studentDto.getGroup() != null) {
            student.setGroup(studentDto.getSemester());
        }

        if (studentDto.getDescription() != null) {
            student.setDescription(studentDto.getDescription());
        }

        if (studentDto.getDegree() != null) {
            student.setDegree(studentDto.getDegree());
        }

        studentRepository.save(student);

        return StudentDTO.serializeFromEntity(student);
    }

    public void delete(UUID id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException();
        }
        studentRepository.delete(student.get());
    }
}
