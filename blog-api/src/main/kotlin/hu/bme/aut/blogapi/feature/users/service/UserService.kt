package hu.bme.aut.blogapi.feature.users.service

import hu.bme.aut.blogapi.feature.users.dto.CreateUserRequest
import hu.bme.aut.blogapi.feature.users.dto.UpdateUserRequest
import hu.bme.aut.blogapi.feature.users.dto.UserResponse
import org.springframework.data.domain.Sort

interface UserService {
    fun findAllUsersSorted(sort: Sort): List<UserResponse>
    fun createUser(createUserRequest: CreateUserRequest): UserResponse
    fun updateUser(id: String, updateUserRequest: UpdateUserRequest): UserResponse
    fun findUserById(id: String): UserResponse
    fun deleteUserById(id: String)
}
