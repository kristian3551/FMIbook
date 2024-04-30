package com.example.FMIbook.domain.student;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.CourseRepository;
import com.example.FMIbook.domain.student.grade.Grade;
import com.example.FMIbook.domain.student.grade.GradeRepository;
import com.example.FMIbook.server.student.StudentNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<StudentDTO> findAll(Integer limit, Integer offset, String sort) {
        Sort.Direction orderOptions = Sort.Direction.ASC;
        String sortField = "name";
        if (sort != null) {
            sortField = sort.charAt(0) == '-' ? sort.substring(1) : sort;
            orderOptions = sort.charAt(0) == '-' ? Sort.Direction.DESC : Sort.Direction.ASC;
        }
        int pageNumber = offset == null ? 0 : offset;
        int pageSize = limit == null ? 5 : limit;

        Pageable page = PageRequest.of(pageNumber, pageSize,
                orderOptions == Sort.Direction.ASC ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

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
            student.setName(student.getName());
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

        if (studentDto.getPassword() != null) {
            student.setPassword(studentDto.getPassword());
        }

        studentRepository.save(student);

        return StudentDTO.serializeFromEntity(student);
    }

    public StudentDTO setCourses(UUID id, List<UUID> courseIds) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException();
        }
        List<Course> courses = courseRepository.findAllById(courseIds);
        student.get().setCourses(courses);
        studentRepository.save(student.get());
        return StudentDTO.serializeFromEntity(student.get());
    }

    public StudentDTO setGrades(UUID id, List<UUID> gradeIds) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException();
        }
        List<Grade> grades = gradeRepository.findAllById(gradeIds);
        student.get().setGrades(grades);
        studentRepository.save(student.get());
        return StudentDTO.serializeFromEntity(student.get());
    }

    public void delete(UUID id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException();
        }
        studentRepository.delete(student.get());
    }
}
