package com.example.FMIbook.domain.auth;

import com.example.FMIbook.domain.users.student.Student;
import com.example.FMIbook.domain.users.student.StudentDTO;
import com.example.FMIbook.domain.users.student.StudentRepository;
import com.example.FMIbook.domain.users.teacher.Teacher;
import com.example.FMIbook.domain.users.teacher.TeacherDTO;
import com.example.FMIbook.domain.users.teacher.TeacherRepository;
import com.example.FMIbook.utils.exception.ForbiddenException;
import com.example.FMIbook.utils.jwt.JwtService;
import com.example.FMIbook.domain.users.user.User;
import com.example.FMIbook.domain.users.user.UserDTO;
import com.example.FMIbook.domain.users.user.UserRepository;
import com.example.FMIbook.domain.users.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthService(StudentRepository studentRepository,
                       TeacherRepository teacherRepository,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authManager) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        Optional<Student> student = studentRepository.findByEmail(request.getEmail());
        if (student.isPresent()) {
            String token = jwtService.generateToken(student.get());
            return new AuthenticationResponse(
                    token,
                    StudentDTO.serializeFromEntity(student.get())
            );
        }
        Optional<Teacher> teacher = teacherRepository.findByEmail(request.getEmail());
        if (teacher.isPresent()) {
            String token = jwtService.generateToken(teacher.get());
            return new AuthenticationResponse(
                    token,
                    TeacherDTO.serializeFromEntity(teacher.get())
            );
        }
        Optional<User> admin = userRepository.findByEmail(request.getEmail());
        if (admin.isPresent()) {
            String token = jwtService.generateToken(admin.get());
            return new AuthenticationResponse(
                    token,
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
        }
        else if (request.getType().equals("teacher")) {
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
                jwtToken, UserDTO.serializeFromEntity(user)
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
                jwtToken, UserDTO.serializeFromEntity(admin)
        );
    }
}
