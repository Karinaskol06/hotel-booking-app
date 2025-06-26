package practice.hotel_system.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import practice.hotel_system.service.UserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor //constructor for all final fields
public class WebSecurityConfig {

    private final UserService userService;

    //algorithm to secure and verify passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //uses userservice for data retrieval and password checking
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    //processes login
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        //pages for unauthorized users
                        .requestMatchers("/", "/login", "/registration", "/logout", "/error403",
                                "/static/**", "/api/**",
                                "/about_us", "/contacts",
                                "/cart/**", "/cart", "/addToCart", "/updateCart", "/deleteFromCart", "/clearCart",
                                "/booking_form", "/booking_result")
                        .permitAll()
                        .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        //for authorized users
                        .requestMatchers("/booking", "/order/**", "/thank").hasAuthority("ROLE_user")
                        //for manager
                        .requestMatchers("/manager", "/manager/**").hasAuthority("ROLE_manager")
                        //for administrator
                        .requestMatchers("/admin", "/admin/**").hasAuthority("ROLE_admin")
                        .anyRequest().authenticated()) //any other request requires auth
                //custom login page
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .permitAll()
                        .logoutSuccessUrl("/"))
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/403")
                )
                //binding with spring security
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}