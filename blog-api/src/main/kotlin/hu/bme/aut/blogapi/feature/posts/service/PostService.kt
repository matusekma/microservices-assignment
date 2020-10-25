package hu.bme.aut.blogapi.feature.posts.service

import hu.bme.aut.blogapi.feature.posts.dto.CreatePostResponse
import hu.bme.aut.blogapi.feature.posts.dto.PostResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface PostService {
    fun getPostById(postId: String): CreatePostResponse
    fun findAllPostsByUserPaged(userId: String, pageable: Pageable): Page<PostResponse>
}
