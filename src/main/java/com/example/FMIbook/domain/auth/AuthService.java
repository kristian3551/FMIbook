package com.example.FMIbook.domain.auth;

import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.student.StudentDTO;
import com.example.FMIbook.domain.users.student.StudentRepository;
import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.domain.users.teacher.TeacherDTO;
import com.example.FMIbook.domain.users.teacher.TeacherRepository;
import com.example.FMIbook.domain.users.user.User;
import com.example.FMIbook.domain.users.user.UserDTO;
import com.example.FMIbook.domain.users.user.UserRepository;
import com.example.FMIbook.domain.users.user.exception.UserNotFoundException;
import com.example.FMIbook.utils.exception.ForbiddenException;
import com.example.FMIbook.utils.jwt.JwtService;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class AuthService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Optional<Student> student = studentRepository.findByEmail(request.getEmail());
        if (student.isPresent()) {
            String accessToken = jwtService.generateToken(student.get());
            return new AuthenticationResponse(
                    accessToken,
                    jwtService.generateRefreshToken(student.get()),
                    StudentDTO.serializeFromEntity(student.get())
            );
        }
        Optional<Teacher> teacher = teacherRepository.findByEmail(request.getEmail());
        if (teacher.isPresent()) {
            String accessToken = jwtService.generateToken(teacher.get());
            return new AuthenticationResponse(
                    accessToken,
                    jwtService.generateRefreshToken(student.get()),
                    TeacherDTO.serializeFromEntity(teacher.get())
            );
        }
        Optional<User> admin = userRepository.findByEmail(request.getEmail());
        if (admin.isPresent()) {
            String token = jwtService.generateToken(admin.get());
            return new AuthenticationResponse(
                    token,
                    jwtService.generateRefreshToken(student.get()),
                    TeacherDTO.serializeFromEntity(admin.get())
            );
        }

        throw new UserNotFoundException();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        String jwtToken = null;
        User user = null;
        if (request.getType().equals("student")) {
            user = new Student(
                    request.getName(),
                    request.getFacultyNumber(),
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getSemester(),
                    request.getGroup(),
                    request.getDescription(),
                    request.getDegree()
            );
            studentRepository.save((Student) user);
            jwtToken = jwtService.generateToken(user);
        } else if (request.getType().equals("teacher")) {
            user = new Teacher(
                    request.getEmail(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getName(),
                    request.getDegree()
            );
            teacherRepository.save((Teacher) user);
            jwtToken = jwtService.generateToken(user);
        }

        if (jwtToken == null) {
            throw new ForbiddenException("Unauthorized!", 101);
        }

        return new AuthenticationResponse(
                jwtToken,
                jwtService.generateRefreshToken(user),
                UserDTO.serializeFromEntity(user)
        );
    }

    public AuthenticationResponse registerAdmin(RegisterRequest request) {
        User admin = new User(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword())
        );
        userRepository.save(admin);
        String jwtToken = jwtService.generateToken(admin);

        return new AuthenticationResponse(
                jwtToken,
                jwtService.generateRefreshToken(admin),
                UserDTO.serializeFromEntity(admin)
        );
    }

    public AuthenticationResponse refreshToken(String refreshToken) {
        if (refreshToken == null || !refreshToken.startsWith("Bearer ")) {
            throw new ForbiddenException("refresh token is invalid", 403);
        }

        String jwt = refreshToken.substring(7);
        String userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null) {
            UserDetails user = userDetailsService.loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwt, user)) {
                return AuthenticationResponse.builder()
                        .refreshToken(jwtService.generateRefreshToken(user))
                        .accessToken(jwtService.generateToken(user))
                        .user(null)
                        .build();
            }
        }
        throw new ForbiddenException("cannot refresh token", 403);
    }
}
