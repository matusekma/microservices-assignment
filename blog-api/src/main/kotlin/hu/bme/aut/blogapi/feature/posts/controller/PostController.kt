package hu.bme.aut.blogapi.feature.posts.controller

import hu.bme.aut.blogapi.feature.posts.dto.CreatePostRequest
import hu.bme.aut.blogapi.feature.posts.dto.CreatePostResponse
import hu.bme.aut.blogapi.feature.posts.dto.PostResponse
import hu.bme.aut.blogapi.feature.posts.dto.UpdatePostRequest
import hu.bme.aut.blogapi.feature.posts.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.query.Param
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(val postService: PostService) {

    @GetMapping
    fun getPostsPaged(@Param(value = "isArchived") isArchived: Boolean = false, pageable: Pageable): Page<PostResponse> {
        return postService.findAllPostsPaged(isArchived, pageable)
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: String): PostResponse {
        return postService.getPostById(id)
    }

    @GetMapping("/users/{userId}")
    fun getPostsOfUserPaged(@PathVariable userId: String, @Param(value = "isArchived") isArchived: Boolean = false, pageable: Pageable): Page<PostResponse> {
        return postService.findAllPostsByUserPaged(userId, isArchived, pageable)
    }

    @PostMapping("/users/{userId}")
    fun postPostForUser(@PathVariable userId: String, @RequestBody createPostRequest: CreatePostRequest): CreatePostResponse {
        return postService.createPostForUser(userId, createPostRequest)
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: String) {
        return postService.deletePostById(id)
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: String, @RequestBody updatePostRequest: UpdatePostRequest): PostResponse {
        return postService.updatePost(id, updatePostRequest)
    }

    @PutMapping("/{id}/archive")
    fun toggleIsArchivedForPost(@PathVariable id: String): PostResponse {
        return postService.toggleIsArchivedForPost(id)
    }
}