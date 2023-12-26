package com.tcc.entrepaginas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfiguration {

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public SecurityFilterChain sessionFilterChain(HttpSecurity http) throws Exception {
                return http.csrf((csrf) -> csrf.disable())
                                .httpBasic(Customizer.withDefaults())
                                .authorizeHttpRequests((authorize) -> authorize
                                                .requestMatchers("/perfil").authenticated()
                                                .requestMatchers("/infos/**").authenticated()
                                                .requestMatchers("/community/**").authenticated()
                                                .requestMatchers("/**").permitAll()
                                                .anyRequest().authenticated())
                                .formLogin((form) -> form
                                                .loginPage("/login").permitAll()
                                                .defaultSuccessUrl("/index")) // Redirecionamento padrão para "/index"
                                .logout((logout) -> logout
                                                .logoutSuccessUrl("/index").permitAll())
                                .build();
        }

        @Bean
        public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
                        UserDetailsService userDetailsService) throws Exception {

                return http.getSharedObject(AuthenticationManagerBuilder.class)
                                .userDetailsService(userDetailsService)
                                .passwordEncoder(passwordEncoder)
                                .and()
                                .build();
        }
}
