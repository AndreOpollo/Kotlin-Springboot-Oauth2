package com.opollo.security_oauth2_demo.config.service

import com.opollo.security_oauth2_demo.config.model.UserInfo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    private val oAuth2User: OAuth2User,
    private val userInfo: UserInfo
):OAuth2User{

    override fun getName(): String =userInfo.name

    override fun getAttributes(): MutableMap<String, Any> = oAuth2User.attributes

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = oAuth2User.authorities

    fun getUserInfo():UserInfo = userInfo

}