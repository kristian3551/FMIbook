package com.example.FMIbook.domain.course;

import com.example.FMIbook.domain.course.exception.CourseNotFoundException;
import com.example.FMIbook.domain.student.Student;
import com.example.FMIbook.domain.student.StudentDTO;
import com.example.FMIbook.domain.student.StudentRepository;
import com.example.FMIbook.domain.student.grade.Grade;
import com.example.FMIbook.domain.student.grade.GradeRepository;
import com.example.FMIbook.domain.teacher.Teacher;
import com.example.FMIbook.domain.teacher.TeacherRepository;
import com.example.FMIbook.server.student.StudentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final TeacherRepository teacherRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, GradeRepository gradeRepository, TeacherRepository teacherRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<CourseDTO> findAll(Integer limit, Integer offset, String sort) {
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

        Page<Course> courses = courseRepository.findAll(page);
        return courses.getContent().stream().map(CourseDTO::serializeFromEntity).toList();
    }

    public CourseDTO getOne(UUID id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }

        return CourseDTO.serializeFromEntity(course.get());
    }

    public CourseDTO addOne(Course course) {
        courseRepository.save(course);
        return CourseDTO.serializeFromEntity(course);
    }

    public CourseDTO update(UUID id, CourseDTO courseDto) {
        Optional<Course> courseOpt = courseRepository.findById(id);

        if (courseOpt.isEmpty()) {
            throw new CourseNotFoundException();
        }

        Course course = courseOpt.get();

        if (courseDto.getName() != null) {
            course.setName(courseDto.getName());
        }

        if (courseDto.getYear() != null) {
            course.setYear(courseDto.getYear());
        }

        if (courseDto.getSemester() != null) {
            course.setSemester(courseDto.getSemester());
        }

        if (courseDto.getCategory() != null) {
            course.setCategory(courseDto.getCategory());
        }

        if (courseDto.getType() != null) {
            course.setType(courseDto.getType());
        }

        if (courseDto.getDescription() != null) {
            course.setDescription(courseDto.getDescription());
        }

        courseRepository.save(course);
        return CourseDTO.serializeFromEntity(course);
    }

    public CourseDTO setStudents(UUID id, List<UUID> gradeIds) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }
        List<Student> students = studentRepository.findAllById(gradeIds);
        course.get().setStudents(students);
        courseRepository.save(course.get());
        return CourseDTO.serializeFromEntity(course.get());
    }

    public CourseDTO setGrades(UUID id, List<UUID> gradeIds) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }
        List<Grade> grades = gradeRepository.findAllById(gradeIds);
        course.get().setGrades(grades);
        courseRepository.save(course.get());
        return CourseDTO.serializeFromEntity(course.get());
    }

    public CourseDTO setTeachers(UUID id, List<UUID> teacherIds) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }
        List<Teacher> teachers = teacherRepository.findAllById(teacherIds);
        course.get().setTeachers(teachers);
        courseRepository.save(course.get());
        return CourseDTO.serializeFromEntity(course.get());
    }

    public void delete(UUID id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }

        courseRepository.delete(course.get());
    }
}
