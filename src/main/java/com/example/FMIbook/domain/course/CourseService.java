package com.example.FMIbook.domain.course;

import com.example.FMIbook.domain.course.exception.CourseNotFoundException;
import com.example.FMIbook.domain.course.exception.CourseUpdateException;
import com.example.FMIbook.domain.course.posts.CoursePost;
import com.example.FMIbook.domain.course.posts.CoursePostRepository;
import com.example.FMIbook.domain.course.posts.PostDTO;
import com.example.FMIbook.domain.course.posts.PostRequestDTO;
import com.example.FMIbook.domain.course.posts.exception.PostNotFoundException;
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
import com.example.FMIbook.domain.student.grade.Grade;
import com.example.FMIbook.domain.teacher.Teacher;
import com.example.FMIbook.domain.teacher.TeacherRepository;
import com.example.FMIbook.utils.user.User;
import com.example.FMIbook.utils.user.UserRepository;
import com.example.FMIbook.utils.user.exception.UserNotFoundException;
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
    private final CoursePostRepository coursePostRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         StudentRepository studentRepository,
                         DepartmentRepository departmentRepository,
                         TeacherRepository teacherRepository,
                         SectionRepository sectionRepository,
                         CoursePostRepository coursePostRepository,
                         UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.sectionRepository = sectionRepository;
        this.coursePostRepository = coursePostRepository;
        this.userRepository = userRepository;
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

        for (Grade grade : course.getGrades()) {
            Course gradeCourse = grade.getCourse();
        }

        for (Section section : course.getSections()) {
            Course sectionCourse = section.getCourse();
        }



        if (course.getDepartment() != null) {
            List<Course> courses = course.getDepartment().getCourses();
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

    public List<PostDTO> findAllPostsByCourse(UUID id, Integer limit, Integer offset, String sort) {
        Optional<Course> courseOpt = courseRepository.findById(id);

        if (courseOpt.isEmpty()) {
            throw new CourseNotFoundException();
        }

        Sort.Direction orderOptions = Sort.Direction.ASC;
        String sortField = "created_at";
        if (sort != null) {
            sortField = sort.charAt(0) == '-' ? sort.substring(1) : sort;
            orderOptions = sort.charAt(0) == '-' ? Sort.Direction.DESC : Sort.Direction.ASC;
        }
        int pageNumber = offset == null ? 0 : offset;
        int pageSize = limit == null ? 5 : limit;

        Pageable page = PageRequest.of(pageNumber, pageSize,
                orderOptions == Sort.Direction.ASC ? Sort.by(sortField).ascending() : Sort.by(sortField).descending());

        Page<CoursePost> posts = coursePostRepository.findAllByCourse(id, page);
        return posts.getContent().stream().map(PostDTO::serializeFromEntity).toList();
    }

    public PostDTO getOnePost(UUID id) {
        Optional<CoursePost> post = coursePostRepository.findById(id);

        if (post.isEmpty()) {
            throw new PostNotFoundException();
        }

        return PostDTO.serializeFromEntity(post.get());
    }

    public PostDTO addPost(PostRequestDTO postDto) {
        CoursePost post = new CoursePost(postDto.getTitle(), postDto.getDescription(), postDto.getRate());

        Optional<Course> courseOpt = courseRepository.findById(postDto.getCourseId());

        if (courseOpt.isEmpty()) {
            throw new CourseNotFoundException();
        }

        post.setCourse(courseOpt.get());

        Optional<User> user = userRepository.findById(postDto.getUserId());

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        post.setUser(user.get());

        if (postDto.getParentId() != null) {
            Optional<CoursePost> parentPost = coursePostRepository.findById(postDto.getParentId());
            if (parentPost.isEmpty()) {
                throw new PostNotFoundException();
            }
            post.setParentPost(parentPost.get());
        }

        coursePostRepository.save(post);
        return PostDTO.serializeFromEntity(post);
    }

    public PostDTO updatePost(UUID id, PostRequestDTO postDto) {
        Optional<CoursePost> postOpt = coursePostRepository.findById(id);

        if (postOpt.isEmpty()) {
            throw new PostNotFoundException();
        }

        CoursePost post = postOpt.get();

        if (postDto.getTitle() != null) {
            post.setTitle(postDto.getTitle());
        }

        if (postDto.getDescription() != null) {
            post.setDescription(postDto.getDescription());
        }

        if (postDto.getRate() != null) {
            post.setRate(postDto.getRate());
        }

        coursePostRepository.save(post);
        return PostDTO.serializeFromEntity(post);
    }

    public void deletePost(UUID id) {
        Optional<CoursePost> postOpt = coursePostRepository.findById(id);

        if (postOpt.isEmpty()) {
            throw new PostNotFoundException();
        }

        coursePostRepository.delete(postOpt.get());
    }
}
