package com.example.FMIbook.config;

import com.example.FMIbook.domain.users.user.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JWTAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Autowired
    public SecurityConfiguration(JWTAuthenticationFilter jwtAuthFilter,
                                 AuthenticationProvider authenticationProvider) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(AbstractHttpConfigurer::disable);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((req) ->
                        req
                                .requestMatchers("api/auth/register-admin").hasAnyAuthority(Permission.ADMIN_CREATE.getPermission())
                                .requestMatchers("/api/auth/**").permitAll()

                                // Students endpoints permissions
                                .requestMatchers(HttpMethod.GET, "/api/students/**")
                                .hasAnyAuthority(Permission.ADMIN_READ.getPermission(), Permission.STUDENT_READ.getPermission(), Permission.TEACHER_READ.getPermission())
                                .requestMatchers(HttpMethod.POST, "/api/students/**")
                                .hasAnyAuthority(Permission.ADMIN_CREATE.getPermission())
                                .requestMatchers(HttpMethod.PUT, "/api/students/**")
                                .hasAnyAuthority(Permission.ADMIN_UPDATE.getPermission(), Permission.STUDENT_UPDATE.getPermission())
                                .requestMatchers(HttpMethod.DELETE, "/api/students/**")
                                .hasAnyAuthority(Permission.ADMIN_DELETE.getPermission(), Permission.STUDENT_DELETE.getPermission())

                                // Courses endpoints permissions
                                .requestMatchers(HttpMethod.GET, "/api/courses/**")
                                .hasAnyAuthority(Permission.ADMIN_READ.getPermission(), Permission.STUDENT_READ.getPermission(), Permission.TEACHER_READ.getPermission())
                                .requestMatchers(HttpMethod.POST, "/api/courses/posts/**")
                                .hasAnyAuthority(Permission.ADMIN_CREATE.getPermission(), Permission.STUDENT_CREATE.getPermission(), Permission.TEACHER_CREATE.getPermission())
                                .requestMatchers(HttpMethod.PUT, "/api/courses/posts/**")
                                .hasAnyAuthority(Permission.ADMIN_CREATE.getPermission(), Permission.STUDENT_CREATE.getPermission(), Permission.TEACHER_CREATE.getPermission())
                                .requestMatchers(HttpMethod.DELETE, "/api/courses/posts/**")
                                .hasAnyAuthority(Permission.ADMIN_CREATE.getPermission(), Permission.STUDENT_CREATE.getPermission(), Permission.TEACHER_CREATE.getPermission())
                                .requestMatchers(HttpMethod.POST, "/api/courses/**")
                                .hasAnyAuthority(Permission.ADMIN_CREATE.getPermission(), Permission.TEACHER_CREATE.getPermission())
                                .requestMatchers(HttpMethod.PUT, "/api/courses/**")
                                .hasAnyAuthority(Permission.ADMIN_UPDATE.getPermission(), Permission.TEACHER_UPDATE.getPermission())
                                .requestMatchers(HttpMethod.DELETE, "/api/courses/**")
                                .hasAnyAuthority(Permission.ADMIN_DELETE.getPermission(), Permission.TEACHER_DELETE.getPermission())

                                // Departments endpoints permissions
                                .requestMatchers(HttpMethod.GET, "/api/departments/**")
                                .hasAnyAuthority(Permission.ADMIN_READ.getPermission(), Permission.STUDENT_READ.getPermission(), Permission.TEACHER_READ.getPermission())
                                .requestMatchers(HttpMethod.POST, "/api/departments/**")
                                .hasAnyAuthority(Permission.ADMIN_CREATE.getPermission())
                                .requestMatchers(HttpMethod.PUT, "/api/departments/**")
                                .hasAnyAuthority(Permission.ADMIN_UPDATE.getPermission())
                                .requestMatchers(HttpMethod.DELETE, "/api/departments/**")
                                .hasAnyAuthority(Permission.ADMIN_DELETE.getPermission())

                                // Teachers endpoints permissions
                                .requestMatchers(HttpMethod.GET, "/api/teachers/**")
                                .hasAnyAuthority(Permission.ADMIN_READ.getPermission(), Permission.STUDENT_READ.getPermission(), Permission.TEACHER_READ.getPermission())
                                .requestMatchers(HttpMethod.POST, "/api/teachers/**")
                                .hasAnyAuthority(Permission.ADMIN_CREATE.getPermission())
                                .requestMatchers(HttpMethod.PUT, "/api/teachers/**")
                                .hasAnyAuthority(Permission.ADMIN_UPDATE.getPermission(), Permission.TEACHER_UPDATE.getPermission())
                                .requestMatchers(HttpMethod.DELETE, "/api/teachers/**")
                                .hasAnyAuthority(Permission.ADMIN_DELETE.getPermission(), Permission.TEACHER_DELETE.getPermission())

                                // Grades endpoints permissions
                                .requestMatchers(HttpMethod.GET, "/api/grades/**")
                                .hasAnyAuthority(Permission.ADMIN_READ.getPermission(), Permission.STUDENT_READ.getPermission(), Permission.TEACHER_READ.getPermission())
                                .requestMatchers(HttpMethod.POST, "/api/grades/**")
                                .hasAnyAuthority(Permission.ADMIN_CREATE.getPermission(), Permission.TEACHER_CREATE.getPermission())
                                .requestMatchers(HttpMethod.PUT, "/api/grades/**")
                                .hasAnyAuthority(Permission.ADMIN_UPDATE.getPermission(), Permission.TEACHER_UPDATE.getPermission())
                                .requestMatchers(HttpMethod.DELETE, "/api/grades/**")
                                .hasAnyAuthority(Permission.ADMIN_DELETE.getPermission(), Permission.TEACHER_DELETE.getPermission())

                                .anyRequest().authenticated()


                )
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
