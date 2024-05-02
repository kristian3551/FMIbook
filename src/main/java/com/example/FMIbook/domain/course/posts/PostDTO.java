package com.example.FMIbook.domain.course.posts;

import com.example.FMIbook.domain.course.CourseDTO;
import com.example.FMIbook.domain.users.user.UserDTO;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public PostDTO() {
    }

    public PostDTO(UUID id, String title, String description, Integer rate, CourseDTO course) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.rate = rate;
        this.course = course;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getRate() {
        return rate;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public void setCourse(CourseDTO course) {
        this.course = course;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO teacher) {
        this.user = teacher;
    }

    public List<PostDTO> getChildrenPosts() {
        return childrenPosts;
    }

    public void setChildrenPosts(List<PostDTO> childrenPosts) {
        this.childrenPosts = childrenPosts;
    }

    public static PostDTO serializeFromEntity(CoursePost post) {
        if (post == null) {
            return null;
        }

        List<PostDTO> childrenPosts = post.getChildrenPosts() != null
                ? post.getChildrenPosts().stream().map(PostDTO::serializeFromEntity).toList()
                : new ArrayList<>();

        PostDTO postDto = new PostDTO(post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getRate(),
                null);

        postDto.setUser(UserDTO.serializeFromEntity(post.getUser()));
        postDto.setChildrenPosts(childrenPosts);
        return postDto;
    }
}
