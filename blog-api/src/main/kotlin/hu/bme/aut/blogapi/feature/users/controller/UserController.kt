package hu.bme.aut.blogapi.feature.users.controller

import hu.bme.aut.blogapi.feature.users.dto.CreateUserRequest
import hu.bme.aut.blogapi.feature.users.dto.UpdateUserRequest
import hu.bme.aut.blogapi.feature.users.dto.UserResponse
import hu.bme.aut.blogapi.feature.users.service.UserService
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @GetMapping
    fun getAllUsersSorted(sort: Sort = Sort.by(Sort.Direction.DESC, "username")): List<UserResponse> {
        return userService.findAllUsersSorted(sort)
    }

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: String): UserResponse {
        return userService.findUserById(userId)
    }

    @PostMapping
    fun postUser(@RequestBody createUserRequest: CreateUserRequest): UserResponse {
        return userService.createUser(createUserRequest)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable userId: String, @RequestBody updateUserRequest: UpdateUserRequest): UserResponse {
        return userService.updateUser(userId, updateUserRequest)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: String) {
        userService.deleteUserById(userId)
    }
}