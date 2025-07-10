package com.opollo.security_oauth2_demo.config.service

import com.opollo.security_oauth2_demo.config.model.AuthProvider
import com.opollo.security_oauth2_demo.config.model.UserInfo
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService: OAuth2UserService<OAuth2UserRequest,OAuth2User>{

    private val defaultService = DefaultOAuth2UserService()
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuth2User = defaultService.loadUser(userRequest)

        val registrationId = userRequest?.clientRegistration?.registrationId
        val userInfo = processOAuth2User(registrationId,oAuth2User)
        
        return CustomOAuth2User(oAuth2User,userInfo)

    }

    private fun processOAuth2User(registrationId:String?,oAuth2User: OAuth2User):UserInfo{
        return when(registrationId){
            "github"->{
                UserInfo(
                    id = oAuth2User.getAttribute<Int>("id")?.toString()?:"",
                    name = oAuth2User.getAttribute<String>("name")?:oAuth2User.getAttribute("login")?:"",
                    email = oAuth2User.getAttribute<String>("email")?:"",
                    imageUrl = oAuth2User.getAttribute("avatar_url"),
                    provider = AuthProvider.GITHUB
                )
            }
            "google"->{
                UserInfo(
                    id = oAuth2User.getAttribute<Int>("sub").toString()?:"",
                    name = oAuth2User.getAttribute("name")?:"",
                    email = oAuth2User.getAttribute("email")?:"",
                    imageUrl = oAuth2User.getAttribute("picture"),
                    provider = AuthProvider.GOOGLE
                )
            }
            else -> throw IllegalArgumentException("Unsupported Provider: $registrationId")
        }        

    }
}