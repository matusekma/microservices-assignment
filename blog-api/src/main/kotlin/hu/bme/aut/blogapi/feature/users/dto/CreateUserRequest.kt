package hu.bme.aut.blogapi.feature.users.dto

data class CreateUserRequest(
        val username: String,
        val email: String)
