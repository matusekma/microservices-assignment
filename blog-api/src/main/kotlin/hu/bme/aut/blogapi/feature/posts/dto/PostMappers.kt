package hu.bme.aut.blogapi.feature.posts.dto

import hu.bme.aut.blogapi.domain.Post
import java.time.LocalDateTime

fun Post.toCreatePostResponse(): CreatePostResponse {
    return CreatePostResponse(
            id = this.id!!,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            isArchived = this.isArchived,
            userId = userId
    )
}

fun Post.toPostResponse(): PostResponse {
    return PostResponse(
            id = this.id!!,
            title = this.title,
            content = this.content,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            isArchived = this.isArchived,
            userId = this.userId
    )
}

fun CreatePostRequest.toPost(userId: String): Post {
    return Post(
            title = this.title,
            content = this.content,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            isArchived = false,
            userId = userId
    )
}