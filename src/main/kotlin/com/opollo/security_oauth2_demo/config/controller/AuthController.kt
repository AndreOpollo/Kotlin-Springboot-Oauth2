package com.opollo.security_oauth2_demo.config.controller

import com.opollo.security_oauth2_demo.config.model.AuthProvider
import com.opollo.security_oauth2_demo.config.model.UserInfo
import com.opollo.security_oauth2_demo.config.service.CustomOAuth2User
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class AuthController {

    @GetMapping("/")
    fun home():String = "home"

    @GetMapping("/login")
    fun login():String = "login"

    @GetMapping("/dashboard")
    fun dashboard(
        model:Model,
        authentication: Authentication
    ):String{
        val principal = authentication.principal

        val userInfo = when(principal){
            is CustomOAuth2User->principal.getUserInfo()
            is DefaultOidcUser-> UserInfo(
                id = principal.getAttribute<String>("sub") ?: "",
                name = principal.getAttribute("name") ?: "",
                email = principal.getAttribute("email") ?: "",
                imageUrl = principal.getAttribute("picture"),
                provider = AuthProvider.GOOGLE
            )
            is OAuth2User->UserInfo(
                id = principal.getAttribute<Any>("id")?.toString() ?: "",
                name = principal.getAttribute("name") ?: principal.getAttribute("login") ?: "",
                email = principal.getAttribute("email") ?: "",
                imageUrl = principal.getAttribute("avatar_url"),
                provider = AuthProvider.GITHUB
            )
            else -> throw IllegalArgumentException("Unknown User type: {principal::class}")
        }

        model.addAttribute("user",userInfo)
        return "dashboard"
    }
}