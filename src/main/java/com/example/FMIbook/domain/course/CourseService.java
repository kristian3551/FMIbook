package com.example.FMIbook.domain.course;

import com.example.FMIbook.domain.course.exception.CourseNotFoundException;
import com.example.FMIbook.domain.course.section.Section;
import com.example.FMIbook.domain.course.section.SectionDTO;
import com.example.FMIbook.domain.course.section.SectionRepository;
import com.example.FMIbook.domain.course.section.SectionRequestDTO;
import com.example.FMIbook.domain.course.section.exception.SectionNotFoundException;
import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.domain.department.DepartmentRepository;
import com.example.FMIbook.domain.department.exception.DepartmentNotFoundException;
import com.example.FMIbook.domain.student.Student;
import com.example.FMIbook.domain.student.StudentRepository;
import com.example.FMIbook.domain.teacher.Teacher;
import com.example.FMIbook.domain.teacher.TeacherRepository;
import jakarta.transaction.Transactional;
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
    private final DepartmentRepository departmentRepository;
    private final TeacherRepository teacherRepository;
    private final SectionRepository sectionRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, DepartmentRepository departmentRepository, TeacherRepository teacherRepository, SectionRepository sectionRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.sectionRepository = sectionRepository;
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

    @Transactional
    public CourseDTO addOne(CourseRequestDTO courseDto) {
        List<Student> students = courseDto.getStudents() != null
                ? studentRepository.findAllById(courseDto.getStudents())
                : new ArrayList<>();
        List<Teacher> teachers = courseDto.getTeachers() != null
                ? teacherRepository.findAllById(courseDto.getTeachers())
                : new ArrayList<>();

        Course course = new Course(
                courseDto.getName(),
                courseDto.getYear(),
                courseDto.getSemester(),
                courseDto.getCategory(),
                courseDto.getType(),
                courseDto.getDescription(),
                students,
                teachers
        );
        if (courseDto.getDepartment() != null) {
            Optional<Department> departmentOpt = departmentRepository.findById(courseDto.getDepartment());

            if (departmentOpt.isEmpty()) {
                throw new DepartmentNotFoundException();
            }
            course.setDepartment(departmentOpt.get());
        }
        courseRepository.save(course);
        return CourseDTO.serializeFromEntity(course);
    }

    @Transactional
    public CourseDTO update(UUID id, CourseRequestDTO courseDto) {
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

        if (courseDto.getStudents() != null) {
            List<Student> students = studentRepository.findAllById(courseDto.getStudents());
            course.setStudents(students);
        }

        if (courseDto.getTeachers() != null) {
            List<Teacher> teachers = teacherRepository.findAllById(courseDto.getTeachers());
            course.setTeachers(teachers);
        }

        if (courseDto.getDepartment() != null) {
            Optional<Department> departmentOpt = departmentRepository.findById(courseDto.getDepartment());

            if (departmentOpt.isEmpty()) {
                throw new DepartmentNotFoundException();
            }

            course.setDepartment(departmentOpt.get());
        }

        courseRepository.save(course);
        return CourseDTO.serializeFromEntity(course);
    }

    public SectionDTO addSection(SectionRequestDTO sectionDto) {
        Optional<Course> course = courseRepository.findById(sectionDto.getCourseId());

        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }

        Section section = new Section(sectionDto.getName(), sectionDto.getPriority(), course.get());
        sectionRepository.save(section);
        return SectionDTO.serializeFromEntity(section);
    }

    public void deleteSection(UUID id) {
        Optional<Section> section = sectionRepository.findById(id);

        if (section.isEmpty()) {
            throw new SectionNotFoundException();
        }

        sectionRepository.delete(section.get());
    }

    public SectionDTO updateSection(UUID id, SectionRequestDTO sectionDto) {
        Optional<Section> sectionOpt = sectionRepository.findById(id);

        if (sectionOpt.isEmpty()) {
            throw new SectionNotFoundException();
        }

        Section section = sectionOpt.get();

        if (sectionDto.getName() != null) {
            section.setName(sectionDto.getName());
        }

        if (sectionDto.getPriority() != null) {
            section.setPriority(sectionDto.getPriority());
        }

        sectionRepository.save(section);
        return SectionDTO.serializeFromEntity(section);
    }

    public void delete(UUID id) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }

        courseRepository.delete(course.get());
    }
}
