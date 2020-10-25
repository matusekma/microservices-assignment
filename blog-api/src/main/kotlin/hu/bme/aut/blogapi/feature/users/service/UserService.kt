package hu.bme.aut.blogapi.feature.users.service

import hu.bme.aut.blogapi.feature.posts.dto.CreatePostRequest
import hu.bme.aut.blogapi.feature.posts.dto.CreatePostResponse
import hu.bme.aut.blogapi.feature.users.dto.CreateUserRequest
import hu.bme.aut.blogapi.feature.users.dto.CreateUserResponse
import hu.bme.aut.blogapi.feature.users.dto.UserResponse
import org.springframework.data.domain.Sort

interface UserService {
    fun findAllUsersSorted(sort: Sort? = Sort.by(Sort.Direction.ASC, "username")): List<UserResponse>
    fun createUser(createUserRequest: CreateUserRequest): CreateUserResponse
    fun createPostForUser(userId: String, createPostRequest: CreatePostRequest): CreatePostResponse
}
