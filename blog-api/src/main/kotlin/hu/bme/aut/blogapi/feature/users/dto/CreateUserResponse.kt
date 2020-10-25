package hu.bme.aut.blogapi.feature.users.dto

data class CreateUserResponse(
        val id: String,
        val username: String,
        val email: String)
