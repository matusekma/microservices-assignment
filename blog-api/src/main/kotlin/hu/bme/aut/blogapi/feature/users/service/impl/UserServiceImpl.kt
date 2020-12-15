package hu.bme.aut.blogapi.feature.users.service.impl

import hu.bme.aut.blogapi.feature.users.dto.*
import hu.bme.aut.blogapi.feature.users.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class UserServiceImpl(val restTemplate: RestTemplate,
                      @Value("\${userservice.baseUrl}")
                      val basePath: String) : UserService {


    override fun existsById(id: Int): Boolean {
        val result = restTemplate.getForEntity("$basePath/api/users/$id", UserResponse::class.java)
        return result.statusCode == HttpStatus.OK
    }
}