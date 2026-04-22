package et.edu.astu.core.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security){
        return security.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(http -> http
                        .requestMatchers("/api/auth/e/login/", "/api/auth/u/login/", "/api/admin/**")
                        .permitAll()
                        .requestMatchers("/api/e/**")
                        .hasRole("EMPLOYEE")
                        .requestMatchers("/api/u/**")
                        .hasRole("USER")
                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
