/*
 * Copyright (c) 2024 FSDM IT Club.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fsdm.it.fsdm_it_club.config;


import com.fsdm.it.fsdm_it_club.entity.User;
import com.fsdm.it.fsdm_it_club.services.AdminService;
import com.fsdm.it.fsdm_it_club.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCryptPasswordEncoder();
    }


    @Configuration
    @Order(1)
    public static class AdminSecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChainAdmin(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
            http.securityMatcher("/admin", "/admin/**").authorizeHttpRequests(
                            authorizationManagerRequestMatcherRegistry ->
                            {
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/login")).permitAll();
                                // resources
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/css/**")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/js/**")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/img/**")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/fonts/**")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/scss/**")).permitAll();


                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).permitAll();
//                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole(Role.ADMIN_ROLE);

                            }

                    ).securityMatcher("/admin", "/admin/**")

                    .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/admin/logout").logoutSuccessUrl("/admin/login").deleteCookies("JSESSIONID"))
                    .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->

                            {
                                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {

                                    response.sendRedirect("/admin/login");
                                });
                            }
                    );


            return http.build();
        }

        @Bean
        @Primary
        public AuthenticationManager authenticationManagerAdmin(
                AdminService adminService,
                PasswordEncoder passwordEncoder) {

            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(adminService);
            authenticationProvider.setPasswordEncoder(passwordEncoder);
            return new ProviderManager(authenticationProvider);
        }
    }


    @Configuration
    @Order(2)

    public static class UserSecurityConfig {

        private final PasswordEncoder passwordEncoder;
        private final UserService userService;

        public UserSecurityConfig(PasswordEncoder passwordEncoder, UserService userService) {
            this.passwordEncoder = passwordEncoder;
            this.userService = userService;
        }

        @Bean
        public SecurityFilterChain filterChainApp2(AuthenticationManager authenticationManager, HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

            MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
            http.securityMatcher("/public/**", "/express/**", "/", "/login", "/logout", "/event-listing", "/event-details", "/register",  "/reset-password", "/reset-password-request").authorizeHttpRequests(
                            authorizationManagerRequestMatcherRegistry ->
                            {
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/event-listing")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/event-details")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/join-us")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/test/**")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/register*")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/login*")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/logout*")).hasRole(User.Role.MEMBER.name());
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/verify*")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/public/**")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/reset-password")).permitAll();
                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/reset-password-request")).permitAll();

                                authorizationManagerRequestMatcherRegistry.requestMatchers(mvcMatcherBuilder.pattern("/home/**")).hasRole(User.Role.MEMBER.name());
                            }
                    ).securityMatcher("/public/**",  "/", "/login", "/logout", "/register","/event-listing", "/event-details", "/join-us",  "/reset-password", "/reset-password-request", "/verify", "/test/**")
                    .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/logout").logoutSuccessUrl("/login").deleteCookies("JSESSIONID"))


                    .exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                            {
                                httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/admin/accessDenied");
                                httpSecurityExceptionHandlingConfigurer.authenticationEntryPoint((request, response, authException) -> {

                                    response.sendRedirect("/");
                                });

                            }
                    );

            return http.build();

        }

        @Bean
        public AuthenticationManager authenticationManagerUser() {

            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService((UserDetailsService) userService);
            authenticationProvider.setPasswordEncoder(passwordEncoder);
            return new ProviderManager(authenticationProvider);
        }

    }
}
