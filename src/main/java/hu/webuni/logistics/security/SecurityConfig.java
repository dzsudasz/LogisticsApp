package hu.webuni.logistics.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public static final String ADDRESS_MANAGER = "AddressManager";
    public static final String TRANSPORT_MANAGER = "TransportManager";

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {

        AuthenticationManagerBuilder authManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        return authManagerBuilder.authenticationProvider(authenticationProvider()).build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/login/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/addresses/**")
                .hasAnyAuthority(ADDRESS_MANAGER)
                .requestMatchers(HttpMethod.DELETE, "/api/addresses/**")
                .hasAnyAuthority(ADDRESS_MANAGER)
                .requestMatchers(HttpMethod.PUT, "/api/addresses/**")
                .hasAnyAuthority(ADDRESS_MANAGER)
                .requestMatchers(HttpMethod.POST, "/api/transportPlans/**")
                .hasAnyAuthority(TRANSPORT_MANAGER)
                .anyRequest().permitAll();

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails addressUser = User.builder()
                .username("addressUser")
                .password(passwordEncoder().encode("pass"))
                .authorities(ADDRESS_MANAGER)
                .build();

        UserDetails transportUser = User.builder()
                .username("transportUser")
                .password(passwordEncoder().encode("pass"))
                .authorities(TRANSPORT_MANAGER)
                .build();

        return new InMemoryUserDetailsManager(addressUser, transportUser);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());

        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
