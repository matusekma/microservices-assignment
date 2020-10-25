package hu.bme.aut.blogapi.feature.posts.dto

import hu.bme.aut.blogapi.domain.Post
import java.time.LocalDateTime

fun Post.toCreatePostResponse(): CreatePostResponse {
    return CreatePostResponse(
            id = this.id!!,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt
    )
}

fun Post.toPostResponse(): PostResponse {
    return PostResponse(
            id = this.id!!,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt
    )
}

fun CreatePostRequest.toPost(userId: String): Post {
    return Post(
            title = title,
            content = content,
            createdAt = LocalDateTime.now(),
            userId = userId
    )
}