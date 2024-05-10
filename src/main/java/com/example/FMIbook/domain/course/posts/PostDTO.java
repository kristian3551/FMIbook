package com.example.FMIbook.domain.course.posts;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.users.user.UserDTO;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private UUID id;

    @Pattern(regexp = ".+", message = "title is empty")
    private String title;

    @Pattern(regexp = ".+", message = "description is empty")
    private String description;

    private Integer rate;

    private CourseDTO course;

    private UserDTO user;

    private List<PostDTO> childrenPosts;

    public static PostDTO serializeLightweight(CoursePost post) {
        if (post == null) {
            return null;
        }

        return PostDTO
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .rate(post.getRate())
                .user(UserDTO.serializeFromEntity(post.getUser()))
                .childrenPosts(new ArrayList<>())
                .build();
    }

    public static PostDTO serializeFromEntity(CoursePost post) {
        if (post == null) {
            return null;
        }

        List<PostDTO> childrenPosts = post.getChildrenPosts() != null
                ? post.getChildrenPosts().stream().map(PostDTO::serializeFromEntity).toList()
                : new ArrayList<>();

        return PostDTO
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .rate(post.getRate())
                .user(UserDTO.serializeFromEntity(post.getUser()))
                .childrenPosts(childrenPosts)
                .build();
    }
}
