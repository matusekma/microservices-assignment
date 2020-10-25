package hu.bme.aut.blogapi.feature.users.dto

import hu.bme.aut.blogapi.domain.User

fun User.toCreateUserResponse(): CreateUserResponse {
    return CreateUserResponse(
            id = this.id!!,
            username = username,
            email = email
    )
}

fun CreateUserRequest.toUser(): User {
    return User(
            username = username,
            email = email
    )
}