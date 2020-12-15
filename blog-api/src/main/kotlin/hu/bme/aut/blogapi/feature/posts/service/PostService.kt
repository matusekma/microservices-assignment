package hu.bme.aut.blogapi.feature.posts.service

import hu.bme.aut.blogapi.feature.posts.dto.CreatePostRequest
import hu.bme.aut.blogapi.feature.posts.dto.CreatePostResponse
import hu.bme.aut.blogapi.feature.posts.dto.PostResponse
import hu.bme.aut.blogapi.feature.posts.dto.UpdatePostRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable


interface PostService {
    fun getPostById(id: String): PostResponse
    fun findAllPostsByUserPaged(userId: Int, isArchived: Boolean, pageable: Pageable): Page<PostResponse>
    fun findAllPostsPaged(isArchived: Boolean, pageable: Pageable): Page<PostResponse>
    fun createPostForUser(userId: Int, createPostRequest: CreatePostRequest): CreatePostResponse
    fun deletePostById(id: String)
    fun updatePost(id: String, updatePostRequest: UpdatePostRequest): PostResponse
    fun toggleIsArchivedForPost(id: String): PostResponse
}
