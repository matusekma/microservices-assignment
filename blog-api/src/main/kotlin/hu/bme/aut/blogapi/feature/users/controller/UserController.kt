package hu.bme.aut.blogapi.feature.users.controller

import hu.bme.aut.blogapi.feature.posts.dto.CreatePostRequest
import hu.bme.aut.blogapi.feature.posts.dto.CreatePostResponse
import hu.bme.aut.blogapi.feature.users.dto.CreateUserRequest
import hu.bme.aut.blogapi.feature.users.dto.CreateUserResponse
import hu.bme.aut.blogapi.feature.users.service.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(val userService: UserService) {

    @PostMapping()
    fun postUser(@RequestBody createUserRequest: CreateUserRequest): CreateUserResponse {
        return userService.createUser(createUserRequest)
    }

    @PostMapping("/{userId}/posts")
    fun postPostForUser(@PathVariable userId: String, @RequestBody createPostRequest: CreatePostRequest): CreatePostResponse {
        return userService.createPostForUser(userId, createPostRequest)
    }
}