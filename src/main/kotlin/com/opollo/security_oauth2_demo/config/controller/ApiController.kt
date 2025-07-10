package com.opollo.security_oauth2_demo.config.controller

import com.opollo.security_oauth2_demo.config.model.UserInfo
import com.opollo.security_oauth2_demo.config.service.CustomOAuth2User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class ApiController {

    @GetMapping("/user")
    fun getCurrentUser(authentication: Authentication):ResponseEntity<UserInfo>{
        val oAuth2User = authentication.principal as CustomOAuth2User
        return ResponseEntity.ok(oAuth2User.getUserInfo())
    }

    @GetMapping("/protected")
    fun protectedEndpoint(authentication: Authentication):ResponseEntity<Map<String,Any>>{
        val oAuth2User = authentication.principal as CustomOAuth2User

        return ResponseEntity.ok(
            mapOf(
                "message" to "This is a protected endpoint",
                "user" to oAuth2User.getUserInfo(),
                "authorities" to oAuth2User.authorities.map { it.authority }
            )
        )
    }
}