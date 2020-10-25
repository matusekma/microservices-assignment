package hu.bme.aut.blogapi.feature.posts.controller

import hu.bme.aut.blogapi.feature.posts.dto.CreatePostResponse
import hu.bme.aut.blogapi.feature.posts.dto.PostResponse
import hu.bme.aut.blogapi.feature.posts.service.PostService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController(val postService: PostService) {

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: String): CreatePostResponse {
        return postService.getPostById(id)
    }

    @GetMapping("/users/{userId}")
    fun getPostsOfUserPaged(@PathVariable userId: String, pageable: Pageable): Page<PostResponse> {
        return postService.findAllPostsByUserPaged(userId, pageable)
    }
}