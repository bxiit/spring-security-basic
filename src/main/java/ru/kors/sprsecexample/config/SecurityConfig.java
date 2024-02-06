package ru.kors.sprsecexample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.kors.sprsecexample.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // моделирование авторизации на уровне метода  (@PreAuthorize("hasAuthority('ROLE_USER')")) и т.д.
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService() {
//        UserDetails admin = User.builder().username("admin").password(passwordEncoder.encode("admin")).roles("ADMIN").build();
//        UserDetails user = User.builder().username("user").password(passwordEncoder.encode("user")).roles("USER").build();
//        UserDetails bex = User.builder().username("bex").password(passwordEncoder.encode("bex")).roles("USER", "ADMIN").build();

        return new MyUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable) // для защиты от csrf атак
                .authorizeHttpRequests(auth -> auth.requestMatchers("api/v1/apps/welcome", "api/v1/apps/new-user").permitAll() // разрещаем всем посещать ссылку "api/v1/apps/welcome:
                        .requestMatchers("api/v1/apps/**").authenticated()) // но для всех остальных нужна аутентификация (api/v1/apps/**)
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll).build(); // авторизация разрешена всем
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
