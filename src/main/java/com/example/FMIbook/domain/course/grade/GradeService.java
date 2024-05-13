package com.example.FMIbook.domain.course.grade;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.CourseRepository;
import com.example.FMIbook.domain.course.exception.CourseNotFoundException;
import com.example.FMIbook.domain.policy.ReadPolicy;
import com.example.FMIbook.domain.policy.exception.CannotReadException;
import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.student.StudentRepository;
import com.example.FMIbook.domain.course.grade.exception.GradeNotFoundException;
import com.example.FMIbook.domain.users.student.exception.StudentNotFoundException;
import com.example.FMIbook.domain.users.user.User;
import com.example.FMIbook.utils.ServiceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GradeService {
    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public GradeService(GradeRepository gradeRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    public List<GradeDTO> findAllByCourse(
            UUID courseId,
            User user,
            Integer limit,
            Integer offset,
            String sort,
            UUID studentId
    ) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }
        if (!ReadPolicy.canReadCourse(user, course.get())) {
            throw new CannotReadException();
        }

        Pageable page = ServiceUtils.buildOrder(limit, offset, sort, "created_at", Sort.Direction.DESC);
        Page<Grade> grades;

        if (user.isAdmin() || user.isTeacher()) {
            if (studentId != null) {
                grades = gradeRepository.findByCourseAndStudent(courseId, studentId, page);
            }
            else {
                grades = gradeRepository.findByCourse(courseId, page);
            }
        }
        else {
            grades = gradeRepository.findByCourseAndStudent(courseId, user.getId(), page);
        }
        return grades.getContent().stream().map(GradeDTO::serializeFromEntity).toList();
    }

    public GradeDTO getOne(UUID id) {
        Optional<Grade> grade = gradeRepository.findById(id);
        if (grade.isEmpty()) {
            throw new GradeNotFoundException();
        }

        return GradeDTO.serializeFromEntity(grade.get());
    }

    public GradeDTO addOne(GradeRequestDTO gradeDto) {
        Optional<Student> student = studentRepository.findById(gradeDto.getStudentId());
        Optional<Course> course = courseRepository.findById(gradeDto.getCourseId());

        if (student.isEmpty()) {
            throw new StudentNotFoundException();
        }
        if (course.isEmpty()) {
            throw new CourseNotFoundException();
        }
        Grade grade = Grade.builder()
                .student(student.get())
                .course(course.get())
                .percentage(gradeDto.getPercentage())
                .grade(gradeDto.getGrade())
                .isFinal(gradeDto.getIsFinal())
                .build();
        gradeRepository.save(grade);
        return GradeDTO.serializeFromEntity(grade);
    }

    public GradeDTO update(UUID id, GradeRequestDTO gradeDto) {
        Optional<Grade> gradeOpt = gradeRepository.findById(id);
        if (gradeOpt.isEmpty()) {
            throw new GradeNotFoundException();
        }

        Grade grade = gradeOpt.get();

        if (gradeDto.getPercentage() != null) {
            grade.setPercentage(gradeDto.getPercentage());
        }

        if (gradeDto.getGrade() != null) {
            grade.setGrade(gradeDto.getGrade());
        }

        if (gradeDto.getCourseId() != null) {
            Optional<Course> course = courseRepository.findById(gradeDto.getCourseId());
            if (course.isEmpty()) {
                throw new CourseNotFoundException();
            }
            grade.setCourse(course.get());
        }

        if (gradeDto.getStudentId() != null) {
            Optional<Student> student = studentRepository.findById(gradeDto.getStudentId());
            if (student.isEmpty()) {
                throw new StudentNotFoundException();
            }
            grade.setStudent(student.get());
        }

        if (gradeDto.getIsFinal() != null) {
            grade.setIsFinal(gradeDto.getIsFinal());
        }

        gradeRepository.save(grade);
        return GradeDTO.serializeFromEntity(grade);
    }

    public void delete(UUID id) {
        Optional<Grade> grade = gradeRepository.findById(id);
        if (grade.isEmpty()) {
            throw new GradeNotFoundException();
        }
        gradeRepository.delete(grade.get());
    }
}
