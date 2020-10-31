package hu.bme.aut.blogapi.feature.users.service.impl

import hu.bme.aut.blogapi.domain.User
import hu.bme.aut.blogapi.exception.EntityNotFoundException
import hu.bme.aut.blogapi.feature.users.dto.*
import hu.bme.aut.blogapi.feature.users.service.UserService
import hu.bme.aut.blogapi.repository.PostRepository
import hu.bme.aut.blogapi.repository.UserRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(val userRepository: UserRepository) : UserService {

    private fun findUserByIdOrThrow(id: String): User =
            userRepository.findById(id)
                    .orElseThrow { throw EntityNotFoundException("User not found.") }


    override fun findAllUsersSorted(sort: Sort): List<UserResponse> {
        return userRepository.findAll(sort).map { it.toUserResponse() }
    }

    override fun createUser(createUserRequest: CreateUserRequest): UserResponse =
            userRepository.insert(createUserRequest.toUser())
                    .toUserResponse()

    override fun updateUser(id: String, updateUserRequest: UpdateUserRequest): UserResponse {
        val user = userRepository.findById(id).orElseThrow { throw EntityNotFoundException("User with id $id not found.") }
        user.username = updateUserRequest.username
        return userRepository.save(user).toUserResponse()
    }

    override fun findUserById(id: String): UserResponse =
            findUserByIdOrThrow(id).toUserResponse()

    override fun deleteUserById(id: String) {
        userRepository.deleteById(id)
    }
}