package com.opollo.security_oauth2_demo.config.model

data class UserInfo(
    val id: String,
    val name: String,
    val email: String,
    val imageUrl: String?,
    val provider: AuthProvider
)

enum class AuthProvider{
    GOOGLE, GITHUB
}