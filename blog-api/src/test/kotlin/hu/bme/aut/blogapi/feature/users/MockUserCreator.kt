package hu.bme.aut.blogapi.feature.users

import hu.bme.aut.blogapi.domain.User
import hu.bme.aut.blogapi.feature.users.dto.CreateUserRequest
import hu.bme.aut.blogapi.feature.users.dto.UpdateUserRequest


const val mockId = "123"

fun getMockUser(): User = User(
        id = mockId,
        username = "testusername",
        email = "test@test.com")

fun getMockCreateUserRequest(): CreateUserRequest =
        CreateUserRequest(
                username = "testusername",
                email = "test@test.com")

fun getMockUpdateUserRequest(): UpdateUserRequest =
        UpdateUserRequest(
                username = "updatedusername"
        )