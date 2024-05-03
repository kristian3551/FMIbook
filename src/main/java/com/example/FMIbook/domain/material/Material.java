package com.example.FMIbook.domain.material;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

@Entity
@Table(name = "materials")
public class Material {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;

    @Column(nullable = false)
    @NotEmpty(message = "name is empty")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;


}
