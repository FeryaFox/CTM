package ru.feryafox.ctm.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import ru.feryafox.ctm.services.CustomAuthenticationSuccessHandler;
import ru.feryafox.ctm.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        // Разрешаем доступ только к публичным страницам (логин, регистрация)
                        .requestMatchers("/login", "/registration", "/public/**", "/").permitAll()
                        // Защищаем страницы, доступные только для ролей
                        .requestMatchers("/employee/**").hasRole("EMPLOYEE")
                        .requestMatchers("/user/**").hasRole("USER")
                        // Все остальные страницы требуют авторизации
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login") // Страница логина
                        .usernameParameter("phoneNumber") // Параметр для имени пользователя
                        .successHandler(customAuthenticationSuccessHandler) // Обработчик успешного входа
                        .permitAll() // Разрешаем доступ к странице логина для всех
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для запроса выхода
                        .logoutSuccessUrl("/login") // Страница после выхода
                        .invalidateHttpSession(true) // Аннулировать сессию
                        .deleteCookies("JSESSIONID") // Удаление cookie сессии
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            // Перенаправление на страницу логина при ошибке авторизации
                            response.sendRedirect("/login?sessionExpired");
                        })
                )
                .sessionManagement(session -> session
                        .invalidSessionUrl("/login?sessionExpired") // URL для перенаправления при истечении сессии
                )
                .authenticationProvider(daoAuthenticationProvider()); // Используем authenticationProvider

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

