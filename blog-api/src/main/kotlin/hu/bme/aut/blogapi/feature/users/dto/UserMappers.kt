package hu.bme.aut.blogapi.feature.users.dto

import hu.bme.aut.blogapi.domain.User

fun User.toUserResponse(): UserResponse {
    return UserResponse(
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