package com.example.FMIbook.domain.policy;

import com.example.FMIbook.domain.course.Course;
import com.example.FMIbook.domain.course.achievement.Achievement;
import com.example.FMIbook.domain.course.posts.CoursePost;
import com.example.FMIbook.domain.course.section.Section;
import com.example.FMIbook.domain.course.task.Task;
import com.example.FMIbook.domain.users.user.Permission;
import com.example.FMIbook.domain.users.user.User;

public class ReadPolicy {
    private static boolean hasAdminReadRights(User user) {
        return user.getAuthorities().stream().anyMatch(
                grantedAuthority -> grantedAuthority.toString()
                        .equals(Permission.ADMIN_READ.getPermission()));
    }
    public static boolean canReadCourse(User user, Course course) {
        if (hasAdminReadRights(user)) {
            return true;
        }

        if (course.isPublic()) {
            return true;
        }

        return course.getStudents().stream().anyMatch(student -> student.getId().equals(user.getId()))
                || course.getTeachers().stream().anyMatch(student -> student.getId().equals(user.getId()));

    }

    public static boolean canReadAchievement(User user, Achievement achievement) {
        return canReadCourse(user, achievement.getCourse());
    }

    public static boolean canReadPost(User user, CoursePost post) {
        return canReadCourse(user, post.getCourse());
    }

    public static boolean canReadSection(User user, Section section) {
        return canReadCourse(user, section.getCourse());
    }

    public static boolean canReadTask(User user, Task task) {
        return canReadCourse(user, task.getCourse());
    }
}
