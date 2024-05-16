package com.example.FMIbook.domain.policy;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.achievement.Achievement;
import com.example.FMIbook.domain.course.posts.CoursePost;
import com.example.FMIbook.domain.course.section.Section;
import com.example.FMIbook.domain.course.task.Task;
import com.example.FMIbook.domain.course.task.submission.Submission;
import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.domain.course.grade.Grade;
import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.domain.users.user.Permission;
import com.example.FMIbook.domain.users.user.User;

public class UpdatePolicy {
    private static boolean hasAdminUpdateRights(User user) {
        return user.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.toString()
                        .equals(Permission.ADMIN_UPDATE.getPermission()));
    }

    public static boolean canModifyCourse(User user, Course course) {
        if (hasAdminUpdateRights(user)) {
            return true;
        }
        return course.getTeachers().stream().anyMatch(
                teacher -> teacher.getId().equals(user.getId()));
    }

    public static boolean canModifyStudent(User user, Student student) {
        if (hasAdminUpdateRights(user)) {
            return true;
        }

        return user.getId().equals(student.getId());
    }

    public static boolean canModifyTeacher(User user, Teacher teacher) {
        if (hasAdminUpdateRights(user)) {
            return true;
        }

        return user.getId().equals(teacher.getId());
    }

    public static boolean canModifyDepartment(User user, Department department) {
        return hasAdminUpdateRights(user);
    }

    public static boolean canModifySection(User user, Section section) {
        return canModifyCourse(user, section.getCourse());
    }

    public static boolean canModifyPost(User user, CoursePost post) {
        if (hasAdminUpdateRights(user)) {
            return true;
        }

        return user.getId().equals(post.getUser().getId());
    }

    public static boolean canModifyTask(User user, Task task) {
        return canModifyCourse(user, task.getCourse());
    }

    public static boolean canModifyAchievement(User user, Achievement achievement) {
        return canModifyCourse(user, achievement.getCourse());
    }

    public static boolean canModifyGrade(User user, Grade grade) {
        return canModifyCourse(user, grade.getCourse());
    }

    public static boolean canModifySubmission(User user, Submission submission) {
        return DeletePolicy.canDeleteSubmission(user, submission) ||
                UpdatePolicy.canModifyTask(user, submission.getTask());
    }
}
