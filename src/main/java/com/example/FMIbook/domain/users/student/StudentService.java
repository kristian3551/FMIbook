package com.example.FMIbook.domain.users.student;

import com.example.FMIbook.domain.policy.DeletePolicy;
import com.example.FMIbook.domain.policy.UpdatePolicy;
import com.example.FMIbook.domain.policy.exception.CannotDeleteException;
import com.example.FMIbook.domain.policy.exception.CannotUpdateException;
import com.example.FMIbook.domain.users.student.exception.StudentNotFoundException;
import com.example.FMIbook.utils.ServiceUtils;
import com.example.FMIbook.domain.users.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public StudentService(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<StudentDTO> findAll(Integer limit, Integer offset, String sort) {
        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "name", Sort.Direction.ASC);
        Page<Student> students = studentRepository.findAll(page);
        return students.getContent().stream().map(StudentDTO::serializeLightweight).toList();
    }

    public StudentDTO getOne(UUID id) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        return StudentDTO.serializeFromEntity(student);
    }

    public StudentDTO addOne(Student student, User user) {
        student.setPassword(this.passwordEncoder.encode(student.getPassword()));
        studentRepository.save(student);
        return StudentDTO.serializeFromEntity(student);
    }

    public StudentDTO update(UUID id, @Valid StudentDTO studentDto, User user) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);

        if (!UpdatePolicy.canModifyStudent(user, student)) {
            throw new CannotUpdateException();
        }

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
            student.setGroup(studentDto.getGroup());
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

    public void delete(UUID id, User user) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);

        if (!DeletePolicy.canDeleteStudent(user, student)) {
            throw new CannotDeleteException();
        }

        studentRepository.delete(student);
    }
}
