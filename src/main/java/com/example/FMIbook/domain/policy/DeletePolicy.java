package com.example.FMIbook.domain.policy;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.achievement.Achievement;
import com.example.FMIbook.domain.course.posts.CoursePost;
import com.example.FMIbook.domain.course.section.Section;
import com.example.FMIbook.domain.course.task.Task;
import com.example.FMIbook.domain.course.task.submission.Submission;
import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.domain.grade.Grade;
import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.domain.users.user.Permission;
import com.example.FMIbook.domain.users.user.User;

public class DeletePolicy {
    private static boolean hasAdminDeleteRights(User user) {
        return user.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.toString()
                        .equals(Permission.ADMIN_DELETE.getPermission()));
    }

    public static boolean canDeleteStudent(User user, Student student) {
        return user.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.toString()
                        .equals(Permission.ADMIN_DELETE.getPermission()));
    }

    public static boolean canDeleteTeacher(User user, Teacher teacher) {
        return user.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.toString()
                        .equals(Permission.ADMIN_DELETE.getPermission()));
    }

    public static boolean canDeleteCourse(User user, Course course) {
        return hasAdminDeleteRights(user);
    }

    public static boolean canDeleteAchievement(User user, Achievement achievement) {
        return UpdatePolicy.canModifyCourse(user, achievement.getCourse());
    }

    public static boolean canDeletePost(User user, CoursePost post) {
        if (hasAdminDeleteRights(user)) {
            return true;
        }

        if (UpdatePolicy.canModifyCourse(user, post.getCourse())) {
            return true;
        }

        return user.getId().equals(post.getUser().getId());
    }

    public static boolean canDeleteSection(User user, Section section) {
        return UpdatePolicy.canModifyCourse(user, section.getCourse());
    }

    public static boolean canDeleteTask(User user, Task task) {
        return UpdatePolicy.canModifyCourse(user, task.getCourse());
    }

    public static boolean canDeleteDepartment(User user, Department department) {
        return hasAdminDeleteRights(user);
    }

    public static boolean canDeleteGrade(User user, Grade grade) {
        return UpdatePolicy.canModifyCourse(user, grade.getCourse());
    }

    public static boolean canDeleteSubmission(User user, Submission submission) {
        if (hasAdminDeleteRights(user)) {
            return true;
        }

        return user.getId().equals(submission.getStudent().getId());
    }
}
