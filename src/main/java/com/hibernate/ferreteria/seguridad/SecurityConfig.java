package com.hibernate.ferreteria.seguridad;
import com.hibernate.ferreteria.servicios.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UsuarioService userServ;

    public SecurityConfig(UsuarioService userServ) {
        this.userServ = userServ;
    }

    @Bean
    public PasswordEncoder codificaPass(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager autenticacion(AuthenticationConfiguration authConfig)
            throws Exception{
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityChain(HttpSecurity http, AuthenticationManager authManger)
            throws Exception{
        http.
                csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/articulo/**")
                        .hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                )
                .authenticationManager(authManger)
                .userDetailsService(userServ)
                .formLogin(form -> form
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()
                )
                .httpBasic(basic -> {});
                return http.build();
    }


}
