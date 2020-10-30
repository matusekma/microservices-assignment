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
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(val userRepository: UserRepository, val postRepository: PostRepository) : UserService {

    override fun findAllUsersSorted(sort: Sort): List<UserResponse> {
        return userRepository.findAll(sort).map { it.toUserResponse() }
    }

    override fun createUser(createUserRequest: CreateUserRequest): UserResponse =
            userRepository.insert(createUserRequest.toUser())
                    .toUserResponse()

    @Transactional
    override fun updateUser(userId: String, updateUserRequest: UpdateUserRequest): UserResponse {
        val user = userRepository.findById(userId).orElseThrow { throw EntityNotFoundException("User not found.") }
        user.username = updateUserRequest.username
        return userRepository.save(user).toUserResponse()
    }

    override fun findUserById(userId: String): UserResponse =
            userRepository.findById(userId)
                    .orElseThrow { throw EntityNotFoundException("User not found.") }.toUserResponse()

    override fun deleteUserById(userId: String) {
        userRepository.deleteById(userId)
    }
}