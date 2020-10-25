package hu.bme.aut.blogapi.feature.posts.service

import hu.bme.aut.blogapi.feature.posts.dto.PostResponse


interface PostService {
    fun getPostById(postId: String): PostResponse
}
