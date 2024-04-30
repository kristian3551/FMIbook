package com.example.FMIbook.domain.teacher;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.course.CourseRepository;
import com.example.FMIbook.domain.course.exception.CourseNotFoundException;
import com.example.FMIbook.domain.teacher.exception.TeacherNotFoundException;
import com.example.FMIbook.server.student.StudentNotFoundException;
import jakarta.transaction.Transactional;
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
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
    }

    public List<TeacherDTO> findAll(
            Integer limit,
            Integer offset,
            String sort
    ) {
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

        if (teacherDto.getPassword() != null) {
            teacher.setPassword(teacherDto.getPassword());
        }

        teacherRepository.save(teacher);
        return TeacherDTO.serializeFromEntity(teacher);
    }

    public TeacherDTO setCourses(UUID id, List<UUID> courseIds) {
        Optional<Teacher> teacher = teacherRepository.findById(id);
        if (teacher.isEmpty()) {
            throw new StudentNotFoundException();
        }
        List<Course> courses = courseRepository.findAllById(courseIds);
        teacher.get().setCourses(courses);
        teacherRepository.save(teacher.get());
        return TeacherDTO.serializeFromEntity(teacher.get());
    }

    public void delete(UUID id) {
        Optional<Teacher> teacher = teacherRepository.findById(id);

        if (teacher.isEmpty()) {
            throw new TeacherNotFoundException();
        }

        teacherRepository.delete(teacher.get());
    }
}
