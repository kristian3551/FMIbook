package com.example.FMIbook.domain.auth;

import jakarta.validation.constraints.Pattern;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    @Pattern(regexp = "(student|teacher|admin)", message = "user type is invalid")
    private String type;
    private String facultyNumber;
    private Integer semester;
    private Integer group;
    private String description;
    private String degree;

    public RegisterRequest(String name,
                           String email,
                           String password,
                           String type,
                           String facultyNumber,
                           Integer semester,
                           Integer group,
                           String description,
                           String degree) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
        this.facultyNumber = facultyNumber;
        this.semester = semester;
        this.group = group;
        this.description = description;
        this.degree = degree;
    }

    public RegisterRequest() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public Integer getSemester() {
        return semester;
    }

    public Integer getGroup() {
        return group;
    }

    public String getDescription() {
        return description;
    }

    public String getDegree() {
        return degree;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }
}
