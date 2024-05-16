package com.example.FMIbook.domain.course;

import com.example.FMIbook.domain.course.achievement.Achievement;
import com.example.FMIbook.domain.course.achievement.AchievementDTO;
import com.example.FMIbook.domain.course.achievement.AchievementRepository;
import com.example.FMIbook.domain.course.achievement.AchievementRequestDTO;
import com.example.FMIbook.domain.course.achievement.exception.AchievementNotFoundException;
import com.example.FMIbook.domain.course.exception.CourseNotFoundException;
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
import com.example.FMIbook.domain.course.task.Task;
import com.example.FMIbook.domain.course.task.TaskRepository;
import com.example.FMIbook.domain.course.task.TaskRequestDTO;
import com.example.FMIbook.domain.course.task.TaskResponseDTO;
import com.example.FMIbook.domain.course.task.exception.TaskNotFoundException;
import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.domain.department.DepartmentRepository;
import com.example.FMIbook.domain.department.exception.DepartmentNotFoundException;
import com.example.FMIbook.domain.policy.CreatePolicy;
import com.example.FMIbook.domain.policy.DeletePolicy;
import com.example.FMIbook.domain.policy.ReadPolicy;
import com.example.FMIbook.domain.policy.UpdatePolicy;
import com.example.FMIbook.domain.policy.exception.CannotCreateException;
import com.example.FMIbook.domain.policy.exception.CannotDeleteException;
import com.example.FMIbook.domain.policy.exception.CannotReadException;
import com.example.FMIbook.domain.policy.exception.CannotUpdateException;
import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.student.StudentRepository;
import com.example.FMIbook.domain.users.student.exception.StudentNotFoundException;
import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.domain.users.teacher.TeacherRepository;
import com.example.FMIbook.utils.ServiceUtils;
import com.example.FMIbook.domain.users.user.User;
import com.example.FMIbook.domain.users.user.UserRepository;
import com.example.FMIbook.domain.users.user.exception.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private final AchievementRepository achievementRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository,
                         StudentRepository studentRepository,
                         DepartmentRepository departmentRepository,
                         TeacherRepository teacherRepository,
                         SectionRepository sectionRepository,
                         CoursePostRepository coursePostRepository,
                         UserRepository userRepository,
                         AchievementRepository achievementRepository,
                         TaskRepository taskRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.teacherRepository = teacherRepository;
        this.sectionRepository = sectionRepository;
        this.coursePostRepository = coursePostRepository;
        this.userRepository = userRepository;
        this.achievementRepository = achievementRepository;
        this.taskRepository = taskRepository;
    }

    public List<CourseDTO> findAll(Integer limit, Integer offset, String sort, String search) {
        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "name", Sort.Direction.ASC);
        Page<Course> courses = null;
        if (search == null) {
            courses = courseRepository.findAll(page);
        }
        else {
            courses = courseRepository.findByNameIgnoreCaseContaining(search, page);
        }
        return courses.getContent().stream().map(CourseDTO::serializeLightweight).toList();
    }

    public CourseDTO getOne(UUID id, User user) {
        Optional<Course> course = courseRepository.findById(id);
        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }

        if (!ReadPolicy.canReadCourse(user, course.get())) {
            throw new CannotReadException();
        }

        return CourseDTO.serializeFromEntity(course.get());
    }

    public CourseDTO addOne(CourseRequestDTO courseDto, User user) {
        List<Student> students = courseDto.getStudents() != null
                ? studentRepository.findAllById(courseDto.getStudents())
                : new ArrayList<>();
        List<Teacher> teachers = courseDto.getTeachers() != null
                ? teacherRepository.findAllById(courseDto.getTeachers())
                : new ArrayList<>();
        Optional<Department> department = courseDto.getDepartment() != null
                ? departmentRepository.findById(courseDto.getDepartment())
                : Optional.empty();

        Course course = Course.builder()
                .name(courseDto.getName())
                .year(courseDto.getYear())
                .semester(courseDto.getSemester())
                .category(courseDto.getCategory())
                .type(courseDto.getType())
                .description(courseDto.getDescription())
                .students(students)
                .teachers(teachers)
                .department(department.orElse(null))
                .isPublic(courseDto.getIsPublic())
                .build();

        if (!CreatePolicy.canCreateCourse(user, course)) {
            throw new CannotCreateException();
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

    public CourseDTO update(UUID id, CourseRequestDTO courseDto, User user) {
        Course course = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);

        if (!UpdatePolicy.canModifyCourse(user, course)) {
            throw new CannotUpdateException();
        }

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

        if (courseDto.getIsPublic() != null) {
            course.setPublic(courseDto.getIsPublic());
        }

        if (courseDto.getDepartment() != null) {
            Optional<Department> departmentOpt = departmentRepository.findById(courseDto.getDepartment());

            if (departmentOpt.isEmpty()) {
                throw new DepartmentNotFoundException();
            }

            course.setDepartment(departmentOpt.get());
        }

        if (courseDto.getStudents() != null) {
            List<Student> students = studentRepository.findAllById(courseDto.getStudents());
            course.setStudents(students);
        }

        if (courseDto.getTeachers() != null) {
            List<Teacher> teachers = teacherRepository.findAllById(courseDto.getTeachers());
            course.setTeachers(teachers);
        }

        courseRepository.save(course);
        return CourseDTO.serializeFromEntity(course);
    }

    public SectionDTO addSection(SectionRequestDTO sectionDto, User user) {
        Course course = courseRepository.findById(sectionDto.getCourseId()).orElseThrow(CourseNotFoundException::new);

        Section section = Section.builder()
                .name(sectionDto.getName())
                .priority(sectionDto.getPriority())
                .course(course)
                .build();

        if (!CreatePolicy.canCreateSection(user, section)) {
            throw new CannotCreateException();
        }

        sectionRepository.save(section);
        return SectionDTO.serializeFromEntity(section);
    }

    public void deleteSection(UUID id, User user) {
        Optional<Section> section = sectionRepository.findById(id);

        if (section.isEmpty()) {
            throw new SectionNotFoundException();
        }

        if (!DeletePolicy.canDeleteSection(user, section.get())) {
            throw new CannotDeleteException();
        }

        sectionRepository.delete(section.get());
    }

    public SectionDTO updateSection(UUID id, SectionRequestDTO sectionDto, User user) {
        Optional<Section> sectionOpt = sectionRepository.findById(id);

        if (sectionOpt.isEmpty()) {
            throw new SectionNotFoundException();
        }

        Section section = sectionOpt.get();

        if (!UpdatePolicy.canModifySection(user, section)) {
            throw new CannotUpdateException();
        }

        if (sectionDto.getName() != null) {
            section.setName(sectionDto.getName());
        }

        if (sectionDto.getPriority() != null) {
            section.setPriority(sectionDto.getPriority());
        }

        sectionRepository.save(section);
        return SectionDTO.serializeFromEntity(section);
    }

    public void delete(UUID id, User user) {
        Course course = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);

        if (!DeletePolicy.canDeleteCourse(user, course)) {
            throw new CannotDeleteException();
        }

        courseRepository.delete(course);
    }

    public List<PostDTO> findAllPostsByCourse(UUID id, Integer limit, Integer offset, String sort, User user) {
        Course course = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);

        if (!ReadPolicy.canReadCourse(user, course)) {
            throw new CannotReadException();
        }

        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "created_at", Sort.Direction.DESC);
        Page<CoursePost> posts = coursePostRepository.findAllByCourse(id, page);
        return posts.getContent().stream().map(PostDTO::serializeLightweight).toList();
    }

    public PostDTO getOnePost(UUID id, User user) {
        CoursePost post = coursePostRepository.findById(id).orElseThrow(PostNotFoundException::new);

        if (!ReadPolicy.canReadPost(user, post)) {
            throw new CannotReadException();
        }

        return PostDTO.serializeFromEntity(post);
    }

    public PostDTO addPost(PostRequestDTO postDto, User loggedUser) {
        CoursePost post = new CoursePost(postDto.getTitle(), postDto.getDescription(), postDto.getRate());

        Course course = courseRepository.findById(postDto.getCourseId()).orElseThrow(CourseNotFoundException::new);
        post.setCourse(course);

        User user = userRepository.findById(postDto.getUserId()).orElseThrow(UserNotFoundException::new);
        post.setUser(user);

        if (postDto.getParentId() != null) {
            Optional<CoursePost> parentPost = coursePostRepository.findById(postDto.getParentId());
            if (parentPost.isEmpty()) {
                throw new PostNotFoundException();
            }
            post.setParentPost(parentPost.get());
        }

        if (!CreatePolicy.canCreatePost(loggedUser, post)) {
            throw new CannotCreateException();
        }

        coursePostRepository.save(post);
        return PostDTO.serializeFromEntity(post);
    }

    public PostDTO updatePost(UUID id, PostRequestDTO postDto, User user) {
        CoursePost post = coursePostRepository.findById(id).orElseThrow(PostNotFoundException::new);

        if (postDto.getTitle() != null) {
            post.setTitle(postDto.getTitle());
        }

        if (postDto.getDescription() != null) {
            post.setDescription(postDto.getDescription());
        }

        if (postDto.getRate() != null) {
            post.setRate(postDto.getRate());
        }

        if (!UpdatePolicy.canModifyPost(user, post)) {
            throw new CannotUpdateException();
        }

        coursePostRepository.save(post);
        return PostDTO.serializeFromEntity(post);
    }

    public void deletePost(UUID id, User user) {
        CoursePost post = coursePostRepository.findById(id).orElseThrow(PostNotFoundException::new);

        if (!DeletePolicy.canDeletePost(user, post)) {
            throw new CannotDeleteException();
        }

        coursePostRepository.delete(post);
    }

    public List<AchievementDTO> findAllAchievementsByCourse(
            UUID courseId,
            Integer limit,
            Integer offset,
            String sort,
            User user
    ) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);

        if (!ReadPolicy.canReadCourse(user, course)) {
            throw new CannotReadException();
        }

        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "name", Sort.Direction.ASC);
        List<Achievement> achievements = achievementRepository.findByCourse(courseId, page);
        return achievements.stream().map(achievement -> AchievementDTO.serializeLightweight(achievement, true, false)).toList();
    }

    public AchievementDTO addAchievement(AchievementRequestDTO achievementDto, User user) {
        Student student = studentRepository.findById(achievementDto.getStudentId()).orElseThrow(StudentNotFoundException::new);
        Course course = courseRepository.findById(achievementDto.getCourseId()).orElseThrow(CourseNotFoundException::new);

        Achievement achievement = Achievement.builder()
                .name(achievementDto.getName())
                .description(achievementDto.getDescription())
                .course(course)
                .student(student)
                .build();

        if (!CreatePolicy.canCreateAchievement(user, achievement)) {
            throw new CannotCreateException();
        }

        achievementRepository.save(achievement);
        return AchievementDTO.serializeFromEntity(achievement);
    }

    public AchievementDTO updateAchievement(UUID id, AchievementRequestDTO achievementDto, User user) {
        Achievement achievement = achievementRepository.findById(id).orElseThrow(AchievementNotFoundException::new);

        if (achievementDto.getName() != null) {
            achievement.setName(achievementDto.getName());
        }

        if (achievementDto.getDescription() != null) {
            achievement.setDescription(achievementDto.getDescription());
        }

        if (!UpdatePolicy.canModifyAchievement(user, achievement)) {
            throw new CannotUpdateException();
        }

        achievementRepository.save(achievement);
        return AchievementDTO.serializeFromEntity(achievement);
    }

    public void deleteAchievement(UUID id, User user) {
        Achievement achievement = achievementRepository.findById(id).orElseThrow(AchievementNotFoundException::new);

        if (!DeletePolicy.canDeleteAchievement(user, achievement)) {
            throw new CannotDeleteException();
        }

        achievementRepository.delete(achievement);
    }

    public List<TaskResponseDTO> findAllTasksByCourse(UUID courseId,
                                                       Integer limit,
                                                       Integer offset,
                                                       String sort,
                                                      User user) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);

        if (!ReadPolicy.canReadCourse(user, course)) {
            throw new CannotReadException();
        }

        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "created_at", Sort.Direction.DESC);
        Page<Task> tasks = taskRepository.findAllByCourse(courseId, page);
        return tasks.getContent().stream().map(TaskResponseDTO::serializeLightweight).toList();
    }

    public TaskResponseDTO findOneTask(UUID id, User user) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        if (!ReadPolicy.canReadTask(user, task)) {
            throw new CannotReadException();
        }

        return TaskResponseDTO.serializeFromEntity(task);
    }

    public TaskResponseDTO addTask(TaskRequestDTO taskDto, User user) {
        Course course = courseRepository.findById(taskDto.getCourseId()).orElseThrow(CourseNotFoundException::new);

        Task task = Task
                .builder()
                .name(taskDto.getName())
                .startDate(LocalDateTime.parse(taskDto.getStartDate()))
                .endDate(LocalDateTime.parse(taskDto.getEndDate()))
                .description(taskDto.getDescription())
                .course(course)
                .type(taskDto.getType())
                .build();

        if (!CreatePolicy.canCreateTask(user, task)) {
            throw new CannotCreateException();
        }

        taskRepository.save(task);
        return TaskResponseDTO.serializeFromEntity(task);
    }

    public TaskResponseDTO updateTask(UUID id, TaskRequestDTO taskDto, User user) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        if (!UpdatePolicy.canModifyTask(user, task)) {
            throw new CannotUpdateException();
        }

        if (taskDto.getName() != null) {
            task.setName(taskDto.getName());
        }

        if (taskDto.getStartDate() != null) {
            task.setStartDate(LocalDateTime.parse(taskDto.getStartDate()));
        }

        if (taskDto.getEndDate() != null) {
            task.setEndDate(LocalDateTime.parse(taskDto.getEndDate()));
        }

        if (taskDto.getDescription() != null) {
            task.setDescription(taskDto.getDescription());
        }

        if (taskDto.getType() != null) {
            task.setType(taskDto.getType());
        }

        taskRepository.save(task);
        return TaskResponseDTO.serializeFromEntity(task);
    }

    public void deleteTask(UUID id, User user) {
        Task task = taskRepository.findById(id).orElseThrow(TaskNotFoundException::new);

        if (!DeletePolicy.canDeleteTask(user, task)) {
            throw new CannotDeleteException();
        }

        taskRepository.delete(task);
    }
}
