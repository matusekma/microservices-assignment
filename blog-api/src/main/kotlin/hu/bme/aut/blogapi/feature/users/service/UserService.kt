package hu.bme.aut.blogapi.feature.users.service

import hu.bme.aut.blogapi.feature.users.dto.CreateUserRequest
import hu.bme.aut.blogapi.feature.users.dto.UpdateUserRequest
import hu.bme.aut.blogapi.feature.users.dto.UserResponse
import org.springframework.data.domain.Sort

interface UserService {
    fun findAllUsersSorted(sort: Sort): List<UserResponse>
    fun createUser(createUserRequest: CreateUserRequest): UserResponse
    fun updateUser(userId: String, updateUserRequest: UpdateUserRequest): UserResponse
    fun findUserById(userId: String): UserResponse
    fun deleteUserById(userId: String)
}
