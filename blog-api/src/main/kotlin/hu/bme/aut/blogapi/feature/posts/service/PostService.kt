package hu.bme.aut.blogapi.feature.posts.service

import hu.bme.aut.blogapi.feature.posts.dto.CreatePostResponse


interface PostService {
    fun getPostById(postId: String): CreatePostResponse
}
