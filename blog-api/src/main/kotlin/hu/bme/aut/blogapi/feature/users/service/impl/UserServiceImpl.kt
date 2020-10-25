package hu.bme.aut.blogapi.feature.users.service.impl

import hu.bme.aut.blogapi.exception.EntityNotFoundException
import hu.bme.aut.blogapi.feature.posts.dto.CreatePostRequest
import hu.bme.aut.blogapi.feature.posts.dto.CreatePostResponse
import hu.bme.aut.blogapi.feature.posts.dto.toPost
import hu.bme.aut.blogapi.feature.posts.dto.toCreatePostResponse
import hu.bme.aut.blogapi.feature.users.dto.*
import hu.bme.aut.blogapi.feature.users.service.UserService
import hu.bme.aut.blogapi.repository.PostRepository
import hu.bme.aut.blogapi.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepository: UserRepository, val postRepository: PostRepository) : UserService {

    override fun findAllUsersSorted(sort: Sort?): List<UserResponse> {
        return userRepository.findAll(sort!!).map { user -> user.toUserResponse() }
    }

    override fun createUser(createUserRequest: CreateUserRequest): CreateUserResponse {
        return userRepository.insert(createUserRequest.toUser()).toCreateUserResponse()
    }

    override fun createPostForUser(userId: String, createPostRequest: CreatePostRequest): CreatePostResponse {
        userRepository.findById(userId).orElseThrow { throw EntityNotFoundException("User not found.") }
        val post = createPostRequest.toPost(userId)
        return postRepository.insert(post).toCreatePostResponse()
    }

}