package com.opollo.security_oauth2_demo.config.security

import com.opollo.security_oauth2_demo.config.service.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(httpSecurity: HttpSecurity):SecurityFilterChain{
        return httpSecurity.csrf{it.disable()}
            .authorizeHttpRequests {
                auth->
                auth.requestMatchers("/","/login","/error","/webjars/**").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2Login{
                oauth2->
                oauth2.loginPage("/login")
                    .defaultSuccessUrl("/dashboard",true)
                    .failureUrl("/login?error=true")
                    .userInfoEndpoint{
                        userInfo->
                        userInfo.userService(customOauth2UserService())
                    }
            }
            .logout{
                logout->
                logout.logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            }
            .build()
    }

    @Bean
    fun customOauth2UserService():CustomOAuth2UserService{
        return CustomOAuth2UserService()
    }
}