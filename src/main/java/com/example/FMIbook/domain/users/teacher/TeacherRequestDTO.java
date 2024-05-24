package com.example.FMIbook.domain.users.teacher;

import lombok.Data;

import java.util.UUID;

@Data
public class TeacherRequestDTO {
    private String email;
    private String password;
    private String name;
    private String degree;
    private UUID departmentId;
}
