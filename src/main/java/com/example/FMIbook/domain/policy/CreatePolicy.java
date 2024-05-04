package com.example.FMIbook.domain.policy;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.achievement.Achievement;
import com.example.FMIbook.domain.course.course_material.CourseMaterial;
import com.example.FMIbook.domain.course.posts.CoursePost;
import com.example.FMIbook.domain.course.section.Section;
import com.example.FMIbook.domain.course.task.Task;
import com.example.FMIbook.domain.course.task.submission.Submission;
import com.example.FMIbook.domain.department.Department;
import com.example.FMIbook.domain.grade.Grade;
import com.example.FMIbook.domain.users.user.Permission;
import com.example.FMIbook.domain.users.user.User;

public class CreatePolicy {
    private static boolean hasAdminCreateRights(User user) {
        return user.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.toString()
                        .equals(Permission.ADMIN_CREATE.getPermission()));
    }

    public static boolean canCreateCourse(User user, Course course) {
        return hasAdminCreateRights(user);
    }

    public static boolean canCreateAchievement(User user, Achievement achievement) {
        return UpdatePolicy.canModifyCourse(user, achievement.getCourse());
    }

    public static boolean canCreatePost(User user, CoursePost post) {
        if (hasAdminCreateRights(user)) {
            return true;
        }

        return post.getCourse().getStudents()
                    .stream().anyMatch(student -> student.getId().equals(user.getId())) ||
                post.getCourse().getTeachers()
                    .stream().anyMatch(student -> student.getId().equals(user.getId()));
    }

    public static boolean canCreateSection(User user, Section section) {
        return UpdatePolicy.canModifyCourse(user, section.getCourse());
    }

    public static boolean canCreateTask(User user, Task task) {
        return UpdatePolicy.canModifyCourse(user, task.getCourse());
    }

    public static boolean canCreateDepartment(User user, Department department) {
        return hasAdminCreateRights(user);
    }

    public static boolean canCreateGrade(User user, Grade grade) {
        return UpdatePolicy.canModifyCourse(user, grade.getCourse());
    }

    public static boolean canCreateCourseMaterial(User user, CourseMaterial courseMaterial) {
        return UpdatePolicy.canModifySection(user, courseMaterial.getSection());
    }

    public static boolean canCreateSubmission(User user, Submission submission) {
        return ReadPolicy.canReadCourse(user, submission.getTask().getCourse());
    }
}
